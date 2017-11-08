package com.postss.common.kylin.entity;

import com.alibaba.fastjson.JSONObject;

public class ExampleResultEntity implements ApiSqlResults {

    private static final long serialVersionUID = -2006887087631941841L;
    private String abc;

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    @Override
    public void resolveResults(JSONObject apiSqlResult) {

    }

}
