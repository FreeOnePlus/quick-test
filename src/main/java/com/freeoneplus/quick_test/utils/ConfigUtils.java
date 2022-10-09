package com.freeoneplus.quick_test.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author YiJia.Su
 * @title: ConfigUtils
 * @projectName quick-test
 * @description: 使用该配置文件工具类获取各类配置信息，若获取信息为空，则返回NULL，交由使用层统一处理null值，可避免空指针异常
 * @date 2022/7/13 14:17
 */
@Slf4j
public class ConfigUtils {
    // 该对象使用频次不高，静态初始化
    private static Properties properties = new Properties();

    /**
     * 读取所有配置，返回一个Map集合
     *
     * @param path 配置文件目标路径
     * @return 配置Map集合
     */
    public static Map<String, Object> readAllConfig(String path) {
        Map<String, Object> configMap = new HashMap<>();
        try {
            properties.load(ConfigUtils.class.getClassLoader().getResourceAsStream(path));
        } catch (NullPointerException e) {
            log.error("The file path could not found!");
        } catch (IOException e) {
            log.error("IO check exception!");
        }
        Enumeration<?> names = properties.propertyNames();
        while (names.hasMoreElements()) {
            String key = (String) names.nextElement();
            Object value = properties.get(key);
            configMap.put(key, value);
        }
        return configMap;
    }

    /**
     * 读取指定配置，返回字符串类型的配置内容
     *
     * @param path 配置文件目标路径
     * @param key  要获取值的Key
     * @return 获取到的Value
     */
    public static String readStringConfig(String path, String key) {
        try {
            properties.load(ConfigUtils.class.getClassLoader().getResourceAsStream(path));
        } catch (NullPointerException e) {
            log.error("The file path could not found!");
        } catch (IOException e) {
            log.error("IO check exception!");
        }
        return (String) properties.get(key);
    }

    public static Integer readIntConfig(String path, String key) {
        try {
            properties.load(ConfigUtils.class.getClassLoader().getResourceAsStream(path));
        } catch (NullPointerException e) {
            log.error("The file path could not found!");
        } catch (IOException e) {
            log.error("IO check exception!");
        }
        String strValue = (String) properties.get(key);
        Integer value = null;
        try {
            value = Integer.parseInt(strValue);
        }catch (NumberFormatException e){
            log.error("Type conversion error!");
        }
        return value;
    }

    /**
     * TODO 其他基本类型读取
     */
}
