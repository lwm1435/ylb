package com.lwm.dataservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwm1435@163.com
 * @date 2022-01-01 20:08
 * @description
 */
@EnableDubbo
@MapperScan(basePackages = "com.lwm.dataservice.mapper")
@SpringBootApplication
public class DataserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataserviceApplication.class, args);
    }
}
