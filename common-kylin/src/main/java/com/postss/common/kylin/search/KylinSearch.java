package com.postss.common.kylin.search;

import java.util.Set;

import org.springframework.http.HttpMethod;

/**
 * 基本请求查询类
 * @className KylinSearch
 * @author jwSun
 * @date 2017年8月22日 下午7:18:12
 * @version 1.0.0
 */
public interface KylinSearch {

    String getUrl();

    default HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    default Set<String> getFilterName() {
        return null;
    }

    default boolean checkParams() {
        return true;
    }

}
