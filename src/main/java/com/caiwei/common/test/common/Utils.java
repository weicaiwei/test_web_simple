package com.caiwei.common.test.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-08
 */
public class Utils {


    /**
     * SimpleDateFormat 是 Java 中一个非常常用的类，该类用来对日期字符串进行解析和格式化输出，
     * 但如果使用不小心会导致非常微妙和难以调试的问题，因为 DateFormat 和 SimpleDateFormat 类不都是线程安全的，
     * 在多线程环境下调用 format() 和 parse() 方法应该使用同步代码来避免问题。
     */
    private static ThreadLocal<DateFormat> threadLocalHf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static ThreadLocal<DateFormat> threadLocalNf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmssSSS"));

    /**
     * 将标准格式时间字符串转换成date对象
     *
     * @param dateString 时间字符串
     * @return 时间对象
     * @throws ParseException 字符串解析异常
     */
    public static Date parse(String dateString) throws ParseException {
        return threadLocalHf.get().parse(dateString);
    }

    /**
     * 将date对象转换成标准格式时间字符串
     *
     * @param date 时间对象
     * @return 标准格式时间字符串
     */
    public static String format(Date date) {
        return threadLocalHf.get().format(date);
    }

    /**
     * 获取当前时间的标准格式时间字符串
     *
     * @return 时间字符串
     */
    public static String now() {
        return threadLocalHf.get().format(new Date());
    }

    /**
     * 获取当前时间的标准格式时间字符串
     *
     * @return 时间字符串
     */
    public static String nowMillisecond() {
        return threadLocalNf.get().format(new Date());
    }




    public static String generateRandomLenGthSting(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(new Random().nextInt(10));
        }
        return stringBuilder.toString();
    }

    public static String generateSerialNo(String appId) {
        return appId +
                nowMillisecond() +
                generateRandomLenGthSting(12);
    }

}
