/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.core.sql;

import org.apache.linkis.engineconnplugin.seatunnel.util.SeatunnelUtils;

import org.apache.seatunnel.common.config.Common;
import org.apache.seatunnel.core.base.Starter;
import org.apache.seatunnel.core.flink.args.FlinkCommandArgs;
import org.apache.seatunnel.core.flink.config.FlinkJobType;
import org.apache.seatunnel.core.flink.utils.CommandLineUtils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlinkSqlStarter implements Starter {
  private static Logger logger = LoggerFactory.getLogger(FlinkSqlStarter.class);
  private static final String APP_JAR_NAME = "seatunnel-core-flink-sql.jar";
  private static final String CLASS_NAME = SeatunnelSql.class.getName();

  private final FlinkCommandArgs flinkCommandArgs;
  /** SeaTunnel flink sql job jar. */
  private final String appJar;

  FlinkSqlStarter(String[] args) {
    this.flinkCommandArgs = CommandLineUtils.parseCommandArgs(args, FlinkJobType.SQL);
    // set the deployment mode, used to get the job jar path.
    Common.setDeployMode(flinkCommandArgs.getDeployMode().getName());
    this.appJar = Common.appLibDir().resolve(APP_JAR_NAME).toString();
  }

  @Override
  public List<String> buildCommands() throws Exception {
    return CommandLineUtils.buildFlinkCommand(flinkCommandArgs, CLASS_NAME, appJar);
  }

  @SuppressWarnings("checkstyle:RegexpSingleline")
  public static int main(String[] args) {
    int exitCode = 0;
    logger.info("FlinkSqlStarter start");
    try {
      FlinkSqlStarter flinkSqlStarter = new FlinkSqlStarter(args);
      String commandVal = String.join(" ", flinkSqlStarter.buildCommands());
      logger.info("commandVal:" + commandVal);
      exitCode = SeatunnelUtils.executeLine(commandVal);
    } catch (Exception e) {
      exitCode = 1;
      logger.error("\n\n该任务最可能的错误原因是:\n" + e);
    }

    return exitCode;
  }
}