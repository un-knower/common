package com.postss.common.kylin.entity;

import com.alibaba.fastjson.JSONObject;

public interface ApiSqlResults extends java.io.Serializable {

    /**
     * 解析结果
     * @param results
     */
    public void resolveResults(JSONObject apiSqlResult);

}
