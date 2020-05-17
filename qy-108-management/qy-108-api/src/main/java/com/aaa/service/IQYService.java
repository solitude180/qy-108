package com.aaa.service;

import com.aaa.model.User;
import com.aaa.vo.TokenVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author Qin
 * @Date 2020/5/16 23:40
 * @Description 实际开发必须注意的东西:
 * 无论是springcloud1.x还是2.x版本
 * 一旦使用feign来进行传递参数的时候，必须要注意两个点:
 * 1.如果是简单类型(8种基本类型，String)--->必须使用注解@RequestParam
 * 基本类型可以传多个，也就是说一个方法的参数中可以使用多@RequestParam
 * <p>
 * 2.如果传递包装类型(List, Map, Vo, Po)，只能传递一个，而且必须要使用@RequestBody注解
 * <p>
 * 也就是说最终把这些参数值传递到provider项目的controller中，在这provider项目的controller中也必须使用
 * 相同的注解，而且provider和api的方法必须要一模一样(copy是最方便的)
 * @FeignClient(value = "")中valuse的值就是在provider中配置的spring.application.name=system-interface
 **/
@FeignClient(value = "SYSTEM-INTERFACE")
public interface IQYService {


    /**
     * @Description: 执行登录验证
     * @Param: [user]
     * @return: com.aaa.vo.TokenVo
     * @Author: Qin
     * @Date: 2020/5/17
     */
    @PostMapping("/doLogin")
    TokenVo doLogin(@RequestBody User user);
}
