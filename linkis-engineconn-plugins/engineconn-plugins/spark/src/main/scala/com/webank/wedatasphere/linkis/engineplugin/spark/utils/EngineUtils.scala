/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.linkis.engineplugin.spark.utils

import java.io.{IOException, InputStream, OutputStream}
import java.net.ServerSocket
import java.util.HashMap

import org.apache.linkis.common.conf.CommonVars
import org.apache.linkis.common.io.FsPath
import org.apache.linkis.common.utils.Utils
import org.apache.linkis.engineplugin.spark.common.LineBufferedProcess
import org.apache.linkis.rpc.Sender
import org.apache.linkis.storage.resultset.ResultSetReader
import org.apache.linkis.storage.utils.StorageUtils
import org.apache.linkis.storage.{FSFactory, LineMetaData}

/**
  *
  */
object EngineUtils {
  private val user:String = System.getProperty("user.name")
  private var sparkVersion: String = _
  private  var fileSystem : org.apache.linkis.common.io.Fs = _

  def getName:String = Sender.getThisServiceInstance.getInstance

  def findAvailPort = {
    val socket = new ServerSocket(0)
    Utils.tryFinally(socket.getLocalPort){ Utils.tryQuietly(socket.close())}
  }

  def sparkSubmitVersion(): String = {
    if(sparkVersion != null) {
      return sparkVersion
    }
    val sparkSubmit = CommonVars("wds.linkis.server.spark-submit", "spark-submit").getValue
    val pb = new ProcessBuilder(sparkSubmit, "--version")
    pb.redirectErrorStream(true)
    pb.redirectInput(ProcessBuilder.Redirect.PIPE)

    val process = new LineBufferedProcess(pb.start())
    val exitCode = process.waitFor()
    val output = process.inputIterator.mkString("\n")

    val regex = """version (.*)""".r.unanchored

    sparkVersion = output match {
      case regex(version) => version
      case _ => throw new IOException(f"Unable to determing spark-submit version [$exitCode]:\n$output")
    }
    sparkVersion
  }

  def jarOfClass(cls: Class[_]): Option[String] = {
    val uri = cls.getResource("/" + cls.getName.replace('.', '/') + ".class")
    if (uri != null) {
      val uriStr = uri.toString
      if (uriStr.startsWith("jar:file:")) {
        Some(uriStr.substring("jar:file:".length, uriStr.indexOf("!")))
      } else {
        None
      }
    } else {
      None
    }
  }


  def createOutputStream(path:String): OutputStream = {
    if (fileSystem == null) this synchronized {
      if (fileSystem == null){
        fileSystem = FSFactory.getFs(StorageUtils.HDFS)
        fileSystem.init(new HashMap[String, String]())
      }
    }
    val outputStream:OutputStream = fileSystem.write(new FsPath(path),true)
    //val inputStream = new FileInputStream(logPath)
    outputStream
  }
  def createInputStream(path:String): InputStream = {
    if (fileSystem == null) this synchronized {
      if (fileSystem == null){
        fileSystem = FSFactory.getFs(StorageUtils.HDFS)
        fileSystem.init(new HashMap[String, String]())
      }
    }
    val inputStream:InputStream = fileSystem.read(new FsPath(path))
    //val inputStream = new FileInputStream(logPath)
    inputStream
  }

  def getResultStrByDolphinTextContent(dolphinContent:String):String = {
    val resultSetReader = ResultSetReader.getResultSetReader(dolphinContent)
    val errorMsg =  resultSetReader.getMetaData match {
      case metadata: LineMetaData =>
        val sb = new StringBuilder
        while (resultSetReader.hasNext){
          sb.append(resultSetReader.getRecord).append("\n")
        }
       sb.toString()
      case _ => dolphinContent
    }
    errorMsg
  }
}
