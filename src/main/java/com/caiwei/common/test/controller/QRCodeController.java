package com.caiwei.common.test.controller;

import com.alibaba.fastjson.JSON;
import com.caiwei.common.test.util.QrCodeUtils;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 二维码请求
 *
 * @auther caiwei
 * @date 2019-12-26
 */
@RequestMapping("qrCode")
@Controller
@Slf4j
public class QRCodeController {

    @GetMapping("create")
    public void createQRCode(HttpServletRequest request, HttpServletResponse response) {
        String c = "我就是要泡冰冰啊";
        try {

            String Xip = request.getHeader("X-Real-IP");
            String host = request.getRemoteHost();
            String addr = request.getRemoteAddr();
            String XFor = request.getHeader("X-Forwarded-For");
            if((!StringUtils.isEmpty(XFor)) && !"unKnown".equalsIgnoreCase(XFor)){
                //多次反向代理后会有多个ip值，第一个ip才是真实ip
                int index = XFor.indexOf(",");
                if(index != -1){
                    System.out.println(XFor.substring(0, index));
                }
            }
            XFor = Xip;
            if((!StringUtils.isEmpty(XFor)) && !"unKnown".equalsIgnoreCase(XFor)){
                System.out.println(XFor);
            }
            if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
                XFor = request.getRemoteAddr();
            }

            System.out.println(JSON.toJSONString(request.getHeaderNames()));

            OutputStream os = response.getOutputStream();
            os.write( QrCodeUtils.encode(c));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
