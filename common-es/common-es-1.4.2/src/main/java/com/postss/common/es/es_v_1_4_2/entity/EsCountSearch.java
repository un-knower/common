package com.postss.common.es.es_v_1_4_2.entity;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.search.sort.SortOrder;

/**
 * (不通用)查询通行次数实体类
 * @author jwSun
 * @date 2017年4月25日 下午4:18:40
 */
public class EsCountSearch extends EsSearch {

    private String field;//属性名
    private int size;//查询结果条数
    private String inline;//如count>0 && count <10
    private Map<String, SortOrder> orderMap;//暂未实现,默认大->小
    private EsCountFilter esCountFilter;//count过滤

    public EsCountSearch(String index, String type, String field, int size, String inline) {
        super(index, type);
        this.field = field;
        this.size = size;
        this.inline = inline;
        this.orderMap = new HashMap<>();
    }

    public EsCountSearch(String index, String type, String field, int size, EsCountFilter esCountFilter,
            Map<String, SortOrder> orderMap) {
        super(index, type);
        this.field = field;
        this.size = size;
        this.esCountFilter = esCountFilter;
        this.orderMap = orderMap;
    }

    public EsCountSearch(String index, String type, String field, int size, EsCountFilter esCountFilter) {
        super(index, type);
        this.field = field;
        this.size = size;
        this.esCountFilter = esCountFilter;
        this.orderMap = new HashMap<>();
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
