package com.pg.screen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author c.chuang
 */
@MapperScan("com.pg.screen.mapper.**")
@SpringBootApplication
public class PowerGridScreenApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerGridScreenApplication.class, args);
    }

}
