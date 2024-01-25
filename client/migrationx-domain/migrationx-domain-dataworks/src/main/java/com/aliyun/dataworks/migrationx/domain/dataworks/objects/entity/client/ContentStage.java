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

/**
 * @author sam.liux
 * @date 2020/04/27
 */
public enum ContentStage {
    /**
     * 最新
     */
    LATEST,

    /**
     * 最近提交
     */
    LATEST_SUBMITTED,

    /**
     * 最近发布
     */
    LATEST_DEPLOYED
}
