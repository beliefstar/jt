package com.jtboot.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.jtboot.web", "com.jtboot.common"})
public class WebApp {
    public static void main( String[] args ) {
        SpringApplication.run(WebApp.class, args);
    }
}
