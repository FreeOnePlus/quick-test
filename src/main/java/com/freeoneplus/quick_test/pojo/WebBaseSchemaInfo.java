package com.freeoneplus.quick_test.pojo;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WebBaseSchemaInfo extends BaseSchemaInfo{
    private String tableName = "";
    private Long totalNum = 0L;

    public WebBaseSchemaInfo(String dbName, String feHost, int fePort, String beHost, int bePort, String username, String password, String tableName, Long totalNum) {
        super(dbName, feHost, fePort, beHost, bePort, username, password);
        this.tableName = tableName;
        this.totalNum = totalNum;
    }
}
