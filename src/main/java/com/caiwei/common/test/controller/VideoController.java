package com.caiwei.common.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: VideoController
 * @Description: http协议从1.1开始支持获取文件的部分内容，这为并行下载以及断点续传提供了技术支持。
 * 它通过在Header里两个参数实现的，客户端发请求时对应的是Range，服务器端响应时对应的是Content-Range；
 * 提供视频播放和视频下载两个功能
 * 视频播放：主要通过请求头的request的Range，response的Content-Length，Content-Range，返回码设定为206来控制视频快进，后退
 * 视频下载：主要通过设置response的Content-Type为application/octet-stream实现
 * @auther: caiwei
 * @date: 2019/9/28 23:15
 */
@Controller
@Slf4j
public class VideoController {

    @RequestMapping("video/{fileName}")
    public void download(HttpServletRequest request, HttpServletResponse response,  @PathVariable String fileName) throws IOException {

        //清空缓存
        response.reset();
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        File file = new File("D:\\temp\\" + fileName);
        if (file.exists()) {
            //创建随机读取文件对象
            RandomAccessFile targetFile = new RandomAccessFile(file, "r");
            long fileLength = targetFile.length();
            //获取从那个字节开始读取文件
            String rangeString = request.getHeader("Range");
            if (rangeString != null) {//如果rangeString不为空，证明是播放视频发来的请求
                long range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
                log.info("请求视频播放流，从字节："+range+" 开始");
                //设置内容类型
                response.setHeader("Content-Type", "video/mp4");
                //设置此次相应返回的数据长度
                response.setHeader("Content-Length", String.valueOf(fileLength - range));
                //设置此次相应返回的数据范围
                response.setHeader("Content-Range", "bytes "+range+"-"+(fileLength-1)+"/"+fileLength);
                //返回码需要为206，而不是200
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                //设定文件读取开始位置（以字节为单位）
                targetFile.seek(range);
            }else {////如果rangeString为空，证明是下载视频发来的请求
                log.info("请求视频文件下载");
                //设置响应头，把文件名字设置好
                response.setHeader("Content-Disposition", "attachment; filename="+fileName );
                //设置文件长度
                response.setHeader("Content-Length", String.valueOf(fileLength));
                //解决编码问题
                response.setHeader("Content-Type","application/octet-stream");

            }
            byte[] cache = new byte[1024 * 300];
            int flag;
            while ((flag = targetFile.read(cache))!=-1){
                outputStream.write(cache, 0, flag);
            }
        }else{
            String message = "file:"+fileName+" not exists";
            log.error(message);
            response.setHeader("Content-Type","application/json");
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        }
        outputStream.flush();
        outputStream.close();
    }
}
