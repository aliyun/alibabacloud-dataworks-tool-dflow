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

package com.aliyun.dataworks.migrationx.domain.dataworks.objects.serialize;

import com.aliyun.migrationx.common.utils.GsonUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sam.liux
 * @date 2020/08/25
 */
public class String2JsonObjectConverter implements Converter {
    @Override
    public Object convert(Object value) {
        if (value instanceof String && StringUtils.isNotBlank((String)value)) {
            value = GsonUtils.fromJsonString((String)value, new TypeToken<JsonObject>(){}.getType());
        }
        return value;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(JsonObject.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(JsonObject.class);
    }
}
