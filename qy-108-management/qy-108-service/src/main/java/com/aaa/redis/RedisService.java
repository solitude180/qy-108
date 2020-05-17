package com.aaa.redis;

import com.aaa.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import static com.aaa.staticstatus.RedisProperties.*;

/**
 * @Author Qin
 * @Date 2020/5/14 22:31
 * @Version 1.0
 */
@Service
public class RedisService<T> {
    @Autowired
    private JedisCluster jedisCluster;
/**
 * @Description:
 *
 * @Param: [key, value, nxxx, expx, seconds]
 * @return: java.lang.String
 * @Author: Qin
 * @Date: 2020/5/16
 */
    /**
     * @Description: 向redis中存储数据，并设置失效时间
     * key：redis的key
     * value：redis的value
     * nxxx：nx--key不存在可以存数据，xx--key存在可以才可以存储数据
     * expx：时间单位，wx--秒，px--毫秒
     * seconds：失效时间，如果没写则默认不需要设置
     * @Author: guohang
     * @Date: 2020/5/16 9:34
     * @Param: [key, value, nxxx, expx, seconds]
     * @return: java.lang.String
     */
    public String set(String key, T value, String nxxx, String expx, Integer seconds) {
        if (null != seconds && seconds > 0 && (NX.equals(nxxx) || XX.equals(nxxx)) && (EX.equals(expx) || PX.equals(expx))) {
            //说明需要设置失效时间，使用糊涂工具包的JSONObject对象转换String
            return jedisCluster.set(key, JSONUtil.toJsonString(value), nxxx, expx, seconds);
        } else {
            //不需要设置失效时间
            if (NX.equals(nxxx)) {
                //long类型，需要转换为String类型传出去
                return String.valueOf(jedisCluster.setnx(key, JSONUtil.toJsonString(value)));
            } else if (XX.equals(nxxx)) {
                return String.valueOf(jedisCluster.set(key, JSONUtil.toJsonString(value)));
            }
        }
        return NO;
    }

}
