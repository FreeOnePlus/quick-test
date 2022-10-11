package com.freeoneplus.quick_test.controller;

import com.freeoneplus.quick_test.pojo.BaseSchemaInfo;
import com.freeoneplus.quick_test.pojo.TableDataInfo;
import com.freeoneplus.quick_test.service.DataGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class QuickTestController {

    @Autowired
    DataGenerateService dataGenerateService;

    @RequestMapping("/test")
    public void test() {
        ArrayList<TableDataInfo> list = new ArrayList<>();
        list.add(new TableDataInfo("tb_td_app", 10000000L));
        long start = System.currentTimeMillis();
        dataGenerateService.analysisSchemaGenerateData(new BaseSchemaInfo("test", "192.168.31.241",
                9030,"192.168.31.241", 8040, "root", ""), list);
        long end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start) / 1000 + "秒");
    }
}
