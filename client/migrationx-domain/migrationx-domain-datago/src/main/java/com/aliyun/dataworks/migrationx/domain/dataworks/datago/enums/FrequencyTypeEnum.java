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

package com.aliyun.dataworks.migrationx.domain.dataworks.datago.enums;

import com.aliyun.dataworks.migrationx.domain.dataworks.objects.types.CycleType;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author qiwei.hqw
 * @version 1.0.0
 * @description
 * @createTime 2020-04-07
 */
public enum FrequencyTypeEnum {
    /**
     * 一次性任务
     */
    ONE_TIME("ONE_TIME"),
    /**
     * 准实时分钟调度
     */
    QUASI("QUASI"),
    /**
     * 按小时/分钟更新
     */
    HOUR("HOUR"),
    /**
     * 按天更新
     */
    DAY("DAY");

    private final String type;

    public static FrequencyTypeEnum fromType(String type) {
        return ALL_VALUES.get(type);
    }

    FrequencyTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static CycleType toCycleType(String type) {
        if (QUASI.type.equals(type) || HOUR.type.equals(type)) {
            return CycleType.NOT_DAY;
        } else {
            return CycleType.DAY;
        }
    }

    private static final Map<String, FrequencyTypeEnum> ALL_VALUES = Maps.newHashMap();

    static {
        for (FrequencyTypeEnum type : FrequencyTypeEnum.values()) {
            ALL_VALUES.put(type.getType(), type);
        }
    }
}
