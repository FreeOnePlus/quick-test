package com.freeoneplus.quick_test.service.impl;

import com.freeoneplus.quick_test.common.JsonFileSchemaInit;
import com.freeoneplus.quick_test.pojo.TableDataInfo;
import com.freeoneplus.quick_test.pojo.JsonFileSchemaDatabaseInfo;
import com.freeoneplus.quick_test.pojo.JsonFileSchemaTableInfo;
import com.freeoneplus.quick_test.pojo.UrlSchemaInfo;
import com.freeoneplus.quick_test.service.DataGenerateService;
import com.freeoneplus.quick_test.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemaServiceImpl implements SchemaService {
    @Autowired
    JsonFileSchemaInit jsonFileSchemaInit;

    @Autowired
    DataGenerateService dataGenerateService;

    /**
     * 读取指定JSON格式的Schema文件进行解析
     *
     * @param schemaFilePath
     */
    @Override
    public void analysisJsonFileSchema(String schemaFilePath) {
        JsonFileSchemaDatabaseInfo jsonFileSchemaDatabaseInfo = jsonFileSchemaInit.readSchemaFile(schemaFilePath);
        String username = jsonFileSchemaDatabaseInfo.getUsername();
        String password = jsonFileSchemaDatabaseInfo.getPassword();
        String dbName = jsonFileSchemaDatabaseInfo.getDbName();
        String csvFilePath = jsonFileSchemaDatabaseInfo.getCsvFilePath();
        List<JsonFileSchemaTableInfo> jsonFileSchemaTableInfoList = jsonFileSchemaDatabaseInfo.getJsonFileSchemaTableInfoList();
        dataGenerateService.analysisSchemaGenerateData(dbName, csvFilePath, username, password, jsonFileSchemaTableInfoList);
    }

    /**
     * 读取指定Doris库表进行解析
     *
     * @param urlSchemaInfo
     */
    @Override
    public void analysisUrlSchema(UrlSchemaInfo urlSchemaInfo) {
        String dbName = urlSchemaInfo.getDbName();
        String feHost = urlSchemaInfo.getFeHost();
        String username = urlSchemaInfo.getUsername();
        String password = urlSchemaInfo.getPassword();
        int feHttpPort = urlSchemaInfo.getFeHttpPort();
        List<TableDataInfo> tableList = urlSchemaInfo.getTableList();
        dataGenerateService.analysisSchemaGenerateData(dbName, feHost, feHttpPort, username, password, tableList);
    }


}
