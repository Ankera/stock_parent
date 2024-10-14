package com.ankers.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ankers.stock.mapper")
public class JobApp {
    public static void main(String[] args) {

        SpringApplication.run(JobApp.class, args);
    }
}
