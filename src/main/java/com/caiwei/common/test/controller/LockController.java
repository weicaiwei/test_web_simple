package com.caiwei.common.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LockController
 * @Description: 非公平锁与公平锁的区别在于新晋获取锁的进程会有多次机会去抢占锁。如果被加入了等待队列后则跟公平锁没有区别。
 * @auther: caiwei
 * @date: 2019/9/6 20:55
 **/




@RestController
@Slf4j
public class LockController {

    private Lock unfairLock = new ReentrantLock();
    private Lock fairLock = new ReentrantLock(true);

    private ExecutorService threadPool = Executors.newFixedThreadPool(100);



    @GetMapping("go")
    public String go() {
        for (int i = 0; i < 100; i++) {
            threadPool.execute(()->{
                unfairLock.lock();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程是" + Thread.currentThread().getName());

                unfairLock.unlock();

            });
        }
        //lock.lock();
        return "go";
    }

}
