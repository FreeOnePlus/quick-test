package com.freeoneplus.quick_test.service;

import com.freeoneplus.quick_test.pojo.BaseSchemaInfo;

import java.util.ArrayList;

public interface LoadDataService {

    boolean dorisCsvStreamLoad(BaseSchemaInfo baseSchemaInfo, String tableName, ArrayList<String> datas);
}
