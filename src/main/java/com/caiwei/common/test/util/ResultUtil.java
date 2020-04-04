package com.caiwei.common.test.util;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-09
 */
public class ResultUtil {

    /**接口调用成功返回码*/
    private static final String SUCCESS_CODE = "0000";
    /**接口调用失败，未知异常返回码*/
    private static final String UNKNOWN_EXCEPTION = "9999";




    /**文件未找到*/
    public static final String FILE_NOT_FOUND = "1001";

    /**文件读取异常*/
    public static final String FILE_READ_ERROR = "1002";

    /**返回码与返回信息映射*/
    private static final Map<String, String> responseMap = new HashMap<>();
    static {
        responseMap.put(SUCCESS_CODE, "successful");
        responseMap.put(UNKNOWN_EXCEPTION, "服务器异常，请联系管理员");
        responseMap.put(FILE_NOT_FOUND, "文件未找到");
        responseMap.put(FILE_READ_ERROR, "文件读取异常");
    }


    /**
     * 接口正常执行完毕，无返回数据时调用
     *
     * @return 返回数据
     */
    public static Map<String, Object> success() {

        return writeResponse(SUCCESS_CODE, null);
    }


    /**
     * 接口正常执行完毕，有返回数据时调用
     *
     * @param data 需要返回的数据
     * @return 返回数据
     */
    public static Map<String, Object> success(Object data) {

        return writeResponse(SUCCESS_CODE, data);
    }

    /**
     * 接口执行时，发生可知异常时调用
     *
     * @param code 错误码
     * @return  返回数据
     */
    public static Map<String, Object> fail(String code) {

        return writeResponse(code, null);
    }

    /**
     * 接口执行时，发生未知异常时调用
     *
     * @return 返回数据
     */
    public static Map<String, Object> fail() {

        return writeResponse(UNKNOWN_EXCEPTION, null);
    }


    private static Map<String, Object> writeResponse(String code, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", responseMap.get(code));
        response.put("data", data);
        return response;
    }

}
