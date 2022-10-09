package com.freeoneplus.quick_test.service.impl;

import com.freeoneplus.quick_test.pojo.TableDataInfo;
import com.freeoneplus.quick_test.service.LoadDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoadDataServiceImpl implements LoadDataService {

    @Override
    public void dorisStreamLoad(String feHost, int feHttpPort, String dbname, String tableName, long batchNum, ArrayList<TableDataInfo> datas) {

    }
}
