package com.postss.common.kylin.exception;

import org.springframework.http.HttpStatus;

import com.postss.common.system.exception.ConnectionException;

/**
 * kylin连接失败
 * @className KylinConnectionException
 * @author jwSun
 * @date 2017年7月27日 下午3:53:15
 * @version 1.0.0
 */
public class KylinConnectionException extends ConnectionException implements KylinException {

    private static final long serialVersionUID = 4080810070315357155L;

    public KylinConnectionException(HttpStatus httpStatus, String message) {
        super("httpstatus is " + httpStatus + ",message is " + message);
    }

}
