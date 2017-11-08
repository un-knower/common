package com.postss.common.es.search;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.search.sort.SortOrder;

import com.postss.common.es.entity.EsCountFilter;

/**
 * Es聚合查询类
 * @author jwSun
 * @date 2017年6月21日 上午11:33:58
 */
public class EsAggregationSearch extends EsSearch {

    /**
     * 聚合属性名
     */
    private String field;
    /**
     * 查询结果条数
     */
    private int size = 10;
    /**
     * 如count>0 && count <10
     */
    private String inline;
    /**
     * 暂未实现,默认大->小
     */
    private Map<String, SortOrder> orderMap = new HashMap<String, SortOrder>();
    /**
     * 聚合条数过滤
     */
    private EsCountFilter esCountFilter;

    public EsAggregationSearch(String index, String type) {
        super(index, type);
    }

    public EsAggregationSearch(String index, String type, String field) {
        super(index, type);
        this.field = field;
    }

    public EsAggregationSearch setField(String field) {
        this.field = field;
        return this;
    }

    public EsAggregationSearch setSize(int size) {
        this.size = size;
        return this;
    }

    public EsAggregationSearch setInline(String inline) {
        this.inline = inline;
        return this;
    }

    public EsAggregationSearch setOrderMap(Map<String, SortOrder> orderMap) {
        this.orderMap = orderMap;
        return this;
    }

    public EsAggregationSearch setEsCountFilter(EsCountFilter esCountFilter) {
        this.esCountFilter = esCountFilter;
        return this;
    }

    public String getField() {
        return field;
    }

    public int getSize() {
        return size;
    }

    public String getInline() {
        return inline;
    }

    public Map<String, SortOrder> getOrderMap() {
        return orderMap;
    }

    public EsCountFilter getEsCountFilter() {
        return esCountFilter;
    }

}
