package com.postss.common.kylin.api;

import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.postss.common.kylin.api.search.ApiCubeDescriptorSearch;
import com.postss.common.kylin.api.search.ApiCubeSearch;
import com.postss.common.kylin.api.search.ApiCubesListSearch;
import com.postss.common.kylin.api.search.ApiDataModelSearch;
import com.postss.common.kylin.api.search.ApiProjectsReadableSearch;
import com.postss.common.kylin.api.search.ApiTablesAndColumnsSearch;
import com.postss.common.kylin.search.ApiSqlSearch;
import com.postss.common.kylin.search.KylinSearch;

/**
 * 常用kylin restful api枚举类
 * @className KylinApi
 * @author jwSun
 * @date 2017年8月22日 下午7:06:56
 * @version 1.0.0
 */
public enum KylinApi {

    /**查询所有cube列表**/
    CUBES_LIST(ApiCubesListSearch.class, JSONArray.class, "/cubes", HttpMethod.GET, "List cubes"),
    /**查询单个cube**/
    CUBE(ApiCubeSearch.class, JSONObject.class, "/cubes/{cubeName}", HttpMethod.GET, "Get cube"),
    /**查询单个cube详情**/
    CUBE_DESCRIPTOR(ApiCubeDescriptorSearch.class, JSONArray.class, "/cube_desc/{cubeName}", HttpMethod.GET, "Get cube descriptor"),
    /**查询model信息**/
    DATA_MODEL(ApiDataModelSearch.class, JSONObject.class, "/model/{modelName}", HttpMethod.GET, "Get data model"),
    /**查询所有project**/
    PROJECT_READABLE(ApiProjectsReadableSearch.class, JSONArray.class, "/projects/readable", HttpMethod.GET, "Get projects"),
    /**根据sql查询**/
    SQL(ApiSqlSearch.class, JSONArray.class, "/query", HttpMethod.POST, "Get by sql"),
    /**查询工程对应表结构**/
    TABLES_AND_COLUMNS(ApiTablesAndColumnsSearch.class, JSONArray.class, "/tables_and_columns", HttpMethod.GET, "Get TABLES_AND_COLUMNS");

    private Class<? extends KylinSearch> searchClazz;
    private Class<?> resolveClazz;
    private String url;
    private HttpMethod httpMethod;
    private String remark;

    /**
     * 创建查询cube列表查询类
     * @return {@link com.postss.common.kylin.api.search.ApiCubesListSearch}
     */
    public static ApiCubesListSearch createApiCubesListSearch() {
        return new ApiCubesListSearch();
    }

    public static ApiTablesAndColumnsSearch createApiTablesAndColumnsSearch() {
        return new ApiTablesAndColumnsSearch();
    }

    /**
     * 创建查询sql查询类
     * @param project project名
     * @param sql 查询sql
     * @return {@link com.postss.common.kylin.search.ApiSqlSearch}
     */
    public static ApiSqlSearch createApiSqlSearch(String project, String sql) {
        return new ApiSqlSearch(project, sql);
    }

    public static ApiProjectsReadableSearch createApiProjectsReadableSearch() {
        return new ApiProjectsReadableSearch();
    }

    public static ApiCubeSearch createCubeSearch(String cubeName) {
        return new ApiCubeSearch(cubeName);
    }

    public static ApiCubeDescriptorSearch createApiCubeDescriptorSearch(String cubeName) {
        return new ApiCubeDescriptorSearch(cubeName);
    }

    public static ApiDataModelSearch createApiDataModelSearch(String modelName) {
        return new ApiDataModelSearch(modelName);
    }

    private KylinApi(Class<? extends KylinSearch> searchClazz, Class<?> resolveClazz, String url, HttpMethod httpMethod,
            String remark) {
        this.searchClazz = searchClazz;
        this.resolveClazz = resolveClazz;
        this.url = url;
        this.httpMethod = httpMethod;
        this.remark = remark;
    }

    public Class<? extends KylinSearch> getSearchClazz() {
        return searchClazz;
    }

    public void setSearchClazz(Class<? extends KylinSearch> searchClazz) {
        this.searchClazz = searchClazz;
    }

    public Class<?> getResolveClazz() {
        return resolveClazz;
    }

    public void setResolveClazz(Class<?> resolveClazz) {
        this.resolveClazz = resolveClazz;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
