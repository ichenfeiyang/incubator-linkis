/*
 * Copyright 2019 WeBank
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.linkis.cli.core.interactor.execution.jobexec;

import org.apache.linkis.cli.common.entity.execution.jobexec.JobExec;
import org.apache.linkis.cli.common.entity.execution.jobexec.JobStatus;
import org.apache.linkis.cli.common.entity.job.OutputWay;

/**
 * @description: Intermediate data during submission
 */
public abstract class JobSubmitExec implements JobExec, Cloneable {

    /**
     * ID for client itself
     */
    private String cid;
    /**
     * ID generated by server
     */
    private String jobID;

    private String message;

    private Exception exception;


    private JobStatus jobStatus = JobStatus.UNSUBMITTED;
    private OutputWay outputWay;
    private String outputPath;

    @Override
    public final String getCid() {
        return cid;
    }

    @Override
    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public final String getJobID() {
        return this.jobID;
    }

    @Override
    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public void setException(Exception exception) {
        this.exception = exception;
    }

    public final JobStatus getJobStatus() {
        return jobStatus;
    }

    public final void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public OutputWay getOutputWay() {
        return outputWay;
    }

    public void setOutputWay(OutputWay outputWay) {
        this.outputWay = outputWay;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public final boolean isJobSubmitted() {
        return !(this.getJobStatus() == JobStatus.UNSUBMITTED || this.getJobStatus() == JobStatus.SUBMITTING);
    }

    public final boolean isJobCompleted() {
        return this.isJobSuccess() || this.isJobFailure() || this.isJobCancelled() || this.isJobTimeout() || this.isJobAbnormalStatus();
    }

    public final boolean isJobSuccess() {
        return this.getJobStatus() == JobStatus.SUCCEED;
    }

    public final boolean isJobFailure() {
        return this.getJobStatus() == JobStatus.FAILED;
    }

    public final boolean isJobCancelled() {
        return this.getJobStatus() == JobStatus.CANCELLED;
    }

    public final boolean isJobTimeout() {
        return this.getJobStatus() == JobStatus.TIMEOUT;
    }

    public final boolean isJobAbnormalStatus() {
        return this.getJobStatus() == JobStatus.UNKNOWN;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        JobSubmitExec ret = (JobSubmitExec) super.clone();
        return ret;
    }

    public JobSubmitExec getCopy() throws CloneNotSupportedException {
        return (JobSubmitExec) this.clone();
    }

}