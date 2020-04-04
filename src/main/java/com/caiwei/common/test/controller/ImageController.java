package com.caiwei.common.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * @Name: ImageController
 * @Description: TODO
 * @auther: caiwei
 * @date: 2019/12/15 14:21
 */
@Controller
@RequestMapping("image")
@Slf4j
public class ImageController {

    @GetMapping("{name}")
    public void processImage(HttpServletRequest request, HttpServletResponse response, @PathVariable("name") String fileName) {
        try {
            response.reset();
            RandomAccessFile targetFile = new RandomAccessFile("D:\\temp\\"+fileName, "r");
            OutputStream  outputStream = response.getOutputStream();
            byte[] cache = new byte[1024 * 30];
            int flag;
            log.info("请求图片文件，开始");
            while ((flag = targetFile.read(cache)) != -1) {
                log.info("请求字节长度：" + flag);
                outputStream.write(cache, 0, flag);
            }
            log.info("请求图片文件，结束");
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
