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
    private int fePort = 8030;
    private String beHost;
    private int bePort = 9030;
    private String username = "root";
    private String password = "";
    private Integer singleDataVolume = 100000;

    public BaseSchemaInfo(String dbName, String feHost, int fePort, String username, String password) {
        this.dbName = dbName;
        this.feHost = feHost;
        this.fePort = fePort;
        this.username = username;
        this.password = password;
    }

}
