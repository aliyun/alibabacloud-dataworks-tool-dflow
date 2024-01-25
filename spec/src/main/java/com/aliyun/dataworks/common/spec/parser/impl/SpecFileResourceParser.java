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
import com.aliyun.dataworks.common.spec.domain.SpecConstants;
import com.aliyun.dataworks.common.spec.domain.enums.SpecFileResourceType;
import com.aliyun.dataworks.common.spec.domain.interfaces.LabelEnum;
import com.aliyun.dataworks.common.spec.domain.ref.SpecFileResource;
import com.aliyun.dataworks.common.spec.parser.SpecParserContext;
import org.apache.commons.collections4.MapUtils;

/**
 * SpecFileResourceParser
 *
 * @author 聿剑
 * @date 2023/11/30
 * @see SpecFileResourceParser
 */
@SpecParser
public class SpecFileResourceParser extends DefaultSpecParser<SpecFileResource> {
    public SpecFileResourceParser() {
        super(SpecFileResource.class);
    }

    @Override
    public SpecFileResource parse(Map<String, Object> rawContext, SpecParserContext specParserContext) {
        SpecFileResource res = super.parse(rawContext, specParserContext);
        String typeStr = MapUtils.getString(rawContext, SpecConstants.SPEC_KEY_TYPE);
        res.setType(LabelEnum.getByLabel(SpecFileResourceType.class, typeStr));
        return res;
    }
}
