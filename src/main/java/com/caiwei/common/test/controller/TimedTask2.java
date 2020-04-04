package com.caiwei.common.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TimedTask
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/10/30 21:36
 */
@Component
/*@EnableScheduling*/
@Slf4j
public class TimedTask2 {

  /*  @Scheduled(fixedRate=10000)*/
    public void executeTask() {
        log.debug("Controller这是debug级别的输出");
        log.info("Controller这是info级别的输出");
        log.warn("Controller这是warn级别的输出");
        log.error("Controller这是error级别的输出");
    }
}
