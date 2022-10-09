package com.freeoneplus.quick_test.pojo;

import lombok.*;

import java.util.List;

/**
 * Schema数据表级bean
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JsonFileSchemaTableInfo {
    private String tableName;
    /*
     * 要生成的csv文件数量
     */
    private int csvNum;
    /*
     * 每一个文件的数据条数
     */
    private long singleNum;
    private List<JsonFileSchemaFieldInfo> jsonFileSchemaFieldInfoList;
}
