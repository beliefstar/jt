package com.jtboot.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan({"com.jtboot.manager", "com.jtboot.common"})
@MapperScan("com.jtboot.manager.mapper")
public class ManageApp {

    public static void main(String[] args) {
        SpringApplication.run(ManageApp.class, args);
    }
}
