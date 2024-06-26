package com.aliyun.dataworks.migrationx.domain.dataworks.service.converter;

import com.aliyun.dataworks.common.spec.SpecUtil;
import com.aliyun.dataworks.common.spec.domain.DataWorksWorkflowSpec;
import com.aliyun.dataworks.common.spec.domain.Specification;
import com.aliyun.dataworks.migrationx.domain.dataworks.objects.entity.client.FileDetail;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 戒迷
 * @date 2024/4/16
 */
public class DataWorksSpecNodeConverterTest {

    @Test
    public void testHandleNodeSpec() throws Exception {
        String specStr = "{\n"
            + "\t\"version\":\"1.1.0\",\n"
            + "\t\"kind\":\"CycleWorkflow\",\n"
            + "\t\"spec\":{\n"
            + "\t\t\"nodes\":[\n"
            + "\t\t\t{\n"
            + "\t\t\t\t\"recurrence\":\"Normal\",\n"
            + "\t\t\t\t\"id\":\"26248077\",\n"
            + "\t\t\t\t\"timeout\":0,\n"
            + "\t\t\t\t\"instanceMode\":\"T+1\",\n"
            + "\t\t\t\t\"rerunMode\":\"Allowed\",\n"
            + "\t\t\t\t\"rerunTimes\":0,\n"
            + "\t\t\t\t\"rerunInterval\":120000,\n"
            + "\t\t\t\t\"datasource\":{\n"
            + "\t\t\t\t\t\"name\":\"odps_first\",\n"
            + "\t\t\t\t\t\"type\":\"odps\"\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"script\":{\n"
            + "\t\t\t\t\t\"path\":\"业务流程/建模引擎/MaxCompute/数据开发/config_driver数据同步/model_table\",\n"
            + "\t\t\t\t\t\"runtime\":{\n"
            + "\t\t\t\t\t\t\"command\":\"ODPS_SQL\"\n"
            + "\t\t\t\t\t},\n"
            + "\t\t\t\t\t\"parameters\":[\n"
            + "\t\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\t\"name\":\"bizdate\",\n"
            + "\t\t\t\t\t\t\t\"artifactType\":\"Variable\",\n"
            + "\t\t\t\t\t\t\t\"scope\":\"NodeParameter\",\n"
            + "\t\t\t\t\t\t\t\"type\":\"System\",\n"
            + "\t\t\t\t\t\t\t\"value\":\"$[yyyymmdd-1]\"\n"
            + "\t\t\t\t\t\t}\n"
            + "\t\t\t\t\t]\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"trigger\":{\n"
            + "\t\t\t\t\t\"type\":\"Scheduler\",\n"
            + "\t\t\t\t\t\"cron\":\"00 29 00 * * ?\",\n"
            + "\t\t\t\t\t\"startTime\":\"1970-01-01 00:00:00\",\n"
            + "\t\t\t\t\t\"endTime\":\"9999-01-01 15:12:51\",\n"
            + "\t\t\t\t\t\"timezone\":\"Asia/Shanghai\"\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"runtimeResource\":{\n"
            + "\t\t\t\t\t\"resourceGroup\":\"group_20051853\",\n"
            + "\t\t\t\t\t\"resourceGroupId\":\"20051853\"\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"name\":\"model_table\",\n"
            + "\t\t\t\t\"owner\":\"370260\",\n"
            + "\t\t\t\t\"inputs\":{\n"
            + "\t\t\t\t\t\"nodeOutputs\":[\n"
            + "\t\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\t\"data\":\"dataworks_meta.dwd_base_config_driver_data_jsondata_df\",\n"
            + "\t\t\t\t\t\t\t\"artifactType\":\"NodeOutput\"\n"
            + "\t\t\t\t\t\t}\n"
            + "\t\t\t\t\t]\n"
            + "\t\t\t\t},\n"
            + "\t\t\t\t\"outputs\":{\n"
            + "\t\t\t\t\t\"nodeOutputs\":[\n"
            + "\t\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\t\"data\":\"dataworks_analyze.26248077_out\",\n"
            + "\t\t\t\t\t\t\t\"artifactType\":\"NodeOutput\"\n"
            + "\t\t\t\t\t\t},\n"
            + "\t\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\t\"data\":\"dataworks_analyze.model_table_config_driver\",\n"
            + "\t\t\t\t\t\t\t\"artifactType\":\"NodeOutput\"\n"
            + "\t\t\t\t\t\t}\n"
            + "\t\t\t\t\t]\n"
            + "\t\t\t\t}\n"
            + "\t\t\t}\n"
            + "\t\t],\n"
            + "\t\t\"flow\":[\n"
            + "\t\t\t{\n"
            + "\t\t\t\t\"nodeId\":\"26248077\",\n"
            + "\t\t\t\t\"depends\":[\n"
            + "\t\t\t\t\t{\n"
            + "\t\t\t\t\t\t\"type\":\"Normal\",\n"
            + "\t\t\t\t\t\t\"output\":\"dataworks_meta.dwd_base_config_driver_data_jsondata_df\"\n"
            + "\t\t\t\t\t}\n"
            + "\t\t\t\t]\n"
            + "\t\t\t}\n"
            + "\t\t]\n"
            + "\t}\n"
            + "}";
        Specification<DataWorksWorkflowSpec> spec = SpecUtil.parseToDomain(specStr);
        FileDetail result = DataWorksSpecNodeConverter.nodeSpecToFileDetail(spec);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getFile());
        Assert.assertNotNull(result.getNodeCfg());
    }

    @Test
    public void testHandleResourceSpec() throws Exception {
        String specStr = "{\n"
            + "  \"version\":\"1.1.0\",\n"
            + "  \"kind\":\"CycleWorkflow\",\n"
            + "  \"spec\":{\n"
            + "    \"fileResources\":[\n"
            + "      {\n"
            + "        \"name\":\"mc.py\",\n"
            + "        \"id\":\"6300484767235409791\",\n"
            + "        \"script\":{\n"
            + "          \"path\":\"戒迷/资源/mc.py\",\n"
            + "          \"runtime\":{\n"
            + "            \"command\":\"ODPS_PYTHON\"\n"
            + "          }\n"
            + "        },\n"
            + "        \"runtimeResource\":{\n"
            + "          \"id\":\"5623679673296125496\",\n"
            + "          \"resourceGroup\":\"group_2\",\n"
            + "          \"resourceGroupId\":\"2\"\n"
            + "        },\n"
            + "        \"type\":\"python\",\n"
            + "        \"file\":{\n"
            + "          \"storage\":{\n"
            + "             \"type\": \"oss\"\n"
            + "          }\n"
            + "        },\n"
            + "        \"datasource\":{\n"
            + "          \"name\":\"odps_first\",\n"
            + "          \"type\":\"odps\"\n"
            + "        },\n"
            + "        \"metadata\":{\n"
            + "          \"owner\":\"370260\"\n"
            + "        }\n"
            + "      }\n"
            + "    ]\n"
            + "  }\n"
            + "}";
        Specification<DataWorksWorkflowSpec> spec = SpecUtil.parseToDomain(specStr);
        FileDetail result = DataWorksSpecNodeConverter.resourceSpecToFileDetail(spec);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getFile());
        Assert.assertNotNull(result.getNodeCfg());
    }

    @Test
    public void testHandleFunction() throws Exception {
        String specStr = "{\n"
            + "  \"version\":\"1.1.0\",\n"
            + "  \"kind\":\"CycleWorkflow\",\n"
            + "  \"spec\":{\n"
            + "    \"functions\":[\n"
            + "      {\n"
            + "        \"name\":\"odps_function\",\n"
            + "        \"id\":\"6615080895197716196\",\n"
            + "        \"script\":{\n"
            + "          \"path\":\"戒迷/函数/odps_function\",\n"
            + "          \"runtime\":{\n"
            + "            \"command\":\"ODPS_FUNCTION\"\n"
            + "          }\n"
            + "        },\n"
            + "        \"type\":\"other\",\n"
            + "        \"className\":\"main\",\n"
            + "        \"datasource\":{\n"
            + "          \"name\":\"odps_first\",\n"
            + "          \"type\":\"odps\"\n"
            + "        },\n"
            + "        \"runtimeResource\":{\n"
            + "          \"resourceGroup\":\"group_2\",\n"
            + "          \"id\":\"5623679673296125496\",\n"
            + "          \"resourceGroupId\":\"2\"\n"
            + "        },\n"
            + "        \"resourceType\":\"file\",\n"
            + "        \"metadata\":{\n"
            + "          \"owner\":\"370260\"\n"
            + "        },\n"
            + "        \"fileResources\":[\n"
            + "          {\n"
            + "            \"name\":\"mc.py\"\n"
            + "          }\n"
            + "        ]\n"
            + "      }\n"
            + "    ]\n"
            + "  }\n"
            + "}";
        Specification<DataWorksWorkflowSpec> spec = SpecUtil.parseToDomain(specStr);
        FileDetail result = DataWorksSpecNodeConverter.functionSpecToFileDetail(spec);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getFile());
        Assert.assertNotNull(result.getNodeCfg());
    }
}

// Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme