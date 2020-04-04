/*
package com.caiwei.common.test.util;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * TODO
 *
 * @auther caiwei
 * @date 2019-12-26
 *//*

@Slf4j
public class QRCodeUtil {

    */
/**二维码文字编码格式*//*

    private static final String QRCODE_CHARSET = "UTF-8";

    */
/**二维码图片格式*//*

    private static final String QRCODE_FORMAT_JPG = "jpg";
    private static final String QRCODE_FORMAT_PNG = "png";

    */
/**二维码图片尺寸*//*

    private static final Integer QRCODE_SIZE = 300;

    */
/**二维码中心的logo图片尺寸*//*

    private static final Integer LOGO_SIZE = 50;

    */
/**
     * 生成二维码
     *
     * @param content 二维码中的文字内容
     * @param logoPath log文件的文件路径
     * @param compress 是否需要压缩logo
     * @return 图片
     *//*

    public static byte[] createQRCode(String content, String logoPath, Boolean compress) throws WriterException {

        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hintMap.put(EncodeHintType.CHARACTER_SET, QRCODE_CHARSET);
        hintMap.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hintMap);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        //如果有logo
        if (!StringUtils.isEmpty(logoPath)) {
            insertImage(bufferedImage, logoPath, compress);
        }
        return bufferedImage;
    }

    */
/**
     * 插入LOGO
     *
     * @param source 二维码图片
     * @param logoPath LOGO图片地址
     * @param compress 是否压缩
     *//*

    private static void insertImage(BufferedImage source, String logoPath, boolean compress) {
        try {
            Image logoImage = ImageIO.read(new File(logoPath));

            int width = logoImage.getWidth(null);
            int height = logoImage.getHeight(null);
            if (compress) { // 压缩LOGO
                if (width > LOGO_SIZE) {
                    width = LOGO_SIZE;
                }
                if (height > LOGO_SIZE) {
                    height = LOGO_SIZE;
                }
                Image image = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                logoImage = image;
            }
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (QRCODE_SIZE - width) / 2;
            int y = (QRCODE_SIZE - height) / 2;
            graph.drawImage(logoImage, x, y, width, height, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);
            graph.dispose();
        } catch (IOException e) {
            log.warn("向二维码中插入图片失败", e);
        }

    }
}
*/
