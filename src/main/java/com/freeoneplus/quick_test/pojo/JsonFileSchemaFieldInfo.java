package com.freeoneplus.quick_test.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Schema数据字段级bean
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class JsonFileSchemaFieldInfo {
    private String fieldName;
    private String dataType;

    private int stringLength;
    private int minLength = 0;
    private int maxLength = 0;
    private double minValue = 0.0;
    private double maxValue = 0.0;
    private String beginDate;
    private String endDate;
    private String beginTime;
    private String endTime;


    public JsonFileSchemaFieldInfo(String fieldName, String dataType, int stringLength) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.stringLength = stringLength;
    }

    public JsonFileSchemaFieldInfo(String fieldName, String dataType, int minLength, int maxLength) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public JsonFileSchemaFieldInfo(String fieldName, String dataType, double maxValue) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.maxValue = maxValue;
    }

    public JsonFileSchemaFieldInfo(String fieldName, String dataType, double minValue, double maxValue) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public JsonFileSchemaFieldInfo(String fieldName, String dataType, String beginDate, String endDate) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

}
