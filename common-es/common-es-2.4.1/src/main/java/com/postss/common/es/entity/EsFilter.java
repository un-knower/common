package com.postss.common.es.entity;

/**
 * ES过滤器
 * <pre>gte与gt、lte与lt只能各存在一个,存入两个将使前一个值清空
 * </per>
 * @author jwSun
 * @date 2017年5月2日 上午10:55:48
 */
public class EsFilter {

    private String fieldName;//过滤名
    private Object gte;//大于等于
    private Object lte;//小于等于
    private Object gt;//大于
    private Object lt;//小于
    private Boolean must = Boolean.TRUE;//true:must false:should

    public EsFilter(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getGte() {
        return gte;
    }

    public EsFilter gte(Object gte) {
        this.gt = null;
        this.gte = gte;
        return this;
    }

    public Object getLte() {
        return lte;
    }

    public EsFilter lte(Object lte) {
        this.lt = null;
        this.lte = lte;
        return this;
    }

    public Object getGt() {
        return gt;
    }

    public EsFilter gt(Object gt) {
        this.gte = null;
        this.gt = gt;
        return this;
    }

    public Object getLt() {
        return lt;
    }

    public EsFilter lt(Object lt) {
        this.lte = null;
        this.lt = lt;
        return this;
    }

    public Boolean getMust() {
        return must;
    }

    public void must(Boolean must) {
        this.must = must;
    }

}
