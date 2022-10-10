package com.freeoneplus.quick_test;

import com.freeoneplus.quick_test.common.BasicDataTypeRandom;
import com.freeoneplus.quick_test.controller.QuickTestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class QuickTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(QuickTestApplication.class, args);
        QuickTestController bean = run.getBean(QuickTestController.class);
        bean.test();
    }

}
