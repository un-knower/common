package com.postss.common.base.entity;

import com.postss.common.system.code.SystemCode;

public class ReturnInfo {

    enum ResolveType {
        METHOD, LIST, DATA;
    }

    /**解析code 可为系统/业务**/
    private int code;

    /**返回提示**/
    private String message;

    /**系统code**/
    private SystemCode systemCode;

    /**前端解析类型**/
    private ResolveType resolveType;

    private FunctionEntity functionEntity;

    /**前端调用**/
    private String eval;

    public String getEval() {
        return eval;
    }

    public void setEval(String eval) {
        this.eval = eval;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SystemCode getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(SystemCode systemCode) {
        this.code = systemCode.getCode();
        this.systemCode = systemCode;
    }

    public ResolveType getResolveType() {
        return resolveType;
    }

    public void setResolveType(ResolveType resolveType) {
        this.resolveType = resolveType;
    }

    public FunctionEntity getFunctionEntity() {
        return functionEntity;
    }

    public void setFunctionEntity(FunctionEntity functionEntity) {
        this.functionEntity = functionEntity;
    }

}
