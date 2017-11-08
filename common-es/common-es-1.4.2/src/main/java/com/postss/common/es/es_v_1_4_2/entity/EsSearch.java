package com.postss.common.es.es_v_1_4_2.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchType;

/**
 * ES基本查询实体类
 * @author jwSun
 * @date 2017年4月18日 上午11:56:22
 */
public class EsSearch extends BaseEsIndex {

    private SearchType searchType = SearchType.QUERY_THEN_FETCH;//默认查询类型,查出来的size与用户指定相同
    private Map<String, Set<String>> mustMap = new HashMap<>();//必要查询内容
    private Map<String, Set<String>> shouldMap = new HashMap<>();//需要查询内容(非必要)
    private Integer minimumNumberShouldMatch;//should至少匹配数量
    private Set<String> queryKey = new HashSet<>();//全文搜索
    private boolean scroll = false;
    private long timeValue = 0L;
    private String scrollId;

    public EsSearch(String index, String type, SearchType searchType) {
        super(index, type);
        this.searchType = searchType;
    }

    public EsSearch(String index, String type) {
        super(index, type);
    }

    public EsSearch putQuerySet(String val) {
        this.queryKey.add(val);
        return this;
    }

    public EsSearch must(String key, String val) {
        return this.must(key, val, Boolean.FALSE);
    }

    /**
     * 添加must条件
     * @author jwSun
     * @date 2017年4月24日 下午3:11:52
     * @param key 属性名
     * @param val 属性值
     * @param cover 是否覆盖
     * @return
     */
    public EsSearch must(String key, String val, Boolean cover) {
        if (this.mustMap.get(key) == null || cover) {
            Set<String> set = new HashSet<String>();
            set.add(val);
            this.mustMap.put(key, set);
        } else {
            this.mustMap.get(key).add(val);
        }
        return this;
    }

    public EsSearch should(String key, String val) {
        return this.should(key, val, Boolean.FALSE);
    }

    public EsSearch should(String key, String val, Boolean cover) {
        if (this.shouldMap.get(key) == null || cover) {
            Set<String> set = new HashSet<String>();
            set.add(val);
            this.shouldMap.put(key, set);
        } else {
            this.shouldMap.get(key).add(val);
        }
        return this;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public Set<String> getQueryKey() {
        return queryKey;
    }

    public Map<String, Set<String>> getMustMap() {
        return mustMap;
    }

    public Map<String, Set<String>> getShouldMap() {
        return shouldMap;
    }

    public Integer getMinimumNumberShouldMatch() {
        return minimumNumberShouldMatch;
    }

    public void setMinimumNumberShouldMatch(Integer minimumNumberShouldMatch) {
        this.minimumNumberShouldMatch = minimumNumberShouldMatch;
    }

    public boolean isScroll() {
        return scroll;
    }

    public EsSearch setScroll(boolean scroll, long timeValue) {
        if (scroll) {
            this.timeValue = timeValue;
        }
        this.scroll = scroll;
        return this;
    }

    public long getTimeValue() {
        return timeValue;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }
}
