package com.postss.common.kylin.exception;

import com.postss.common.system.code.CodeMessageManager;
import com.postss.common.system.exception.BusinessCodeException;

/**
 * kylin未知错误
 * @className KylinUnKnowException
 * @author jwSun
 * @date 2017年7月27日 下午3:52:14
 * @version 1.0.0
 */
public class KylinUnKnowException extends BusinessCodeException implements KylinException {

    private final static int businessCode = CodeMessageManager.NO_MESSAGE_CODE;
    private final static String PROMPT = "kylin未知错误";

    private static final long serialVersionUID = 4080810070315357155L;

    public KylinUnKnowException() {
        super(businessCode, PROMPT);
    }

    public KylinUnKnowException(String message) {
        super(businessCode, PROMPT + ":" + message);
    }
}
