package com.postss.common.util;

import java.util.Collection;
import java.util.Map;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.code.SystemCode;
import com.postss.common.system.exception.BusinessCodeException;

/**
 * 数据验证工具类,不符合将报对应错误
 * @className Checker
 * @author jwSun
 * @date 2017年6月29日 下午2:46:05
 * @version 1.0.0
 */
public class Checker {

    private static Logger log = LoggerUtil.getLogger(Checker.class);

    /**
     * 参数不为null
     * @param object 待判断数据
     */
    public static void notNull(Object object, String... promptMessage) {
        if (object == null) {
            throwBusinessException(SystemCode.NULL.getCode(), promptMessage);
        }
    }

    /**
     * 参数不为null
     * @param object 待判断数据
     * @param businessCode 返回码
     */
    public static void notNull(Object object, int businessCode, String... promptMessage) {
        if (object == null) {
            throwBusinessException(businessCode, promptMessage);
        }
    }

    /**
     * 参数不为空且不为字符串形式的空
     * @param object 待判断字符串
     */
    public static void hasText(Object object, String... promptMessage) {
        if (!StringUtil.notEmpty(object)) {
            throwBusinessException(SystemCode.NO_PARAM.getCode(), promptMessage);
        }
    }

    /**
     * 参数不为空且不为字符串形式的空
     * @param object 待判断字符串
     * @param businessCode 返回码
     */
    public static void hasText(Object object, int businessCode, String... promptMessage) {
        if (!StringUtil.notEmpty(object)) {
            throwBusinessException(SystemCode.NO_PARAM.getCode(), promptMessage);
        }
    }

    /**
     * 是否为正确
     * @param object
     */
    public static void isTrue(boolean object, String... promptMessage) {
        if (!object) {
            throwBusinessException(SystemCode.FAIL.getCode(), promptMessage);
        }
    }

    /**
     * 是否为正确
     * @param object 待判断字符串
     * @param businessCode 返回码
     */
    public static void isTrue(boolean object, int businessCode, String... promptMessage) {
        if (!object) {
            throwBusinessException(businessCode, promptMessage);
        }
    }

    /**
     * 有查询结果(不为null且若为map或集合则需要size大于0)
     * @param object 待判断数据
     */
    public static void hasRecord(Object object, String... promptMessage) {
        boolean result = true;
        if (object == null) {
            result = false;
        } else if (object instanceof Collection) {
            Collection<?> collection = (Collection<?>) object;
            if (CollectionUtil.isEmpty(collection)) {
                result = false;
            }
        } else if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            if (map.size() == 0) {
                result = false;
            }
        } else {
            log.debug("cant decide this param : {} ,return true", object);
        }
        if (!result) {
            throwBusinessException(SystemCode.NO_RECORD.getCode(), promptMessage);
        }
    }

    /**
     * 有查询结果(不为null且若为map或集合则需要size大于0)
     * @param object 待判断数据
     * @param businessCode 返回码
     */
    public static void hasRecord(Object object, int businessCode, String... promptMessage) {
        boolean result = true;
        if (object == null) {
            result = false;
        } else if (object instanceof Collection) {
            Collection<?> collection = (Collection<?>) object;
            if (CollectionUtil.isEmpty(collection)) {
                result = false;
            }
        } else if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            if (map.size() == 0) {
                result = false;
            }
        } else {
            log.debug("cant decide this param : {} ,return true", object);
        }
        if (!result) {
            throwBusinessException(businessCode, promptMessage);
        }
    }

    private static void throwBusinessException(int businessCode, String... promptMessage) {
        BusinessCodeException ex = new BusinessCodeException(businessCode);
        ex.setPromptMessage((Object[]) promptMessage);
        throw ex;
    }

}
