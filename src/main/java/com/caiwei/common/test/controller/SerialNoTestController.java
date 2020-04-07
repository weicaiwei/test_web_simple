package com.caiwei.common.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.caiwei.common.test.mapper.SerialNoGenerateDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-08
 */
@RequestMapping("serialNo")
@Controller
@Slf4j
public class SerialNoTestController {

    @Autowired
    private SerialNoGenerateDao serialNoGenerateDao;

    private ExecutorService threadPool = Executors.newFixedThreadPool(100);

    @RequestMapping("/")
    public void generateSerialNo(String appId) {
        for (int i = 0; i < 1000000000; i++) {
            threadPool.execute(()->{
                String serialNo = serialNoGenerateDao.generateSerialNo(appId);
                log.info("treadName[{}],serialNo[{}]", Thread.currentThread().getName(), serialNo);
            });
        }
    }
}
