package com.freeoneplus.quick_test.service;

import com.freeoneplus.quick_test.pojo.TableDataInfo;

import java.util.ArrayList;

public interface LoadDataService {

    void dorisStreamLoad(String feHost, int feHttpPort, String dbname, String tableName, long batchNum, ArrayList<TableDataInfo> datas);
}
