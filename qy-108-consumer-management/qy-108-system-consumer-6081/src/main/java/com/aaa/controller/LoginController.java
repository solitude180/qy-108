package com.aaa.controller;

import com.aaa.base.BaseController;
import com.aaa.base.ResultData;
import com.aaa.model.User;
import com.aaa.service.IQYService;
import com.aaa.vo.TokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Qin
 * @Date 2020/5/16 22:08
 * @Description
 * @Api() 用于类；表示标识这个类是swagger的资源
 * tags–表示说明
 * value–也是说明，可以使用tags替代
 **/
@RestController
@Api(value = "登录信息Controller", tags = "用户登录接口")
public class LoginController extends BaseController {

    @Autowired
    private IQYService iqyService;

    @PostMapping("/doLogin")
    @ApiOperation(value = "登录功能", notes = "用户执行登录功能")
    public ResultData doLogin(@RequestBody User user) {
        System.out.println(user);
        TokenVo tokenVo = iqyService.doLogin(user);
        if (tokenVo.getIfSuccess()) {
            return super.loginSuccess(tokenVo.getToken());
        }
        return super.loginFailed();
    }


}
