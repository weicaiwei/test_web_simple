package com.caiwei.common.test.controller;

import com.caiwei.common.test.config.Caiwei;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-01
 */

@RequestMapping("param")
@RestController
public class ParamController {

    @Autowired
    private Caiwei caiwei;

    @GetMapping("caiwei")
    public Map<String, Object> queryParams() {
        Map<String, Object> result = new HashMap<>();
        result.put("people", caiwei);
        return result;
    }

}
