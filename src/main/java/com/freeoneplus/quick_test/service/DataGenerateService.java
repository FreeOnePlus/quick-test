package com.freeoneplus.quick_test.service;

import com.freeoneplus.quick_test.pojo.BaseSchemaInfo;
import com.freeoneplus.quick_test.pojo.TableDataInfo;
import com.freeoneplus.quick_test.pojo.JsonFileSchemaTableInfo;

import java.util.List;

public interface DataGenerateService {
    void analysisSchemaGenerateData(String databaseName, String csvFilePath, String userName, String password, List<JsonFileSchemaTableInfo> jsonFileSchemaTableInfoList);

    void analysisSchemaGenerateData(BaseSchemaInfo baseSchemaInfo, List<TableDataInfo> tableEntry);
}
