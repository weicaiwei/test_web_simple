package com.caiwei.common.test.controller;

import com.caiwei.common.test.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-09
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public Map<String,Object> except(Exception e){
        log.error("服务器异常",e);
        return ResultUtil.fail();
    }
}
