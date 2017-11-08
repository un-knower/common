package com.postss.common.kylin.search;

import org.springframework.http.HttpMethod;

/**
 * 无具体请求体查询类
 * @className SimpleKylinBodySearch
 * @author jwSun
 * @date 2017年8月22日 下午7:18:43
 * @version 1.0.0
 */
public class SimpleKylinBodySearch extends SimpleKylinSearch implements KylinBodySearch {

    @Override
    public String searchJson() {
        return "{}";
    }

    public HttpMethod getMethod() {
        return this.method == null ? HttpMethod.POST : this.method;
    }

}
