package com.caiwei.common.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @ClassName: TimedTask
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/10/30 21:36
 */
@Component
/*@EnableScheduling*/
@Slf4j
public class TimedTask {

    @Autowired
    private SMSService smsService;

    @Scheduled(fixedRate=1000)
    public void executeTask() {
        smsService.sendMessage(new Random().toString(),new Random().toString());
        System.out.println("倒着啦");
/*        log.debug("这是debug级别的输出");
        log.info("这是info级别的输出");
        log.warn("这是warn级别的输出");
        log.error("这是error级别的输出");*/

    }
}
