package com.postss.common.kylin.api.search;

import com.postss.common.kylin.api.KylinApi;

public class ApiCubeDescriptorSearch extends KylinApiSearch {

    private String cubeName;

    public ApiCubeDescriptorSearch(String cubeName) {
        super(KylinApi.CUBE_DESCRIPTOR);
        this.cubeName = cubeName;
    }

    public String getCubeName() {
        return cubeName;
    }

    public void setCubeName(String cubeName) {
        this.cubeName = cubeName;
    }

}
