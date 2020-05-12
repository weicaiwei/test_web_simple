package com.caiwei.common.test.scopetest;

import com.caiwei.common.test.util.SpringBeanFactoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@RestController
@Slf4j
@RequestMapping("scope")
public class ScopeTestController {

    //由于这个注入只在controller初始化的时候注入一次，所以即使service用的是prototype，新的请求过来的时候也是旧的service
    @Autowired
    private TestService testService;




    @GetMapping("go")
    public String go() {
        testService.who();
        System.out.println(testService);
        //这样通过bean工厂每次请求都获取bean，就能够每次都获取到新的bean
        TestService testService2 = SpringBeanFactoryUtil.getBean(TestService.class);
        testService2.who();
        return "hello";
    }

}
