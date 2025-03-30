package com.permission.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.permission.authority.dao")//扫描组件包
public class PerMissionApplication {

    //启动类
    public static void main(String[] args) {
        SpringApplication.run(PerMissionApplication.class,args) ;
    }
}