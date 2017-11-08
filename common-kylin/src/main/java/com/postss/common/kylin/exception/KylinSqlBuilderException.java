package com.postss.common.kylin.exception;

import com.postss.common.system.code.CodeMessageManager;
import com.postss.common.system.exception.BusinessCodeException;

/**
 * kylin没有查询到表格信息错误
 * @className KylinUnKnowException
 * @author jwSun
 * @date 2017年7月27日 下午3:52:14
 * @version 1.0.0
 */
public class KylinSqlBuilderException extends BusinessCodeException implements KylinException {

    private final static int businessCode = CodeMessageManager.NO_MESSAGE_CODE;
    private final static String PROMPT = "sql build error";

    private static final long serialVersionUID = 4080810070315357155L;

    public KylinSqlBuilderException() {
        super(businessCode, PROMPT);
    }

    public KylinSqlBuilderException(String message) {
        super(businessCode, PROMPT + ":" + message);
    }
}
