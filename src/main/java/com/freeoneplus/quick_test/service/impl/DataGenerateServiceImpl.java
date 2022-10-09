package com.freeoneplus.quick_test.service.impl;

import com.freeoneplus.quick_test.common.BasicDataTypeRandom;
import com.freeoneplus.quick_test.common.ComplexDataTypeRandom;
import com.freeoneplus.quick_test.dao.DorisMapper;
import com.freeoneplus.quick_test.pojo.FieldDataTypeInfo;
import com.freeoneplus.quick_test.pojo.TableDataInfo;
import com.freeoneplus.quick_test.pojo.JsonFileSchemaTableInfo;
import com.freeoneplus.quick_test.pojo.enums.DataTypeEnum;
import com.freeoneplus.quick_test.service.DataGenerateService;
import com.freeoneplus.quick_test.service.LoadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataGenerateServiceImpl implements DataGenerateService {

    private StringBuilder stringBuilder = new StringBuilder();

    @Autowired
    DorisMapper dorisMapper;

    @Autowired
    BasicDataTypeRandom basicDataTypeRandom;

    @Autowired
    ComplexDataTypeRandom complexDataTypeRandom;

    @Autowired
    LoadDataService loadDataService;

    @Override
    public void analysisSchemaGenerateData(String databaseName, String csvFilePath, String userName, String password, List<JsonFileSchemaTableInfo> jsonFileSchemaTableInfoList) {
        for (JsonFileSchemaTableInfo jsonFileSchemaTableInfo : jsonFileSchemaTableInfoList) {
            int csvNum = jsonFileSchemaTableInfo.getCsvNum();
            for (int i = 0; i < csvNum; i++) {

            }
        }
    }

    @Override
    public void analysisSchemaGenerateData(String databaseName, String feHost, int feHttpPort, String userName, String password, List<TableDataInfo> tableEntry) {
        Connection dorisSchema = dorisMapper.getDorisSchema(feHost, feHttpPort, databaseName, userName, password);
        try {
            for (TableDataInfo tableDataInfo : tableEntry) {
                String tableName = tableDataInfo.getTableName();
                Long totalNum = tableDataInfo.getTotalNum();
                Statement fieldStatement = dorisSchema.createStatement();
                ResultSet fieldResult = fieldStatement.executeQuery("DESC " + tableName);
                ArrayList<TableDataInfo> tableDataInfoList = new ArrayList<>();
                while (fieldResult.next()) {
                    String fieldDataType = fieldResult.getString(2);
                    int filedLength = DataTypeEnum.VARCHAR.getLength();
                    if (fieldDataType.matches("VARCHAR\\([0-9]*\\)")) {
                        filedLength = Integer.parseInt(fieldDataType.split("(\\(|\\))")[1]);
                        fieldDataType = "VARCHAR";
                    }
                    DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(fieldDataType);
                    String javaDataType = dataTypeEnum.getJavaDataType();
                    tableDataInfoList.add(new TableDataInfo());
                    loadDataService.dorisStreamLoad(feHost, feHttpPort, databaseName, tableName, totalNum, tableDataInfoList);
                    String result = combineData(javaDataType, filedLength);
                }
                fieldStatement.close();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String combineData(String dataType, int filedLength) {
        String result = "";
        if (DataTypeEnum.TINYINT.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getByte((byte) 0, Byte.MAX_VALUE);
        } else if (DataTypeEnum.SMALLINT.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getShort((short) 0, Byte.MAX_VALUE);
        } else if (DataTypeEnum.INT.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getInteger(0, Integer.MAX_VALUE);
        } else if (DataTypeEnum.BIGINT.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getLong(0, Long.MAX_VALUE);
        } else if (DataTypeEnum.DATETIME.getJavaDataType().equals(dataType)) {
            result += complexDataTypeRandom.getRandomTime("1949-10-01 00:00:00", "2022-12-31 23:59:59");
        } else if (DataTypeEnum.DATE.getJavaDataType().equals(dataType)) {
            result += complexDataTypeRandom.getRandomDate(0, "1949-10-01", "2022-12-31");
        } else if (DataTypeEnum.STRING.getJavaDataType().equals(dataType)) {
            for (int i = 0; i < filedLength; i++) {
                result += stringBuilder.append(basicDataTypeRandom.getChar());
            }
            stringBuilder.delete(0, filedLength);
        } else if (DataTypeEnum.VARCHAR.getJavaDataType().equals(dataType)) {
            for (int i = 0; i < filedLength; i++) {
                result += stringBuilder.append(basicDataTypeRandom.getChar());
            }
            stringBuilder.delete(0, filedLength);
        } else if (DataTypeEnum.BOOLEAN.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getBoolean();
        } else if (DataTypeEnum.DOUBLE.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getDouble(0, Double.MAX_VALUE, 2);
        } else if (DataTypeEnum.FLOAT.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getFloat(0, Float.MAX_VALUE, 2);
        }
        return result;
    }

}
