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

package com.aliyun.dataworks.common.spec.parser.impl;

import java.util.Map;

import com.aliyun.dataworks.common.spec.annotation.SpecParser;
import com.aliyun.dataworks.common.spec.domain.noref.SpecCombined;
import com.aliyun.dataworks.common.spec.parser.Parser;
import com.aliyun.dataworks.common.spec.parser.SpecParserContext;
import com.aliyun.dataworks.common.spec.utils.SpecDevUtil;

/**
 * @author 聿剑
 * @date 2023/10/25
 */
@SpecParser
public class CombinedParser implements Parser<SpecCombined> {
    public static final String KEY_TYPE = "combined";

    @Override
    public SpecCombined parse(Map<String, Object> rawContext, SpecParserContext specParserContext) {
        SpecCombined specCombined = new SpecCombined();
        SpecDevUtil.setSameKeyField(rawContext, specCombined, specParserContext);
        return specCombined;
    }

    @Override
    public String getKeyType() {
        return KEY_TYPE;
    }
}