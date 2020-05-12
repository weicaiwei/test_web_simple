package com.caiwei.common.test.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-05-07
 */

@RestController
@RequestMapping("redis")
@Slf4j
public class RedissonTestController {

    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Autowired
    private RedissonClient redissonClient;


    @Autowired
    @Qualifier("testRLock1")
    private RLock testRLock1;

    @Autowired
    @Qualifier("testRLock2")
    private RLock testRLock2;



    @GetMapping("lock")
    public String test() {

        RBucket<String> stringRBucket = redissonClient.getBucket("caiwei");
        stringRBucket.set("母猪的产后回力",100, TimeUnit.SECONDS);



        System.out.println(stringRBucket.get());
        for (int i = 0; i < 10; i++) {
            threadPool.execute(()->{

                try {

                    log.info("thread[{}] 尝试获取锁",Thread.currentThread().getId());
                    testRLock1.lock();

                    log.info("thread[{}] 获取到锁",Thread.currentThread().getId());
                    Thread.sleep(1000 * 5);
                    log.info("thread[{}] 释放锁",Thread.currentThread().getId());
                    testRLock1.unlock();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            });
        }
        return "ok";

    }
}
