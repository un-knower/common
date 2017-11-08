package com.postss.common.base.entity;

/**
 * 返回解析执行类
 * @className FunctionEntity
 * @author jwSun
 * @date 2017年6月28日 下午8:01:18
 * @version 1.0.0
 */
public class FunctionEntity {

    /**前端调用**/
    private String eval;
    /**执行参数**/
    private String functionParams;
    /**携带数据**/
    private Object data;

    public String getEval() {
        return eval;
    }

    public FunctionEntity setEval(String eval) {
        this.eval = eval;
        return this;
    }

    public String getFunctionParams() {
        return functionParams;
    }

    public FunctionEntity setFunctionParams(String functionParams) {
        this.functionParams = functionParams;
        return this;
    }

    public Object getData() {
        return data;
    }

    public FunctionEntity setData(Object data) {
        this.data = data;
        return this;
    }

}
