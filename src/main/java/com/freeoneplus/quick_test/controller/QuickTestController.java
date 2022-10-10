package com.freeoneplus.quick_test.controller;

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
    public void test(){
        ArrayList<TableDataInfo> list = new ArrayList<>();
        list.add(new TableDataInfo("tb_td_app",1000L));
        dataGenerateService.analysisSchemaGenerateData("test","storage.freeoneplus.com",
                9030,"root","",list);
    }
}