/*
 * Copyright (c) 2024, Alibaba Cloud;
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aliyun.dataworks.migrationx.domain.dataworks.objects.entity.client;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.File;

/**
 * @author sam.liux
 * @date 2020/02/13
 */
@Data
@ToString
public class IdeImportRequest {
    @NotNull(message = "projectId cannot be null")
    private Long projectId;

    @NotNull
    private Integer cloudVersion;

    /**
     * 备份包版本, V2,V3
     */
    @NotBlank
    private String zipVersion;

    @NotNull
    private File packageFile;

    @NotBlank
    private String owner;

    private String engineMap;

    private String userNodeMap;
}
