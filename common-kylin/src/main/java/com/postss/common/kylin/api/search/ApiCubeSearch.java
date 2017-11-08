package com.postss.common.kylin.api.search;

import com.postss.common.kylin.api.KylinApi;

public class ApiCubeSearch extends KylinApiSearch {

    private String cubeName;

    public ApiCubeSearch(String cubeName) {
        super(KylinApi.CUBE);
        this.cubeName = cubeName;
    }

    public String getCubeName() {
        return cubeName;
    }

    public void setCubeName(String cubeName) {
        this.cubeName = cubeName;
    }

}
