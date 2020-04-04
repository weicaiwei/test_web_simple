package com.caiwei.common.test.controller;


import com.caiwei.common.test.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Map;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-01-09
 */
@Slf4j
@RestController
public class ExceptionTestController {

    @GetMapping("catch")
    public Map<String, Object> test(String id) {

        File a = new File("D:\\temp\\caiwei.jpg");
        try {
            if (!StringUtils.isEmpty(id)) {
                hello();
            }
            InputStream inputStream = new FileInputStream(a);

            inputStream.close();
            return ResultUtil.success();
        } catch (FileNotFoundException e) {
            log.error("文件未找到", e);
            return ResultUtil.fail(ResultUtil.FILE_NOT_FOUND);
        } catch (IOException e) {
            log.error("读取文件异常", e);
            return ResultUtil.fail(ResultUtil.FILE_READ_ERROR);
        }


    }

    private void hello() {
        throw new RuntimeException("测试一下");
    }

}


