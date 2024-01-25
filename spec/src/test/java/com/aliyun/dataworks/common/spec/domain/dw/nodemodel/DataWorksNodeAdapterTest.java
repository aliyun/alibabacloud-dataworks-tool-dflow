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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import com.aliyun.dataworks.common.spec.SpecUtil;
import com.aliyun.dataworks.common.spec.domain.DataWorksWorkflowSpec;
import com.aliyun.dataworks.common.spec.domain.Specification;
import com.aliyun.dataworks.common.spec.domain.enums.DependencyType;
import com.aliyun.dataworks.common.spec.domain.noref.SpecDepend;
import com.aliyun.dataworks.common.spec.domain.noref.SpecFlowDepend;
import com.aliyun.dataworks.common.spec.domain.ref.SpecNode;
import com.aliyun.dataworks.common.spec.domain.ref.SpecNodeOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 聿剑
 * @date 2023/11/10
 */
@Slf4j
public class DataWorksNodeAdapterTest {
    @Test
    public void test1() throws IOException {
        String spec = IOUtils.toString(
            Objects.requireNonNull(DataWorksNodeAdapterTest.class.getClassLoader().getResource("nodemodel/assignment.json")),
            StandardCharsets.UTF_8);

        System.out.println(spec);
        Specification<DataWorksWorkflowSpec> specObj = SpecUtil.parseToDomain(spec);
        DataWorksWorkflowSpec specification = specObj.getSpec();
        Assert.assertNotNull(specification);

        Assert.assertNotNull(specification.getNodes());
        SpecNode node = specification.getNodes().get(0);

        DataWorksNodeAdapter adapter = new DataWorksNodeAdapter(specObj, node);
        System.out.println("code: " + adapter.getCode());
        Assert.assertNotNull(adapter.getCode());
        Assert.assertTrue(adapter.getCode().contains("language"));

        System.out.println("inputs: " + adapter.getInputs());
        System.out.println("outputs: " + adapter.getOutputs());

        Assert.assertTrue(CollectionUtils.isNotEmpty(adapter.getInputs()));
        Assert.assertEquals(2, CollectionUtils.size(adapter.getInputs()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(adapter.getOutputs()));
        Assert.assertEquals(1, CollectionUtils.size(adapter.getOutputs()));

        System.out.println("context inputs: " + adapter.getInputContexts());
        System.out.println("context outputs: " + adapter.getOutputContexts());
        Assert.assertTrue(CollectionUtils.isNotEmpty(adapter.getInputContexts()));
        Assert.assertTrue(CollectionUtils.isNotEmpty(adapter.getOutputContexts()));

        System.out.println(SpecUtil.writeToSpec(specObj));
    }

    @Test
    public void testGetDependentType() throws IOException {
        String spec = IOUtils.toString(
            Objects.requireNonNull(DataWorksNodeAdapterTest.class.getClassLoader().getResource("nodemodel/all_depend_types.json")),
            StandardCharsets.UTF_8);

        System.out.println(spec);
        Specification<DataWorksWorkflowSpec> specObj = SpecUtil.parseToDomain(spec);
        Assert.assertNotNull(specObj);

        DataWorksWorkflowSpec specification = specObj.getSpec();
        Assert.assertNotNull(specification);
        Assert.assertEquals(1, CollectionUtils.size(specification.getNodes()));

        DwNodeDependentTypeInfo depInfo = getDwNodeDependentTypeInfo(specObj);
        System.out.println("depInfo: {}" + depInfo);
        Assert.assertNotNull(depInfo);
        Assert.assertEquals(DwNodeDependentTypeInfo.USER_DEFINE_AND_SELF, depInfo.getDependentType());
        Assert.assertEquals(3, CollectionUtils.size(depInfo.getDependentNodeIdList()));
    }

    private DwNodeDependentTypeInfo getDwNodeDependentTypeInfo(Specification<DataWorksWorkflowSpec> specification) {
        DataWorksNodeAdapter adapter = new DataWorksNodeAdapter(specification, specification.getSpec().getNodes().get(0));
        return adapter.getDependentType((specNodeOutputs) -> {
            System.out.println(ListUtils.emptyIfNull(specNodeOutputs).stream().map(SpecNodeOutput::getData).collect(Collectors.toList()));
            return ListUtils.emptyIfNull(specNodeOutputs).stream().map(SpecNodeOutput::getData)
                .map(String::hashCode)
                .map(Math::abs)
                .map(Long::valueOf)
                .collect(Collectors.toList());
        });
    }

    @Test
    public void testDowhile() throws IOException {
        String spec = IOUtils.toString(
            Objects.requireNonNull(DataWorksNodeAdapterTest.class.getClassLoader().getResource("nodemodel/dowhile.json")),
            StandardCharsets.UTF_8);

        System.out.println(spec);
        Specification<DataWorksWorkflowSpec> specObj = SpecUtil.parseToDomain(spec);
        Assert.assertNotNull(specObj);

        DataWorksWorkflowSpec specification = specObj.getSpec();
        Assert.assertNotNull(specification);
        Assert.assertEquals(1, CollectionUtils.size(specification.getNodes()));

        SpecNode dowhile = specification.getNodes().get(0);
        Assert.assertNotNull(dowhile);

        Assert.assertNotNull(dowhile.getDoWhile());
        Assert.assertNotNull(dowhile.getDoWhile().getSpecWhile());
        Assert.assertNotNull(dowhile.getDoWhile().getNodes());
    }

    @Test
    public void testForeach() throws IOException {
        String spec = IOUtils.toString(
            Objects.requireNonNull(DataWorksNodeAdapterTest.class.getClassLoader().getResource("nodemodel/foreach.json")),
            StandardCharsets.UTF_8);

        System.out.println(spec);
        Specification<DataWorksWorkflowSpec> specObj = SpecUtil.parseToDomain(spec);
        Assert.assertNotNull(specObj);

        DataWorksWorkflowSpec specification = specObj.getSpec();
        Assert.assertNotNull(specification);
        Assert.assertEquals(1, CollectionUtils.size(specification.getNodes()));

        SpecNode foreach = specification.getNodes().get(0);
        Assert.assertNotNull(foreach);

        Assert.assertNotNull(foreach.getForeach());
        Assert.assertNotNull(foreach.getForeach().getNodes());
    }

    @Test
    public void testShell() throws IOException {
        String spec = IOUtils.toString(
            Objects.requireNonNull(DataWorksNodeAdapterTest.class.getClassLoader().getResource("nodemodel/dide_shell.json")),
            StandardCharsets.UTF_8);

        System.out.println(spec);
        Specification<DataWorksWorkflowSpec> specObj = SpecUtil.parseToDomain(spec);
        Assert.assertNotNull(specObj);

        DataWorksWorkflowSpec specification = specObj.getSpec();
        Assert.assertNotNull(specification);
        Assert.assertEquals(1, CollectionUtils.size(specification.getNodes()));

        SpecNode shellNode = specification.getNodes().get(0);
        Assert.assertNotNull(shellNode);

        DataWorksNodeAdapter adapter = new DataWorksNodeAdapter(specObj, shellNode);
        log.info("para value: {}", adapter.getParaValue());
        Assert.assertNotNull(adapter.getParaValue());
        Assert.assertEquals("111111 222222", adapter.getParaValue());
    }

    @Test
    public void testPyOdps2() throws IOException {
        String spec = IOUtils.toString(
            Objects.requireNonNull(DataWorksNodeAdapterTest.class.getClassLoader().getResource("nodemodel/pyodps2.json")),
            StandardCharsets.UTF_8);

        System.out.println(spec);
        Specification<DataWorksWorkflowSpec> specObj = SpecUtil.parseToDomain(spec);
        Assert.assertNotNull(specObj);

        DataWorksWorkflowSpec specification = specObj.getSpec();
        Assert.assertNotNull(specification);
        Assert.assertEquals(1, CollectionUtils.size(specification.getNodes()));

        SpecNode shellNode = specification.getNodes().get(0);
        Assert.assertNotNull(shellNode);

        shellNode.setIgnoreBranchConditionSkip(true);
        shellNode.setTimeout(0);

        DataWorksNodeAdapter adapter = new DataWorksNodeAdapter(specObj, shellNode);
        log.info("para value: {}", adapter.getParaValue());
        Assert.assertNotNull(adapter.getParaValue());
        Assert.assertEquals("2=222222 1=111111", adapter.getParaValue());

        log.info("extConfig: {}", adapter.getExtConfig());
        Assert.assertNotNull(adapter.getExtConfig());
        Assert.assertTrue(adapter.getExtConfig().contains(DataWorksNodeAdapter.IGNORE_BRANCH_CONDITION_SKIP));
        Assert.assertFalse(adapter.getExtConfig().contains(DataWorksNodeAdapter.TIMEOUT));
    }

    @Test
    public void testGetDependentTypeWithOutputs() {
        Specification<DataWorksWorkflowSpec> spec = new Specification<>();
        DataWorksWorkflowSpec dwSpec = new DataWorksWorkflowSpec();
        SpecFlowDepend flow = new SpecFlowDepend();
        SpecNode nodeId = new SpecNode();
        nodeId.setId("1");
        flow.setNodeId(nodeId);
        SpecDepend dep = new SpecDepend();
        dep.setType(DependencyType.CROSS_CYCLE_OTHER_NODE);
        SpecNodeOutput out = new SpecNodeOutput();
        out.setData("output1");
        dep.setOutput(out);
        flow.setDepends(Collections.singletonList(dep));
        dwSpec.setFlow(Collections.singletonList(flow));
        spec.setSpec(dwSpec);
        SpecNode node = new SpecNode();
        node.setId("1");
        DataWorksNodeAdapter dataWorksNodeAdapter = new DataWorksNodeAdapter(spec, node);
        DwNodeDependentTypeInfo info = dataWorksNodeAdapter.getDependentType(null);
        Assert.assertNotNull(info);
        Assert.assertEquals(info.getDependentType(), DwNodeDependentTypeInfo.USER_DEFINE);
        Assert.assertNotNull(info.getDependentNodeOutputList());
        Assert.assertTrue(info.getDependentNodeOutputList().contains("output1"));
    }

    @Test
    public void test() {
        String spec = "{\n"
            + "        \"version\": \"1.1.0\",\n"
            + "        \"kind\": \"CycleWorkflow\",\n"
            + "        \"spec\": {\n"
            + "            \"nodes\": [\n"
            + "                {\n"
            + "                    \"recurrence\": \"Normal\",\n"
            + "                    \"id\": \"8643439\",\n"
            + "                    \"timeout\": 0,\n"
            + "                    \"instanceMode\": \"T+1\",\n"
            + "                    \"rerunMode\": \"Allowed\",\n"
            + "                    \"rerunTimes\": 0,\n"
            + "                    \"rerunInterval\": 0,\n"
            + "                    \"script\": {\n"
            + "                        \"path\": \"业务流程/DataStudio弹内/数据集成/alisa_task_history\",\n"
            + "                        \"runtime\": {\n"
            + "                            \"command\": \"DI\"\n"
            + "                        },\n"
            + "                        \"parameters\": [\n"
            + "                            {\n"
            + "                                \"name\": \"-p\\\"-Dbizdate\",\n"
            + "                                \"artifactType\": \"Variable\",\n"
            + "                                \"scope\": \"NodeParameter\",\n"
            + "                                \"type\": \"System\",\n"
            + "                                \"value\": \"$bizdate\"\n"
            + "                            },\n"
            + "                            {\n"
            + "                                \"name\": \"-Denv_path\",\n"
            + "                                \"artifactType\": \"Variable\",\n"
            + "                                \"scope\": \"NodeParameter\",\n"
            + "                                \"type\": \"System\",\n"
            + "                                \"value\": \"$env_path\"\n"
            + "                            },\n"
            + "                            {\n"
            + "                                \"name\": \"-Dhour\",\n"
            + "                                \"artifactType\": \"Variable\",\n"
            + "                                \"scope\": \"NodeParameter\",\n"
            + "                                \"type\": \"System\",\n"
            + "                                \"value\": \"$hour\"\n"
            + "                            },\n"
            + "                            {\n"
            + "                                \"name\": \"-Dtoday\",\n"
            + "                                \"artifactType\": \"Variable\",\n"
            + "                                \"scope\": \"NodeParameter\",\n"
            + "                                \"type\": \"System\",\n"
            + "                                \"value\": \"${yyyymmdd+1}\\\"\"\n"
            + "                            }\n"
            + "                        ]\n"
            + "                    },\n"
            + "                    \"trigger\": {\n"
            + "                        \"type\": \"Scheduler\",\n"
            + "                        \"cron\": \"00 03 06 * * ?\",\n"
            + "                        \"startTime\": \"1970-01-01 00:00:00\",\n"
            + "                        \"endTime\": \"9999-01-01 00:00:00\",\n"
            + "                        \"timezone\": \"Asia/Shanghai\"\n"
            + "                    },\n"
            + "                    \"runtimeResource\": {\n"
            + "                        \"resourceGroup\": \"group_20051853\",\n"
            + "                        \"resourceGroupId\": \"6\"\n"
            + "                    },\n"
            + "                    \"name\": \"alisa_task_history\",\n"
            + "                    \"owner\": \"075180\",\n"
            + "                    \"inputs\": {\n"
            + "                        \"nodeOutputs\": [\n"
            + "                            {\n"
            + "                                \"data\": \"dataworks_analyze_root\",\n"
            + "                                \"artifactType\": \"NodeOutput\"\n"
            + "                            }\n"
            + "                        ]\n"
            + "                    },\n"
            + "                    \"outputs\": {\n"
            + "                        \"nodeOutputs\": [\n"
            + "                            {\n"
            + "                                \"data\": \"dataworks_analyze.8643439_out\",\n"
            + "                                \"artifactType\": \"NodeOutput\"\n"
            + "                            }\n"
            + "                        ]\n"
            + "                    }\n"
            + "                }\n"
            + "            ],\n"
            + "            \"flow\": [\n"
            + "                {\n"
            + "                    \"nodeId\": \"8643439\",\n"
            + "                    \"depends\": [\n"
            + "                        {\n"
            + "                            \"type\": \"Normal\",\n"
            + "                            \"output\": \"dataworks_analyze_root\"\n"
            + "                        }\n"
            + "                    ]\n"
            + "                }\n"
            + "            ]\n"
            + "        }\n"
            + "    }";
        Specification<DataWorksWorkflowSpec> s = SpecUtil.parseToDomain(spec);
        DataWorksNodeAdapter adapter = new DataWorksNodeAdapter(s, s.getSpec().getNodes().get(0));
        System.out.println(adapter.getParaValue());
    }

    @Test
    public void testNoKvVariableExpression() {
        String spec = "{\n"
            + "        \"version\": \"1.1.0\",\n"
            + "        \"kind\": \"CycleWorkflow\",\n"
            + "        \"spec\": {\n"
            + "            \"nodes\": [\n"
            + "                {\n"
            + "                    \"recurrence\": \"Normal\",\n"
            + "                    \"id\": \"8643439\",\n"
            + "                    \"timeout\": 0,\n"
            + "                    \"instanceMode\": \"T+1\",\n"
            + "                    \"rerunMode\": \"Allowed\",\n"
            + "                    \"rerunTimes\": 0,\n"
            + "                    \"rerunInterval\": 0,\n"
            + "                    \"script\": {\n"
            + "                        \"path\": \"业务流程/DataStudio弹内/数据集成/alisa_task_history\",\n"
            + "                        \"runtime\": {\n"
            + "                            \"command\": \"DI\"\n"
            + "                        },\n"
            + "                        \"parameters\": [\n"
            + "                            {\n"
            + "                                \"name\": \"-\",\n"
            + "                                \"artifactType\": \"Variable\",\n"
            + "                                \"scope\": \"NodeParameter\",\n"
            + "                                \"type\": \"NoKvVariableExpression\",\n"
            + "                                \"value\": \" -p\\\"-Dbizdate=$bizdate -Denv_path=$env_path -Dhour=$hour -Dendtime=$[yyyymmdd hh24] "
            + "-Dbegintime=$[yyyymmdd hh24 - 1/24] -Dgmtdate=$gmtdate\\\"\"\n"
            + "                            }\n"
            + "                        ]\n"
            + "                    },\n"
            + "                    \"trigger\": {\n"
            + "                        \"type\": \"Scheduler\",\n"
            + "                        \"cron\": \"00 03 06 * * ?\",\n"
            + "                        \"startTime\": \"1970-01-01 00:00:00\",\n"
            + "                        \"endTime\": \"9999-01-01 00:00:00\",\n"
            + "                        \"timezone\": \"Asia/Shanghai\"\n"
            + "                    },\n"
            + "                    \"runtimeResource\": {\n"
            + "                        \"resourceGroup\": \"group_20051853\",\n"
            + "                        \"resourceGroupId\": \"6\"\n"
            + "                    },\n"
            + "                    \"name\": \"alisa_task_history\",\n"
            + "                    \"owner\": \"075180\",\n"
            + "                    \"inputs\": {\n"
            + "                        \"nodeOutputs\": [\n"
            + "                            {\n"
            + "                                \"data\": \"dataworks_analyze_root\",\n"
            + "                                \"artifactType\": \"NodeOutput\"\n"
            + "                            }\n"
            + "                        ]\n"
            + "                    },\n"
            + "                    \"outputs\": {\n"
            + "                        \"nodeOutputs\": [\n"
            + "                            {\n"
            + "                                \"data\": \"dataworks_analyze.8643439_out\",\n"
            + "                                \"artifactType\": \"NodeOutput\"\n"
            + "                            }\n"
            + "                        ]\n"
            + "                    }\n"
            + "                }\n"
            + "            ],\n"
            + "            \"flow\": [\n"
            + "                {\n"
            + "                    \"nodeId\": \"8643439\",\n"
            + "                    \"depends\": [\n"
            + "                        {\n"
            + "                            \"type\": \"Normal\",\n"
            + "                            \"output\": \"dataworks_analyze_root\"\n"
            + "                        }\n"
            + "                    ]\n"
            + "                }\n"
            + "            ]\n"
            + "        }\n"
            + "    }";
        Specification<DataWorksWorkflowSpec> s = SpecUtil.parseToDomain(spec);
        DataWorksNodeAdapter adapter = new DataWorksNodeAdapter(s, s.getSpec().getNodes().get(0));
        System.out.println(adapter.getParaValue());
        Assert.assertEquals(
            " -p\"-Dbizdate=$bizdate -Denv_path=$env_path -Dhour=$hour -Dendtime=$[yyyymmdd hh24] -Dbegintime=$[yyyymmdd hh24 - 1/24] "
                + "-Dgmtdate=$gmtdate\"",
            adapter.getParaValue());
    }
}
