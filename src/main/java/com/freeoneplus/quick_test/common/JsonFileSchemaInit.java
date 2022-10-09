package com.freeoneplus.quick_test.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.freeoneplus.quick_test.pojo.JsonFileSchemaDatabaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 通过读取schema文件得到生成的csv目标结构
 */
@Slf4j
@Component
public class JsonFileSchemaInit {
    public JsonFileSchemaDatabaseInfo readSchemaFile(String filePath){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            return JSON.parseObject(sb.toString(), JsonFileSchemaDatabaseInfo.class);
        } catch (JsonParseException e){
            throw new RuntimeException("The Schema File Information Is Error!",e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Schema File Not Found!",e);
        } catch (IOException e) {
            throw new RuntimeException("Read Schema File Is Error!",e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
