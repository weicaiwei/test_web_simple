package com.caiwei.common.test.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @ClassName: BeanCreateTimeTest
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/11/21 16:11
 */
@Component
@Lazy
public class BeanCreateTimeTest {

    public BeanCreateTimeTest() {
        System.out.println("创建了BeanCreateTimeTest");

    }
}
