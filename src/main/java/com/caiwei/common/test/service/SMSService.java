package com.caiwei.common.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: SMSService
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/11/3 13:03
 */
@Service
public class SMSService {

    private ExecutorService threadPool = Executors.newFixedThreadPool(1);

    @Transactional
    public void sendMessage(String toPhone, String message) {
        threadPool.submit(()->{
            try {
                System.out.println("thread:"+Thread.currentThread().getName()+toPhone+"进入睡眠"+message);
                Thread.sleep(100 * 1000);
                System.out.println("thread:"+Thread.currentThread().getName()+"解除睡眠");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
