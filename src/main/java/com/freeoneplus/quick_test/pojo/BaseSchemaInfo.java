package com.freeoneplus.quick_test.pojo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseSchemaInfo {
    private String dbName;
    private String feHost;
    private int feHttpPort = 8030;
    private String username = "root";
    private String password = "";
}
