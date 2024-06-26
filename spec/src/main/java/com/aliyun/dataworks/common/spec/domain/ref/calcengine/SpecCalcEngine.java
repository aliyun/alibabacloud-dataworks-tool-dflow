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

package com.aliyun.dataworks.common.spec.domain.ref.calcengine;

import com.aliyun.dataworks.common.spec.domain.SpecRefEntity;
import com.aliyun.dataworks.common.spec.domain.dw.types.CalcEngineType;
import com.aliyun.dataworks.common.spec.domain.ref.SpecDatasource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * CalcEngine
 *
 * @author 聿剑
 * @date 2023/11/30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpecCalcEngine extends SpecRefEntity {
    private CalcEngineType type;
    private SpecDatasource datasource;
    private String endpoint;
    /**
     * mc场景下database代指mcProject
     */
    private String database;
    private String schema;
    private SpecCalcEngineVersion version;
}
