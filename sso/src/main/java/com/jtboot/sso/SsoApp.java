package com.jtboot.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@MapperScan("com.jtboot.sso.mapper")
@ComponentScan({"com.jtboot.sso", "com.jtboot.common"})
public class SsoApp {
    public static void main( String[] args ) {
        SpringApplication.run(SsoApp.class, args);
    }
}
