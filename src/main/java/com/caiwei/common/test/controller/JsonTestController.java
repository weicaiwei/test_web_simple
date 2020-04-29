package com.caiwei.common.test.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-08
 */
@RequestMapping("json")
@RestController
@Slf4j
public class JsonTestController {

    @PostMapping("/test")
    public JSONObject test(@RequestBody JSONObject requestBody) {
        String name = requestBody.getString("name");
        List<String> childList = requestBody.getJSONArray("childList").toJavaList(String.class);
        System.out.println(name);
        System.out.println(childList);
        return null;
    }
}
