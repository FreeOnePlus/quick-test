package com.freeoneplus.quick_test.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

@Component
public class DorisMapper {

    public Connection getDorisSchema(String host, int port, String dbName,String username, String password) {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("url", "jdbc:mysql://" + host + ":" + port + "/" + dbName);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        try {
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Druid Pool Get Error!",e);
        }
    }





}
