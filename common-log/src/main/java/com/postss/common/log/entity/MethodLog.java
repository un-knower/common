package com.postss.common.log.entity;

import java.util.Date;

/**
 * 方法日志
 * @className MethodLog
 * @author jwSun
 * @date 2017年7月4日 下午5:53:06
 * @version 1.0.0
 */
public class MethodLog extends BaseLog {

    private static final long serialVersionUID = 1074294978306437629L;

    /**创建者帐号id**/
    private String createId;

    /**创建时间**/
    private Date createTime;

    /**异常原因**/
    private String exception;

    /**操作IP地址**/
    private String ip;

    /**类名**/
    private String targetName;

    /**操作方式**/
    private String requestMethod;

    /**方法说明**/
    private String methodName;

    /**参数**/
    private String params;

    /**请求URI**/
    private String requestUri;

    /**状态 0正常1异常**/
    private Integer status;

    /**日志标题**/
    private String title;

    /**用户代理**/
    private String userAgent;

    /**错误码**/
    private Integer exceptionCode;

    public static MethodLog buildLog() {
        return new MethodLog();
    }

    public String getCreateId() {
        return createId;
    }

    public MethodLog setCreateId(String createId) {
        this.createId = createId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public MethodLog setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getException() {
        return exception;
    }

    public MethodLog setException(String exception) {
        this.exception = exception;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public MethodLog setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public MethodLog setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public MethodLog setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getParams() {
        return params;
    }

    public MethodLog setParams(String params) {
        this.params = params;
        return this;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public MethodLog setRequestUri(String requestUri) {
        this.requestUri = requestUri;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public MethodLog setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MethodLog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public MethodLog setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getTargetName() {
        return targetName;
    }

    public MethodLog setTargetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public Integer getExceptionCode() {
        return exceptionCode;
    }

    public MethodLog setExceptionCode(Integer exceptionCode) {
        this.exceptionCode = exceptionCode;
        return this;
    }

    @Override
    public String toString() {
        return "[title=" + title + ", status=" + status + ",exception=" + exception + ", ip=" + ip + ", targetName="
                + targetName + ", requestMethod=" + requestMethod + ", methodName=" + methodName + ", params=" + params
                + ", requestUri=" + requestUri + "]";
    }

}
