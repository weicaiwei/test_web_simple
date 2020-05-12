package com.caiwei.common.test.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-05-07
 */
@Configuration
public class RedissonConfig {


    @Value("${redisson.address}")
    private String address;


    /**
     * 单机模式自动装配
     * @return RedissonClient
     */
    @Bean
    RedissonClient redissonSingle() {
        Config config = new Config();
        config.useSingleServer().setAddress(address);
        return Redisson.create(config);
    }
    @Bean("testRLock1")
    public RLock testRLock1() {
        return redissonSingle().getLock("testRLock1");
    }

    @Bean("testRLock2")
    public RLock testRLock2() {
        return redissonSingle().getLock("testRLock2");
    }

}
