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

package com.aliyun.dataworks.migrationx.transformer.core.sqoop;

public class MainframeConfiguration {
    public static final String MAINFRAME_INPUT_DATASET_NAME
        = "mapreduce.mainframe.input.dataset.name";

    public static final String MAINFRAME_INPUT_DATASET_TYPE
        = "mapreduce.mainframe.input.dataset.type";
    public static final String MAINFRAME_INPUT_DATASET_TYPE_SEQUENTIAL
        = "s";
    public static final String MAINFRAME_INPUT_DATASET_TYPE_GDG
        = "g";
    public static final String MAINFRAME_INPUT_DATASET_TYPE_PARTITIONED
        = "p";
    public static final String MAINFRAME_INPUT_DATASET_TAPE = "mainframe.input.dataset.tape";

    public static final String MAINFRAME_FTP_FILE_ENTRY_PARSER_CLASSNAME
        = "org.apache.sqoop.mapreduce.mainframe.MainframeFTPFileEntryParser";
}
