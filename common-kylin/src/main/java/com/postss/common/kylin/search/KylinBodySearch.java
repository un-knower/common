package com.postss.common.kylin.search;

import org.springframework.http.HttpMethod;

/**
 * 请求体方式请求查询类
 * @className KylinBodySearch
 * @author jwSun
 * @date 2017年8月22日 下午7:17:42
 * @version 1.0.0
 */
public interface KylinBodySearch extends KylinSearch {

    String searchJson();

    default HttpMethod getMethod() {
        return HttpMethod.POST;
    }

}
