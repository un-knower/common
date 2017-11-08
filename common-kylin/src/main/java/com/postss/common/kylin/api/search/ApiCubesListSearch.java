package com.postss.common.kylin.api.search;

import com.postss.common.kylin.api.KylinApi;

/**
 * 查询cube列表
 * @className CubesListSearch
 * @author jwSun
 * @date 2017年7月27日 下午4:22:26
 * @version 1.0.0
 */
public class ApiCubesListSearch extends KylinApiSearch {

    public ApiCubesListSearch() {
        super(KylinApi.CUBES_LIST);
    }

    private Integer offset;
    private Integer limit;
    private String cubeName;
    private String projectName;

    public Integer getOffset() {
        return offset;
    }

    public ApiCubesListSearch setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public ApiCubesListSearch setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public String getCubeName() {
        return cubeName;
    }

    public ApiCubesListSearch setCubeName(String cubeName) {
        this.cubeName = cubeName;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ApiCubesListSearch setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

}
