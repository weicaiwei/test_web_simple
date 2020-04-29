package com.caiwei.common.test.controller;

import com.caiwei.common.test.util.Result;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.Objects;

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
    public Result except(Exception e){
        log.error("服务器异常",e);
        return Result.fail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validationError(MethodArgumentNotValidException ex) {
        FieldError fieldError = Objects.requireNonNull(ex.getBindingResult()).getFieldError();

        log.error(Objects.requireNonNull(fieldError).getField()+fieldError.getDefaultMessage());
        return Result.fail(fieldError.getField()+fieldError.getDefaultMessage());
    }


}
