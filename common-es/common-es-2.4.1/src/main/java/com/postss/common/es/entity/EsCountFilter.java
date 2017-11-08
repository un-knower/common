package com.postss.common.es.entity;

/**
 * ES查询数据量计算过滤器-v1.4.2
 * @author jwSun
 * @date 2017年5月4日 上午9:46:45
 */
public class EsCountFilter {

    private Long gte;//大于等于
    private Long lte;//小于等于
    private Long gt;//大于
    private Long lt;//小于

    public Long getGte() {
        return gte;
    }

    public EsCountFilter gte(long gte) {
        this.gt = null;
        this.gte = gte;
        return this;
    }

    public Long getLte() {
        return lte;
    }

    public EsCountFilter lte(long lte) {
        this.lt = null;
        this.lte = lte;
        return this;
    }

    public Long getGt() {
        return gt;
    }

    public EsCountFilter gt(long gt) {
        this.gte = null;
        this.gt = gt;
        return this;
    }

    public Long getLt() {
        return lt;
    }

    public EsCountFilter lt(long lt) {
        this.lte = null;
        this.lt = lt;
        return this;
    }

}
