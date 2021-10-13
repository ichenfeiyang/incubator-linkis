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

package org.apache.linkis.cli.common.entity.execution.executor;


import org.apache.linkis.cli.common.entity.job.Job;

/**
 * @description: Executor should encapsulate methods that linkis-cli need to complete an {@link org.apache.linkis.cli.common.entity.execution.Execution}
 */
public interface Executor {
    boolean terminate(Job job);
}