package com.aaa.utils;

import java.util.UUID;

/**
 * @Author Qin
 * @Date 2020/5/15 22:47
 * @Version 1.0
 */
public class IDUtils {

    private IDUtils() {

    }

    /**
     * @Description: 获取uuid
     * @Param: []
     * @return: java.lang.String
     * @Author: Qin
     * @Date: 2020/5/16
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
