package com.aaa.controller;

import com.aaa.model.User;
import com.aaa.redis.RedisService;
import com.aaa.service.LoginService;
import com.aaa.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Qin
 * @Date 2020/5/16 23:59
 * @Description
 **/

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisService redisService;


    /**
     * @Description: 登录验证
     * @Param: [user]
     * @return: com.aaa.vo.TokenVo
     * @Author: Qin
     * @Date: 2020/5/17
     */
    @PostMapping("/doLogin")
    public TokenVo doLogin(@RequestBody User user) {
        return loginService.doLogin(user, redisService);
    }
}
