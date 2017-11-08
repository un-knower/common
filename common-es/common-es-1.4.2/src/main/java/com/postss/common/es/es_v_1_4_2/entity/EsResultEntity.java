package com.postss.common.es.es_v_1_4_2.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ES返回封装实体类
 * @author jwSun
 * @date 2017年5月2日 上午9:45:03
 */
public class EsResultEntity {

    private List<Map<String, Object>> resultList;//数据
    private long total;//总量
    protected Integer beginNumber;//开始查询条数 从0开始
    protected Integer pageSize;//查询数量
    private Map<String, Long> aggregationMap = new LinkedHashMap<>();//数量查询map key:查询属性名 val:此属性名分组数量
    private String scrollId;//scroll_id
    private long timeValue;

    public EsResultEntity(List<Map<String, Object>> resultList, long total, Integer beginNumber, Integer pageSize) {
        super();
        this.resultList = resultList;
        this.total = total;
        this.beginNumber = beginNumber;
        this.pageSize = pageSize;
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getBeginNumber() {
        return beginNumber;
    }

    public void setBeginNumber(Integer beginNumber) {
        this.beginNumber = beginNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Long> getAggregationMap() {
        return aggregationMap;
    }

    public void setAggregationMap(Map<String, Long> aggregationMap) {
        this.aggregationMap = aggregationMap;
    }

    public String getScrollId() {
        return scrollId;
    }

    public EsResultEntity setScrollId(String scrollId) {
        this.scrollId = scrollId;
        return this;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public EsResultEntity setTimeValue(long timeValue) {
        this.timeValue = timeValue;
        return this;
    }

}
