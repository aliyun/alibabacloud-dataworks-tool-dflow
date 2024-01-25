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

package com.aliyun.dataworks.common.spec.domain.dw.nodemodel;

import lombok.Data;

/**
 * * @author 聿剑
 *
 * @date 2023/11/9
 */
@Data
public class OutputContext {
    public static final String CTX_TYPE_CONST = "CONST";
    public static final String CTX_TYPE_CONST_SYSTEM_VARIABLE = "SYSTEM_VARIABLE";
    public static final String CTX_TYPE_SCRIPT_OUTPUTS = "SCRIPT_OUTPUTS";
    public static final String CTX_TYPE_PARAMETER_NODE_OUTPUTS = "PARAMETER_NODE_OUTPUTS";

    private String key;
    /**
     * CONST,
     * SYSTEM_VARIABLE,
     * SCRIPT_OUTPUTS,
     * PARAMETER_NODE_OUTPUTS
     */
    private String ctxType;
    private String valueExpr;
}
