package com.caiwei.common.test.util;

/**
 * 统一返回对象
 *
 * @auther caiwei
 * @date 2020-04-27
 */
public class Result {


    public static Result success() {
        return new Result("0000", "接口调用成功", null);
    }

    public static Result success(Object data) {
        return new Result("0000", "接口调用成功", data);
    }

    public static Result fail(String code,String msg) {
        return new Result(code, msg, null);
    }

    public static Result fail(String msg) {
        return new Result("9999", msg, null);
    }

    public static Result fail() {
        return new Result("9999", "服务器异常", null);
    }

    private String code;
    private String msg;
    private Object data;

    private Result() {}

    private Result(String code, String msg, Object data) {
        this.code = code;
        this.msg= msg;
        this.data= data;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }
}

