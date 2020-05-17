package com.aaa.base;

import static com.aaa.status.LoginStatus.*;

/**
 * @Author Qin
 * @Date 2020/5/15 22:02
 * @Version 1.0
 * 通用的controller，实现返回值统一
 */
public class BaseController {


    /**
     * @Description: 登录成功，使用系统系统的消息
     * @Param: []
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginSuccess() {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(LOGIN_SUCCESS.getMsg());
        return resultData;
    }

    /**
     * @Description: 登录成功，自己定义消息
     * @Param: [msg]
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginSuccess(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Description: 登录成功，系统消息、系统状态码，系统自定义返回类型
     * @Param: [data]
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginSuccess(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(LOGIN_SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Description: 登录成功，自定义消息，自定义返回值
     * @Param: [msg, data]
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginSuccess(String msg, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Description: 登录失败，使用系统消息
     * @Param: []
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(LOGIN_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Description: 登录失败，使用系统状态码，自定义消息
     * @Param: [msg]
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @Description: 登录失败，系统状态码，系统消息，自定义返回值
     * @Param: [code, msg]
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginFailed(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(LOGIN_FAILED.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Description: 系统状态码，自定义消息，自定义返回值
     * @Param: [msg, data]
     * @return: com.aaa.base.ResultData
     * @Author: Qin
     * @Date: 2020/5/15
     */
    protected ResultData loginFailed(String msg, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(LOGIN_FAILED.getMsg());
        return resultData;
    }

}
