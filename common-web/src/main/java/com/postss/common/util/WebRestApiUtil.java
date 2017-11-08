package com.postss.common.util;

import java.util.List;
import java.util.Map;

/**
 * 访问页面restful风格api工具类
 * @className WebRestApiUtil
 * @author jwSun
 * @date 2017年7月27日 下午5:02:05
 * @version 1.0.0
 */
public class WebRestApiUtil {

    // 匹配{}
    public static final String PATTERN_COMPILE = "\\{(.*)\\}";

    public static String getRealUrl(String url, Map<String, Object> valMap) {
        Checker.notNull(valMap);
        List<String> changeNames = StringUtil.getPatternMattcherList(url, PATTERN_COMPILE, 1);
        if (!CollectionUtil.isEmpty(changeNames)) {
            for (String changeName : changeNames) {
                Object val = valMap.get(changeName);
                Checker.notNull(val);
                url = url.replaceAll("\\{" + changeName + "\\}", String.valueOf(val));
            }
        }
        return url;
    }

}
