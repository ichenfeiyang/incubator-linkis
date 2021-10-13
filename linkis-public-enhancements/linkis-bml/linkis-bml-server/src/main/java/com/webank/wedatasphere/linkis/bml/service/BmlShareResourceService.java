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
package org.apache.linkis.bml.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

public interface BmlShareResourceService {

    void uploadShareResource(MultipartFile multipartFile, String user, Map<String, Object> properties);

    void updateShareResource(MultipartFile multipartFile, String user, Map<String, Object> properties);

    void downloadShareResource(String user, String resourceId, String version, OutputStream outputStream, Map<String, Object> properties);



}
