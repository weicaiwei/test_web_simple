package com.caiwei.common.test.controller;

import com.caiwei.common.test.util.QrCodeUtils;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
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
            OutputStream os = response.getOutputStream();
            os.write( QrCodeUtils.encode(c));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
