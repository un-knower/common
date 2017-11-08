package com.postss.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具类
 * @className RandomUtil
 * @author jwSun
 * @date 2017年6月30日 上午11:33:27
 * @version 1.0.0
 */
public class RandomUtil {

    /**
     * 获得uuid
     * @return uuid
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得随机整数
     * @param bigest 最大随机数
     * @return
     */
    public static int getRandomNum(int bigest) {
        Random random = new Random();
        return random.nextInt(bigest);
    }

    /**
     * 获得0-1的随机数
     * @return
     */
    public static double getRandomNum() {
        Random random = new Random();
        return random.nextDouble();
    }

}
