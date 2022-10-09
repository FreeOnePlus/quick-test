package com.freeoneplus.quick_test.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataTypeEnum {
    TINYINT("TINYINT","BYTE"),
    SMALLINT("SMALLINT","SHORT"),
    INT("INT","INT"),
    BIGINT("BIGINT","LONG"),
    LARGEINT("LARGEINT","LONG"),
    DATETIME("DATETIME","DATETIME"),
    DATE("DATE","DATE"),
    STRING("STRING","STRING",30),
    VARCHAR("VARCHAR","STRING",30),
    BOOLEAN("BOOLEAN","BOOLEAN"),
    DECIMAL("DECIMAL","DOUBLE"),
    DOUBLE("DOUBLE","DOUBLE"),
    BITMAP("BITMAP","LONG"),
    FLOAT("FLOAT","FLOAT");

    private String dorisDataType;
    private String javaDataType;
    private int length;

    DataTypeEnum(String dorisDataType, String javaDataType) {
        this.dorisDataType = dorisDataType;
        this.javaDataType = javaDataType;
    }
}
