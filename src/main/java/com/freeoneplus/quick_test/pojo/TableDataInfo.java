package com.freeoneplus.quick_test.pojo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TableDataInfo {
    private String tableName;
    private Long totalNum;
    private List<FieldDataTypeInfo> fieldDataTypeInfoList;

    public TableDataInfo(String tableName, Long totalNum) {
        this.tableName = tableName;
        this.totalNum = totalNum;
    }
}
