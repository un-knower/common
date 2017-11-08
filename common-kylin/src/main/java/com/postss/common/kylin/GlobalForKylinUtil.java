package com.postss.common.kylin;

import com.postss.common.kylin.entity.Dimension;
import com.postss.common.kylin.entity.Measures;

/**
 * kylin工具所需常量值
 * @className GlobalForKylinUtil
 * @author jwSun
 * @date 2017年8月22日 下午7:26:11
 * @version 1.0.0
 */
public class GlobalForKylinUtil {

    public static final String KYLIN_SERVER_URL = "kylin.server.url";
    public static final String KYLIN_SERVER_NAME = "kylin.auth.name";
    public static final String KYLIN_SERVER_PASSWORD = "kylin.auth.password";
    public static final String DEFAULT_PROPERTIES_PATH = "properties/kylin.properties";

    /*public static final String kylinUrl = PropertiesUtil.getPropertyOrDefault(KYLIN_SERVER_URL,
            PropertiesUtil.getProperty(KYLIN_SERVER_URL, DEFAULT_PROPERTIES_PATH));
    public static final String kylinUserName = PropertiesUtil.getPropertyOrDefault(KYLIN_SERVER_NAME,
            PropertiesUtil.getProperty(KYLIN_SERVER_NAME, DEFAULT_PROPERTIES_PATH));
    public static final String kylinPassword = PropertiesUtil.getPropertyOrDefault(KYLIN_SERVER_PASSWORD,
            PropertiesUtil.getProperty(KYLIN_SERVER_PASSWORD, DEFAULT_PROPERTIES_PATH)); */

    public static final String kylinUrl = "http://192.168.51.70:7070/kylin";
    public static final String kylinUserName = "ADMIN";
    public static final String kylinPassword = "KYLIN";

    public interface CubeDescriptor {
        /**维度**/
        public static final String DIMENSIONS = "dimensions";
        /**维度类**/
        public static final Class<Dimension> DIMENSIONS_CLASS = Dimension.class;
        /**度量**/
        public static final String MEASURES = "measures";
        /**维度类**/
        public static final Class<Measures> MEASURES_CLASS = Measures.class;
        /**模块名**/
        public static final String MODEL_NAME = "model_name";
    }
}
