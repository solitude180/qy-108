package com.aaa.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author Qin
 * @Date 2020/5/15 21:42
 * @Version 1.0
 * 返回的结果信息的封装
 */
@Data
@Accessors(chain = true)
public class ResultData<T> implements Serializable {

    private String code;
    private String msg;
    private String datail;
    private T data;

}
