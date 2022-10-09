package com.freeoneplus.quick_test;

import com.freeoneplus.quick_test.common.BasicDataTypeRandom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class QuickTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(QuickTestApplication.class, args);
        BasicDataTypeRandom bean = run.getBean(BasicDataTypeRandom.class);
        for (int i = 0; i < 100; i++) {
            System.out.println(bean.getDouble(1.1, 2.0,0));
        }
    }

}
