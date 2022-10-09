package com.freeoneplus.quick_test.pojo;

import lombok.*;

import java.util.List;

/**
 * Schema数据库级bean
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class JsonFileSchemaDatabaseInfo extends BaseSchemaInfo {
    /*
     * 是否首行为列名
     */
    private boolean firstLineTitle = false;
    /*
     * 生成的数据文件目标路径
     */
    private String csvFilePath;
    /*
     * Doris 目标数据库
     */
    private String dorisDatabaseName;
    /*
     * 表结构
     */
    private List<JsonFileSchemaTableInfo> jsonFileSchemaTableInfoList;

}
