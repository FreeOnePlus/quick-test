package com.freeoneplus.quick_test.utils;

import com.freeoneplus.quick_test.dao.DorisMapper;
import com.freeoneplus.quick_test.pojo.enums.DataTypeEnum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DorisTest {
    public static void main(String[] args) throws IOException, SQLException {
        DorisMapper dorisMapper = new DorisMapper();
        Connection dorisSchema = dorisMapper.getDorisSchema("storage.freeoneplus.com", 9030, "test", "root", "");
        Statement tableStatement = dorisSchema.createStatement();
        ResultSet tableResult = tableStatement.executeQuery("SHOW TABLES");
        while (tableResult.next()) {
            String tableName = tableResult.getString(1);
            Statement fieldStatement = dorisSchema.createStatement();
            ResultSet fieldResult = fieldStatement.executeQuery("DESC " + tableName);
            System.err.println(tableName);
            while (fieldResult.next()) {
                String fieldName = fieldResult.getString(1);
                String fieldDataType = fieldResult.getString(2);
                int filedLength = DataTypeEnum.VARCHAR.getLength();
                if (fieldDataType.matches("VARCHAR\\([0-9]*\\)")){
                    filedLength = Integer.parseInt(fieldDataType.split("(\\(|\\))")[1]);
                    fieldDataType = "VARCHAR";
                }
                DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(fieldDataType);
                String dorisDataType = dataTypeEnum.getDorisDataType();
                String javaDataType = dataTypeEnum.getJavaDataType();

            }
        }
        tableResult.close();
    }
}
