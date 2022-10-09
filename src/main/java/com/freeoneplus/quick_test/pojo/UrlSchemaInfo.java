package com.freeoneplus.quick_test.pojo;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UrlSchemaInfo extends BaseSchemaInfo{
    /*
     * Table Name
     */
    private List<TableDataInfo> tableList;
}
