package com.aaa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.lang.reflect.Method;

/**
 * @Author Qin
 * @Date 2020/5/13 11:23
 * @Version 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class ApplicationRun7082 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun7082.class, args);


    }

}

