package com.postss.common.util;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

/**
 * 调试工具类
 * @className DebugUtil
 * @author jwSun
 * @date 2017年6月30日 上午11:18:59
 * @version 1.0.0
 */
public class DebugUtil {

    private static Logger log = LoggerUtil.getLogger(DebugUtil.class);

    public static void debug() {
        StackTraceElement[] stacks = (new Throwable()).getStackTrace();
        for (StackTraceElement stack : stacks) {
            log.debug(stack.getClassName() + "-" + stack.getMethodName());
        }
    }

}
