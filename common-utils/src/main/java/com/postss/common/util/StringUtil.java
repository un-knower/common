package com.postss.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本处理工具类
 * ClassName: StringUtil 
 * @author jwSun
 * @date 2016年10月30日下午7:16:02
 */
public class StringUtil {

    /**
     * 正则表达式取文本
     * @param text 文本
     * @param patternCompile 正则表达式
     * @return 符合条件的文本集合
     */
    public static List<String> getPatternMattcherList(String text, String patternCompile) {
        Pattern p = Pattern.compile(patternCompile);
        Matcher matcher = p.matcher(text);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 正则表达式单行取文本,group 为整数型，0为整个字符串，1为第一个括号，2为第二个...
     * @param text 文本
     * @param patternCompile 正则表达式
     * @param group 取下标
     * @return  符合条件的文本集合
     */
    public static List<String> getPatternMattcherList(String text, String patternCompile, Integer group) {
        Pattern p = Pattern.compile(patternCompile);
        Matcher matcher = p.matcher(text);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group(group));
        }
        return list;
    }

    /**
     * 正则表达式多行模式取文本,group 为整数型，0为整个字符串，1为第一个括号，2为第二个...
     * @param text 文本
     * @param patternCompile 正则表达式
     * @param group 取下标
     * @return  符合条件的文本集合
     */
    public static String getAllPatternMattcher(String text, String patternCompile, Integer group) {
        Pattern p = Pattern.compile(patternCompile, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            return matcher.group(group);
        }
        return null;
    }

    /**
     * 十六进制转为字符串 
     * @author sjw
     * @date 2016年10月27日 下午1:46:06 
     * @param value 十六进制数据
     * @param charset 字符串编码
     * @return
     */
    public static String hexToString(String value, String charset) {
        try {
            byte[] baKeyword = new byte[value.length() / 2];
            for (int i = 0; i < baKeyword.length; i++) {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(value.substring(i * 2, i * 2 + 2), 16));
            }
            value = new String(baKeyword, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return value;
    }

    private final static String HEX_STRING = "0123456789ABCDEF";
    //private final static byte[] HEX_BYTE = HEX_STRING.getBytes();
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     * 字节数组 转换为 十六进制字符串,小写
     * @author jwSun
     * @date 2016年10月30日下午7:19:26
     * @param b
     * @return
     */
    private static String bytes2Hex(byte[] b) {
        /*byte[] buff = new byte[3 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[3 * i] = HEX_BYTE[(b[i] >> 4) & 0x0f];
            buff[3 * i + 1] = HEX_BYTE[b[i] & 0x0f];
            buff[3 * i + 2] = 45;
        }
        return new String(buff);*/
        final StringBuilder buf = new StringBuilder(b.length * 2);
        for (int j = 0; j < b.length; j++) {
            buf.append(HEX_DIGITS[(b[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[b[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 字节数组 转换为 十六进制字符串,小写
     * @author jwSun
     * @date 2016年10月30日下午7:19:26
     * @param b
     * @return
     */
    public static String bytes2HexString(byte[] b) {
        //return bytes2Hex(b).replace("-", "");
        return bytes2Hex(b);
    }

    /**
     * 十六进制字符串 转换为 字节数组
     * @author jwSun
     * @date 2016年10月30日下午7:20:40
     * @param hexString
     * @return
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.replace(" ", "");
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

    /**
     * 判断为非空字符
     * @author jwSun
     * @date 2017年3月21日 下午5:51:05
     * @param obj
     * @return
     */
    public static Boolean notEmpty(Object obj) {
        if (obj == null || obj.getClass() != String.class || "".equals(obj)) {
            return false;
        }
        return true;
    }

    /**
     * 字符字节转字节
     * @author jwSun
     * @date 2016年10月30日下午7:20:40
     * @param c
     * @return
     */
    private static byte charToByte(char c) {
        return (byte) HEX_STRING.indexOf(c);
    }

    /**
     * unicode转中文
     * @author jwSun
     * @date 2017年6月10日 下午3:02:09
     * @param dataStr
     * @return
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    public static String format(Object... objects) {
        if (objects != null && objects.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < objects.length; i++) {
                sb.append(" " + objects[i] + "");
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 将有空格/换行符等格式化为一个空格，转为标准一句话，如 SELECT log_id AS logid, title AS titleabc FROM t_sys_log
     * @param sql
     * @return
     */
    public static String format(String sql) {
        return sql.replaceAll("(\r\n|\r|\n|\n\r|\t)", " ").replaceAll("\\s+", " ");
    }

}
