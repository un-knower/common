package com.postss.common.es.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ES返回封装实体类
 * @author jwSun
 * @date 2017年5月2日 上午9:45:03
 */
public class EsResultEntity {

    /**
     * 返回具体数据
     */
    private List<Map<String, Object>> resultList;
    /**
     * 数据总条数
     */
    private long total;
    /**
     * 开始查询条数 从0开始
     */
    protected Integer beginNumber;
    /**
     * 查询数量
     */
    protected Integer pageSize;
    /**
     * 聚合查询map key:查询属性名 val:此属性名分组数量
     */
    private Map<String, Long> aggregationMap = new LinkedHashMap<>();
    /**
     * scroll查询返回id
     */
    private String scrollId;
    /**
     * scroll查询存储时间(ms)
     */
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

    @Override
    public String toString() {
        return "EsResultEntity [resultList=" + resultList + ", total=" + total + ", beginNumber=" + beginNumber
                + ", pageSize=" + pageSize + ", aggregationMap=" + aggregationMap + ", scrollId=" + scrollId
                + ", timeValue=" + timeValue + "]";
    }

}
