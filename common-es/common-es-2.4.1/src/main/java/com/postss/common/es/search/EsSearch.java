package com.postss.common.es.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;

import com.postss.common.es.entity.BaseEsIndex;

/**
 * ES基本查询实体类
 * @author jwSun
 * @date 2017年4月18日 上午11:56:22
 */
public class EsSearch extends BaseEsIndex {

    /**
     * 默认查询类型,查出来的size与用户指定相同
     */
    private SearchType searchType = SearchType.QUERY_THEN_FETCH;
    /**
     * 必要查询内容
     */
    private Map<String, Set<String>> mustMap = new HashMap<>();
    /**
     * 必要查询内容
     */
    private Map<String, Set<String>> mustNotMap = new HashMap<>();
    /**
     * 需要查询内容(非必要)
     */
    private Map<String, Set<String>> shouldMap = new HashMap<>();
    /**
     * should至少匹配数量
     */
    private Integer minimumNumberShouldMatch;
    /**
     * 查询分词时使用模式(ES默认OR 本类默认AND),一般情况无需修改
     */
    private Operator operator = Operator.AND;

    public EsSearch(String index, String type, SearchType searchType) {
        super(index, type);
        this.searchType = searchType;
    }

    public EsSearch(String index, String type) {
        super(index, type);
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

    public EsSearch mustAll(Map<String, Set<String>> mustMap) {
        this.mustMap.putAll(mustMap);
        return this;
    }

    public EsSearch mustNot(String key, String val) {
        return this.mustNot(key, val, Boolean.FALSE);
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
    public EsSearch mustNot(String key, String val, Boolean cover) {
        if (this.mustNotMap.get(key) == null || cover) {
            Set<String> set = new HashSet<String>();
            set.add(val);
            this.mustNotMap.put(key, set);
        } else {
            this.mustNotMap.get(key).add(val);
        }
        return this;
    }

    public EsSearch mustNotAll(Map<String, Set<String>> mustMap) {
        this.mustNotMap.putAll(mustMap);
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

    public EsSearch shouldAll(Map<String, Set<String>> shouldMap) {
        this.shouldMap.putAll(shouldMap);
        return this;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public Map<String, Set<String>> getMustMap() {
        return mustMap;
    }

    public Map<String, Set<String>> getMustNotMap() {
        return mustNotMap;
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

    /**
     * @Description 根据参数增加最小匹配数，若minimumNumberShouldMatch为空则初始化并为参数i
     * @param i 增加最小匹配数
     */
    public void addMinimumNumberShouldMatch(int i) {
        if (this.minimumNumberShouldMatch == null) {
            this.minimumNumberShouldMatch = i;
        } else {
            this.minimumNumberShouldMatch += i;
        }
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "EsSearch [searchType=" + searchType + ", mustMap=" + mustMap + ", shouldMap=" + shouldMap
                + ", minimumNumberShouldMatch=" + minimumNumberShouldMatch + ", operator=" + operator + "]";
    }

}
