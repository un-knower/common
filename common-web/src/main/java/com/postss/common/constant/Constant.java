/**
 * 
 */
package com.postss.common.constant;

import org.apache.commons.lang.SystemUtils;

/**
 * @ClassName: Constant
 * @author jwSun
 * @date 2016年7月20日 上午9:26:15
 */
public class Constant {

    //private static Logger log = LoggerUtil.getLogger(Constant.class);

    /*public static HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
    .getRequestAttributes()).getRequest();
    
    
    public static final String Content_Path = request.getServerName() + ":"
    + request.getServerPort() + request.getContextPath();*/

    /**
     * 桌面地址
     */
    public static String DESKTOP = "C:/Users/sun/Desktop/";

    public static interface CHARSET {

        public static final String ISO_8859_1 = "ISO-8859-1";

        public static final String UTF_8 = "UTF-8";

        public static final String SYSTEM_FILE_ENCODING = SystemUtils.FILE_ENCODING;
    }

    /**
     * 异常页面
     * ClassName: ERROR
     * @author jwSun
     * @date 2016年12月2日下午3:04:38
     */
    public interface ERROR {
        public static String INFO = "error";
        public static String E500 = "/error/500";
        public static String E404 = "/error/404";
        public static String E404INFO = "页面不存在";
        public static String E403 = "/error/500";
        public static String E403INFO = "拒绝访问:您访问的模块被设置权限";
    }

    /**
     * 上传设置
     * @ClassName: UPLOAD_CONFIG
     * @author sjw
     * @date 2016年7月20日 上午10:30:44
     */
    public interface UPLOAD_CONFIG {
        public interface PIC_SIZE {
            public static Long MAX = 10 * 1024 * 1024L;//最大10M
            public static Long MIN = 0L; //最小0
        }

        public interface VIDEO_SIZE {
            public static Long MAX = 50 * 1024 * 1024L;//最大50M
            public static Long MIN = 0L; //最小0
        }

        public interface BAIDU_SIZE {
            public static Long MAX = 10 * 1024 * 1024L;
        }

        /**
         * 上传存储临时文件地址
         */
        public static String TEMPPATH = "E:/TempFile/";

    }

    /**
     * JSON数据验证
     * @ClassName: HTMLVALIDATE
     * @author sjw
     * @date 2016年7月20日 上午9:28:55
     */
    public interface HTMLVALIDATE {
        public static String SUCCESS = "success";
        public static String MSG = "msg";
        public static String INFO = "info";
        public static String ROWS = "rows";
        public static String DATA = "data";
        public static String PAGE = "page";
        public static String TOTAL = "total";
        public static String CODE = "code";
        public static String VALIDATE = "validate";

        public interface VALIDATE {
            public static String TRUE = "true";
            public static String FALSE = "false";
        }
    }
}
