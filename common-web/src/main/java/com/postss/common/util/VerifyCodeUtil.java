package com.postss.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 验证码输出工具类
 * @className VerifyCodeUtil
 * @author jwSun
 * @date 2017年6月30日 下午12:03:27
 * @version 1.0.0
 */
public class VerifyCodeUtil {
    private static int w = 100;
    private static int h = 45;
    private static Random r = new Random();
    private static String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };
    private static String codes = "23456789QWERTYUPKJHGFDSAZXCVBNM";// 去掉了1,L,I便于识别
    private static Color bgcolor = new Color(255, 255, 255);

    /**
     * 随机选择颜色
     * @param @return   
     * @return Color  
     * @author jwSun
     * @date 2016年7月17日下午12:13:09
     */
    private static Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }

    /**
     * 随机选择字体
     * @param @return   
     * @return Font  
     * @author jwSun
     * @date 2016年7月17日下午12:13:01
     */
    private static Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt(4);// 生成随机的样式（0-无样式，1-粗体，2-斜体，3-粗体+斜体 ）
        int size = r.nextInt(5) + 24;// 生成随机字号；24~28
        return new Font(fontName, style, size);

    }

    /**
     * 图片增加线条干扰
     * @param @param image   
     * @return void  
     * @author jwSun
     * @date 2016年7月17日下午12:12:25
     */
    private static void drawLine(BufferedImage image) {
        int num = 5;//需要的线条数量
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(w);
            int y1 = r.nextInt(h);
            int x2 = r.nextInt(w);
            int y2 = r.nextInt(h);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLUE);
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 随机选择字符
     * @param @return   
     * @return char  
     * @date 2016年7月17日下午12:11:14
     */
    private static char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 创建图片
     * @param @return   
     * @return BufferedImage  
     * @author jwSun
     * @date 2016年7月17日下午12:13:42
     */
    private static BufferedImage createImage() {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(bgcolor);
        g2.fillRect(0, 0, w, h);
        return image;
    }

    /**
     * 获得图片
     * @param @return   
     * @return BufferedImage  
     * @author jwSun
     * @date 2016年7月17日下午12:13:52
     */
    private static Map<String, Object> getImage() {
        Map<String, Object> map = new HashMap<String, Object>();
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            String s = randomChar() + "";
            sb.append(s);
            float x = i * 1.0F * w / 4;
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s, x, h - 5);
        }
        drawLine(image);
        map.put("bufferedImage", image);
        map.put("text", sb.toString());
        return map;
    }

    /**
     * 输出验证码
     * @param @param OutputStream
     * @param @throws Exception   
     * @return void  
     * @author jwSun
     * @date 2016年7月17日下午3:05:54
     */
    public static void outputImg(HttpSession session, HttpServletResponse response) throws Exception {
        ServletOutputStream servletOutputStream = null;
        try {
            Map<String, Object> imageMap = getImage();
            session.setAttribute("verifyCode", imageMap.get("text").toString());
            servletOutputStream = response.getOutputStream();
            ImageIO.write((BufferedImage) imageMap.get("bufferedImage"), "JPEG", servletOutputStream);
        } catch (Exception e) {

        } finally {
            if (servletOutputStream != null) {
                servletOutputStream.close();
            }
        }
    }
}
