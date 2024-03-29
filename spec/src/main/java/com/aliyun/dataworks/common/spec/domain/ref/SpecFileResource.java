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

package com.aliyun.dataworks.common.spec.domain.ref;

import com.aliyun.dataworks.common.spec.domain.SpecRefEntity;
import com.aliyun.dataworks.common.spec.domain.enums.SpecFileResourceType;
import com.aliyun.dataworks.common.spec.domain.ref.calcengine.SpecCalcEngine;
import com.aliyun.dataworks.common.spec.domain.ref.file.SpecObjectStorageFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yiwei.qyw
 * @date 2023/7/4
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SpecFileResource extends SpecRefEntity {
    private String name;
    private SpecScript script;
    /**
     * Resource type
     */
    private SpecFileResourceType type;
    /**
     * Resource file storage
     */
    private SpecObjectStorageFile file;
    /**
     * Resource calculation engine type
     */
    private SpecCalcEngine calcEngine;
    /**
     * Whether to register the resource to the calculation engine
     */
    private Boolean registerToCalcEngine;
}