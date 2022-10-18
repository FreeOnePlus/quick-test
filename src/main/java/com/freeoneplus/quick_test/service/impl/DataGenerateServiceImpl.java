package com.freeoneplus.quick_test.service.impl;

import com.freeoneplus.quick_test.common.BasicDataTypeRandom;
import com.freeoneplus.quick_test.common.ComplexDataTypeRandom;
import com.freeoneplus.quick_test.dao.DorisMapper;
import com.freeoneplus.quick_test.pojo.BaseSchemaInfo;
import com.freeoneplus.quick_test.pojo.FieldDataTypeInfo;
import com.freeoneplus.quick_test.pojo.TableDataInfo;
import com.freeoneplus.quick_test.pojo.JsonFileSchemaTableInfo;
import com.freeoneplus.quick_test.pojo.enums.DataTypeEnum;
import com.freeoneplus.quick_test.service.DataGenerateService;
import com.freeoneplus.quick_test.service.LoadDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    /**
     * @param databaseName
     * @param csvFilePath
     * @param userName
     * @param password
     * @param jsonFileSchemaTableInfoList
     */
    @Override
    public void analysisSchemaGenerateData(String databaseName, String csvFilePath, String userName, String password,
                                           List<JsonFileSchemaTableInfo> jsonFileSchemaTableInfoList) {
        // TODO 本地 CSV 格式 V1 版本暂且不开发，主要满足直接对接 Doris 集群造数的能力
    }

    @Override
    public boolean analysisSchemaGenerateData(BaseSchemaInfo baseSchemaInfo, List<TableDataInfo> tableEntry) {
        Connection dorisSchema = dorisMapper.getDorisSchema(baseSchemaInfo.getFeHost(), baseSchemaInfo.getFePort(),
                baseSchemaInfo.getDbName(), baseSchemaInfo.getUsername(), baseSchemaInfo.getPassword());
        try {
            // 循环获取想要造数的表信息
            // TODO 需不需要事物管理，需要考虑
            List<TableDataInfo> tableDataInfoList = new ArrayList<>();
            for (TableDataInfo tableDataInfo : tableEntry) {
                String tableName = tableDataInfo.getTableName();
                Statement fieldStatement = dorisSchema.createStatement();
                ResultSet fieldResult = fieldStatement.executeQuery("DESC " + tableName);
                List<FieldDataTypeInfo> fieldDataTypeInfoList = new ArrayList<>();
                int count = 0;
                while (fieldResult.next()) {
                    String fieldDataType = fieldResult.getString(2);
                    int filedLength = 0;
                    if (fieldDataType.matches("(VARCHAR|CHAR)\\([0-9]*\\)")) {
                        filedLength = Integer.parseInt(fieldDataType.split("(\\(|\\))")[1]);
                        if (filedLength == 0) {
                            filedLength = DataTypeEnum.VARCHAR.getLength();
                        }
                        fieldDataType = "VARCHAR";
                    }
                    // 这里属于V1版本，只生成指定整数长度的 DOUBLE 数
                    if (fieldDataType.matches("DECIMAL\\([0-9]*,[0-9]*\\)")) {
                        filedLength = Integer.parseInt(fieldDataType.split("(\\(|\\))")[1].split(",")[0]);
                        if (filedLength == 0) {
                            filedLength = DataTypeEnum.DOUBLE.getLength();
                        }
                        fieldDataType = "DOUBLE";
                    }
                    DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(fieldDataType);
                    if (filedLength == 0) {
                        filedLength = dataTypeEnum.getLength();
                    }
                    String javaDataType = dataTypeEnum.getJavaDataType();
                    fieldDataTypeInfoList.add(count++, new FieldDataTypeInfo(javaDataType, filedLength));
                }
                tableDataInfo.setFieldDataTypeInfoList(fieldDataTypeInfoList);
                tableDataInfoList.add(tableDataInfo);
                fieldStatement.close();
            }
            boolean status = combineDataController(baseSchemaInfo, tableDataInfoList);
            if (status == false) {
                log.error("导入错误！任务终止！");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean combineDataController(BaseSchemaInfo baseSchemaInfo, List<TableDataInfo> tableDataInfoList) {
        ArrayList<String> dataList = new ArrayList<>();
        for (TableDataInfo dataInfo : tableDataInfoList) {
            // 该表的需要造数数据量
            Long totalNum = dataInfo.getTotalNum();
            String tableName = dataInfo.getTableName();
            for (int i = 0; i < totalNum; i++) {
                StringBuilder sb = new StringBuilder();
                // 字段集合生成数据
                for (FieldDataTypeInfo fieldDataTypeInfo : dataInfo.getFieldDataTypeInfoList()) {
                    String fieldType = fieldDataTypeInfo.getFieldType();
                    int length = fieldDataTypeInfo.getLength();
                    String result = combineData(fieldType, length);
                    sb.append(result).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                dataList.add(sb.toString());
                if (dataList.size() == baseSchemaInfo.getSingleDataVolume() || totalNum == i + 1) {
                    loadDataService.dorisCsvStreamLoad(baseSchemaInfo, tableName, dataList);
                    dataList.clear();
                }
            }
        }
        return true;
    }

    private String combineData(String dataType, int filedLength) {
        String result = "";
        if (DataTypeEnum.TINYINT.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getByte((byte) 0, Byte.MAX_VALUE);
        } else if (DataTypeEnum.SMALLINT.getJavaDataType().equals(dataType)) {
            result += basicDataTypeRandom.getShort((short) 0, Short.MAX_VALUE);
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
                stringBuilder.append(basicDataTypeRandom.getChar());
            }
            result = stringBuilder.toString();
            stringBuilder.delete(0, filedLength);
        } else if (DataTypeEnum.VARCHAR.getJavaDataType().equals(dataType)) {
            for (int i = 0; i < filedLength; i++) {
                stringBuilder.append(basicDataTypeRandom.getChar());
            }
            result = stringBuilder.toString();
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
