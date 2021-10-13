/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

package org.apache.linkis.cs.common.entity.listener;

import org.apache.linkis.cs.common.entity.source.ContextID;


public class CommonContextIDListenerDomain implements ContextIDListenerDomain {
    
    private String source;

    private ContextID contextID;

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public ContextID getContextID() {
        return this.contextID;
    }

    @Override
    public void setContextID(ContextID contextID) {
        this.contextID = contextID;
    }
}
