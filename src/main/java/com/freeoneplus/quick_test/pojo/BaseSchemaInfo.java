package com.freeoneplus.quick_test.pojo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseSchemaInfo {
    private String dbName;
    private String host;
    private int port = 8040;
    private String username = "root";
    private String password = "";
}
