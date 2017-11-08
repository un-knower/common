package com.postss.common.kylin.api.search;

import com.postss.common.kylin.api.KylinApi;

public class ApiDataModelSearch extends KylinApiSearch {

    public ApiDataModelSearch(String modelName) {
        super(KylinApi.DATA_MODEL);
        this.modelName = modelName;
    }

    private String modelName;

    public String getModelName() {
        return modelName;
    }

    public ApiDataModelSearch setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

}
