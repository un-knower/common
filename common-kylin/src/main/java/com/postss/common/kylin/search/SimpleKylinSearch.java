package com.postss.common.kylin.search;

import org.springframework.http.HttpMethod;

/**
 * 基本查询类实现
 * @className SimpleKylinSearch
 * @author jwSun
 * @date 2017年8月22日 下午7:19:14
 * @version 1.0.0
 */
public class SimpleKylinSearch implements KylinSearch {

    protected String url;
    protected HttpMethod method;

    public SimpleKylinSearch setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public HttpMethod getMethod() {
        return this.method == null ? HttpMethod.GET : this.method;
    }

    public SimpleKylinSearch setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
