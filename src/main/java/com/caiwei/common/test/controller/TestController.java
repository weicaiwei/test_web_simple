package com.caiwei.common.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.caiwei.common.test.service.remote.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: TestController
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/10/29 15:21
 */
@RestController
@Slf4j
public class TestController {

    //需要在启动类上添加@EnableFeignClients，否则会报该bean无法注入的错误
    @Autowired
    private TestService testService;

    private String param1 = "参数1";

    private String param2 = "参数2";

    private ExecutorService threadPool = Executors.newFixedThreadPool(3);

    @GetMapping("test1")
    public String test1(@RequestParam String param1, @RequestParam String param2) {
        log.debug("param1[{}],param2[{}]", param1, param2);

        log.info("param1[{}],param2[{}]", param1, param2);
        return testService.getTest(param1,param2);
    }

    @PostMapping("test1")
    public String test1(@RequestBody Map<String,String> params) {
        return testService.postTest(params);
    }


    @GetMapping("thread")
    public void test2() {
        threadPool.submit(() -> {
            System.out.println("thread:" + Thread.currentThread().getName());
        });
    }

    @GetMapping("base64")
    public void test3(@RequestParam String param) {
        String encodeString = Base64.getEncoder().encodeToString(param.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodeString);

        System.out.println(new String(Base64.getDecoder().decode(encodeString), StandardCharsets.UTF_8));

    }

    @GetMapping("serverTest")
    public Map<String, Object> test(@RequestParam("name") String name) {
        System.out.println("name:" + name);
        Map<String, Object> result = new HashMap<>();
        result.put("name", "caiwei");
        return result;
    }

    @PostMapping("serverTest")
    public Map<String, Object> test(@RequestBody Map<String,Object> paramBody) {
        System.out.println(paramBody.get("name"));
        Map<String, Object> objectMap = (Map<String, Object>) paramBody.get("other");
        System.out.println(objectMap.get("age"));
        System.out.println(objectMap.get("love"));

        Map<String, Object> result = new HashMap<>();
        result.put("name", "caiwei");
        return result;
    }

}
