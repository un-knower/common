package com.postss.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Test {

    /*public static void main(String[] args) throws Exception {
    BufferedImage image = ImageIO.read(new FileInputStream("C:\\Users\\jwsun\\Desktop\\appmaptile.png"));
    ImageIO.write(grayImage(image), "PNG",
            new FileOutputStream(new File("C:\\Users\\jwsun\\Desktop\\appmaptile1.png")));
    }*/

    public final static int COLOR_WHITE = 0;
    public final static int COLOR_BLACK = 1;

    /** 
     * 对图片中的 黑色或白色进行透明化处理 
     * @param sourcePath 原始图 
     * @param targetPath 目标图,为null时在原始图同级目录下生成目标图 
     * @param type B:黑色  W:白色 
     * @return 结果图字节数据组 
     */
    public static byte[] transferAlpha(String sourcePath, String targetPath, int color) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            File iFile = new File(sourcePath);
            if (!iFile.exists())
                return byteArrayOutputStream.toByteArray();

            ImageIcon imageIcon = new ImageIcon(ImageIO.read(iFile));
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);
                    if (checkColor(rgb, 16, color)) {
                        //rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                        rgb = new Color(69, 137, 148).getRGB();
                    }
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

            File targetFile = null;
            if (targetPath == null) {
                targetFile = new File(sourcePath + "_" + color + ".png");
            } else {
                targetFile = new File(targetPath);
                if (!targetFile.exists()) {
                    File dir = new File(targetFile.getParent());
                    if (!dir.exists())
                        dir.mkdirs();
                }
            }
            ImageIO.write(bufferedImage, "png", targetFile);

            //返回处理后图像的byte[]  
            //ImageIO.write(bufferedImage, "png", byteArrayOutputStream);  
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();

    }

    /** 
     * 检查颜色是否为 白色 或者 黑色阈值范围 
     * @param rgb 颜色值 
     * @param color 0:白色 1:黑色 
     * @return 检查结果 
     */
    private static boolean checkColor(int rgb, int offset, int color) {
        int R = (rgb & 0xff0000) >> 16;
        int G = (rgb & 0xff00) >> 8;
        int B = (rgb & 0xff);
        if (R == 231) {
            return true;
        }
        if (color == 0) {
            return ((255 - R) <= offset) && ((255 - G) <= offset) && ((255 - B) <= offset);
        } else {
            return ((R <= offset) && (G <= offset) && (B <= offset));
        }
    }

    /** 
     * 合并图像 
     * @param src0i 输入图像0 
     * @param src1i 输入图像1 
     * @param out 输出图像 
     */
    public final static void mergeImage(String src0i, String src1i, String out) {

        try {
            File f0 = new File(src0i);
            File f1 = new File(src1i);
            Image srcimg0 = ImageIO.read(f0);
            Image srcimg1 = ImageIO.read(f1);

            System.out.println(f0.getName() + " + " + f1.getName() + " = " + out);
            int width = srcimg0.getWidth(null);
            int height = srcimg0.getHeight(null);

            BufferedImage buffereI0 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g0 = buffereI0.createGraphics();
            g0.drawImage(srcimg0, 0, 0, width, height, null);

            BufferedImage buffereI1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g1 = buffereI1.createGraphics();
            g1.drawImage(srcimg1, 0, 0, null);

            for (int j1 = buffereI0.getMinY(); j1 < buffereI0.getHeight(); j1++) {
                for (int j2 = buffereI0.getMinX(); j2 < buffereI0.getWidth(); j2++) {
                    int rgb0 = buffereI0.getRGB(j2, j1);
                    int rgb1 = buffereI1.getRGB(j2, j1);
                    buffereI0.setRGB(j2, j1,
                            (checkColor(rgb0, 0, COLOR_WHITE) && !checkColor(rgb1, 0, COLOR_WHITE)) ? rgb1 : rgb0);
                }
            }

            g0.dispose();
            FileOutputStream fout = new FileOutputStream(out);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fout);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(buffereI0);
            param.setQuality(80f, true);
            encoder.encode(buffereI0);
            fout.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }

    }

    /** 
     * 批量合并图像 
     * @param sources 
     * @param target 
     */
    public static void marge(String[] sources, String target) {
        for (int i = 0; i < sources.length; i++) {
            if (i == 0) {
                i++;
                mergeImage(sources[0], sources[1], target);
            } else {
                mergeImage(target, sources[i], target);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        //去除影像黑边  
        /*String[] sources = { "C:\\Users\\jwsun\\Desktop\\appmaptile.png" };
        marge(sources, "C:\\Users\\jwsun\\Desktop\\appmaptile1.png");*/
        transferAlpha("C:\\Users\\jwsun\\Desktop\\appmaptile1.png", "C:\\Users\\jwsun\\Desktop\\appmaptile2.png",
                COLOR_WHITE);

    }
}
