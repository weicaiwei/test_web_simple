package com.caiwei.common.test.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: TestService
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/10/29 15:10
 */
@FeignClient(name = "remoteService",url = "http://127.0.0.1:8001/api")
public interface TestService {

    //请求路径可以统一使用requestMapping
    //get还是post请求，通过@RequestParam与@RequestBody注解来作区分
    //注意如果是get请求，如果拥有多个参数，那么每个参数前都需要添加@RequestParam注解
    @RequestMapping("get")
    String getTest(@RequestParam String param1, @RequestParam String param2);

    @RequestMapping("post")
    String postTest(@RequestBody Map<String, String> params);
}
