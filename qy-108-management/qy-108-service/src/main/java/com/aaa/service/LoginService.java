package com.aaa.service;

import com.aaa.base.BaseSerivce;
import com.aaa.mapper.UserMapper;
import com.aaa.model.User;
import com.aaa.redis.RedisService;
import com.aaa.utils.IDUtils;
import com.aaa.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.aaa.staticstatus.RedisProperties.EX;
import static com.aaa.staticstatus.RedisProperties.XX;

/**
 * @Author Qin
 * @Date 2020/5/16 23:18
 * @Description
 **/
@Service
public class LoginService extends BaseSerivce<User> {
    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: 登录，并把token存入到redis中
     * @Param: [user, redisService]
     * @return: com.aaa.vo.TokenVo
     * @Author: Qin
     * @Date: 2020/5/16
     */
    public TokenVo doLogin(User user, RedisService redisService) {
        TokenVo tokenVo = new TokenVo();
        tokenVo.setIfSuccess(false);
        //1.判断传过来的值是否为空
        if (null != user) {
            //2.把接受到的数据，也就是账号、密码和数据库进行比对
            User user1 = userMapper.selectOne(user);
            //3.判断是否正确
            if (null != user1) {
                //4.不为空，说明存在该用户
                //随机获取一个uuid给token赋值
                String token = IDUtils.getUUID();
                user1.setToken(token);
                int i = userMapper.updateByPrimaryKeySelective(user1);
                //5.判断token是否更新成功
                if (i > 0) {
                    //6.这说明更新成功
                    //然后把token值存入到redis中，并设置一个失效时间
                    String setResult = redisService.set(String.valueOf(user1.getId()), token, XX, EX, 1800);
                    if ("OK".equals(setResult.toUpperCase()) || "1".equals(setResult)) {
                        // TODO OK一定会返回，但是受影响的行数你们自己测试一下，我忘记了！
                        return tokenVo.setIfSuccess(true).setToken(token).setRedisKey(String.valueOf(user1.getId()));
                    }
                }
            }
        }
        return tokenVo;
    }


}
