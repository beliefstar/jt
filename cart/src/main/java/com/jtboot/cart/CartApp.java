package com.jtboot.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Hello world!
 *
 */


@SpringBootApplication
@MapperScan("com.jtboot.cart.mapper")
@ComponentScan({"com.jtboot.cart", "com.jtboot.common"})
public class CartApp {

    public static void main( String[] args ) {
        SpringApplication.run(CartApp.class, args);
    }
}
