package com.postss.common.es.es_v_1_4_2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.postss.common.base.page.BasePage;
import com.postss.common.es.es_v_1_4_2.entity.EsCountFilter;
import com.postss.common.es.es_v_1_4_2.entity.EsCountSearch;
import com.postss.common.es.es_v_1_4_2.entity.EsFilter;
import com.postss.common.es.es_v_1_4_2.entity.EsResultEntity;
import com.postss.common.es.es_v_1_4_2.entity.EsSearch;

/**
 * ES查询各步骤封装
 * 
 * @author jwSun
 * @date 2017年5月2日 下午4:02:53
 */
public class EsQueryAction {

    public static final String TERMS_NAME = "count_show_result";

    /**
     * 1.获得多条件查询builder
     * 
     * @author jwSun
     * @date 2017年5月2日 下午3:47:16
     * @param search
     * @param filters
     * @return
     */
    public static BoolQueryBuilder getBoolQueryBuilder(EsSearch search, EsFilter... filters) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (filters != null && filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                EsFilter filter = filters[i];
                if (filter == null)
                    continue;
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(filter.getFieldName());
                if (filter.getGte() != null)
                    rangeQueryBuilder.gte(filter.getGte());
                if (filter.getLte() != null)
                    rangeQueryBuilder.lte(filter.getLte());
                if (filter.getLt() != null)
                    rangeQueryBuilder.lt(filter.getLt());
                if (filter.getGt() != null)
                    rangeQueryBuilder.gt(filter.getGt());
                if (filter.getMust())
                    boolQueryBuilder.must(rangeQueryBuilder);
                else
                    boolQueryBuilder.should(rangeQueryBuilder);
            }
        }

        if (search.getMustMap().size() != 0) {
            search.getMustMap().forEach((String key, Set<String> valSet) -> {
                valSet.forEach((String val) -> {
                    //boolQueryBuilder.must(QueryBuilders.termQuery(key, val.toLowerCase()));
                    boolQueryBuilder.must(QueryBuilders.termQuery(key, val));
                });
            });
        }
        if (search.getShouldMap().size() != 0) {
            search.getShouldMap().forEach((String key, Set<String> valSet) -> {
                valSet.forEach((String val) -> {
                    if (search.getMinimumNumberShouldMatch() != null)
                        boolQueryBuilder.minimumNumberShouldMatch(search.getMinimumNumberShouldMatch());
                    //boolQueryBuilder.should(QueryBuilders.termQuery(key, val.toLowerCase()));
                    boolQueryBuilder.should(QueryBuilders.termQuery(key, val));
                });
            });
        }
        return boolQueryBuilder;
    }

    /**
     * 2.获得QueryBuilder
     * 
     * @author jwSun
     * @date 2017年4月21日 下午4:51:26
     * @param search
     * @return
     */
    public static SearchRequestBuilder getSearchRequestBuilder(EsSearch search, BasePage queryPage,
            QueryBuilder boolQueryBuilder) {

        Set<String> indexsSet = new HashSet<>();
        Set<String> typesSet = new HashSet<>();
        for (int i = 0; i < search.getEsIndexs().size(); i++) {
            IndicesExistsRequest exitRequest = new IndicesExistsRequest(search.getEsIndexs().get(i).getIndex());
            if (EsQueryUtil.getClient().admin().indices().exists(exitRequest).actionGet().isExists()) {
                indexsSet.add(search.getEsIndexs().get(i).getIndex());
                typesSet.add(search.getEsIndexs().get(i).getType());
            }
        }
        if (indexsSet.size() == 0)
            throw new RuntimeException("ES 无选择的索引");
        String[] indexs = indexsSet.toArray(new String[indexsSet.size()]);
        String[] types = typesSet.toArray(new String[typesSet.size()]);
        SearchRequestBuilder builder = EsQueryUtil.getClient().prepareSearch(indexs).setTypes(types)
                .setSearchType(search.getSearchType()).setQuery(boolQueryBuilder).setFrom(queryPage.getBeginNumber())
                .setSize(queryPage.getPageSize());
        if (search.isScroll()) {
            EsQueryAction.addScroll(builder, search.getTimeValue());
        }
        return builder;
    }

    public static SearchScrollRequestBuilder getSearchScrollRequestBuilder(String scrollId, long timeValue) {
        return EsQueryUtil.getClient().prepareSearchScroll(scrollId).setScroll(new TimeValue(timeValue));
    }

    /**
     * 2.多条件查询
     * 
     * @author jwSun
     * @date 2017年4月21日 下午4:51:26
     * @param search
     * @return
     */
    public static MultiSearchRequestBuilder getMultiSearchRequestBuilder(EsSearch search, BasePage queryPage,
            BoolQueryBuilder boolQueryBuilder) {
        /*
         * if (search.getEsIndexs().size() == 1) throw new
         * RuntimeException("EsIndexEntity must more than 1");
         * 
         * MultiSearchRequestBuilder multiSearchRequestBuilder =
         * EsQueryUtil.getClient().prepareMultiSearch(); for (EsIndexEntity
         * entity : search.getEsIndexs()) {
         * multiSearchRequestBuilder.add(EsQueryUtil.getClient().prepareSearch(
         * entity.getIndex())
         * .setTypes(entity.getType()).setSearchType(search.getSearchType()).
         * setQuery(boolQueryBuilder)
         * .setFrom(queryPage.getBeginNumber()).setSize(queryPage.getPageSize())
         * ); }
         */
        return null;
    }

    /**
     * 聚合查询则增加所需参数
     * 
     * @author jwSun
     * @date 2017年5月2日 下午4:03:47
     * @param search
     * @param builder
     */
    public static void addAggregation(EsCountSearch search, SearchRequestBuilder builder) {
        search.getOrderMap().forEach((String field, SortOrder order) -> {
            builder.addSort(field, order);
        });

        TermsBuilder termsBuilder = AggregationBuilders.terms(TERMS_NAME).field(search.getField())
                .size(search.getSize());
        builder.addAggregation(termsBuilder);
    }

    public static void addScroll(SearchRequestBuilder builder, long millis) {
        builder.setScroll(new Scroll(new TimeValue(millis)));
    }

    /**
     * 获得一般结果集
     * 
     * @author jwSun
     * @date 2017年5月2日 下午4:04:07
     * @param builder
     * @param queryPage
     * @return
     */
    public static EsResultEntity getSimpleEsResultEntity(ActionRequestBuilder<?, SearchResponse, ?, ?> builder,
            BasePage queryPage) {
        SearchResponse response = builder.get();
        SearchHits hits = response.getHits();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        hits.forEach((SearchHit searchHit) -> {
            result.add(searchHit.getSource());
        });
        return new EsResultEntity(result, hits.getTotalHits(), queryPage.getBeginNumber(), queryPage.getPageSize())
                .setScrollId(response.getScrollId());
    }

    /**
     * 获得聚合结果集
     * 
     * @author jwSun
     * @date 2017年5月2日 下午4:04:22
     * @param builder
     * @param queryPage
     * @return
     */
    public static EsResultEntity getAggregationEsResultEntity(ActionRequestBuilder<?, SearchResponse, ?, ?> builder,
            BasePage queryPage, EsCountFilter... filters) {
        /*if (builder.toString().indexOf("aggregations") == -1 || builder.toString().indexOf(TERMS_NAME) == -1) {
            throw new RuntimeException("must invoke this before addAggregation method");
        }*/
        SearchResponse response = builder.get();
        // 有序，从大->小
        Map<String, Long> aggregationMap = new LinkedHashMap<>();
        Aggregation aggregation = response.getAggregations().get(TERMS_NAME);

        ((InternalTerms) aggregation).getBuckets().forEach((Bucket bucket) -> {
            if (filters != null) {
                for (EsCountFilter filter : filters) {
                    if (filter.getGte() != null) {
                        if (bucket.getDocCount() < filter.getGte())
                            return;
                    } else if (filter.getGt() != null) {
                        if (bucket.getDocCount() <= filter.getGt())
                            return;
                    }
                    if (filter.getLte() != null) {
                        if (bucket.getDocCount() > filter.getLte())
                            return;
                    } else if (filter.getLt() != null) {
                        if (bucket.getDocCount() >= filter.getLt())
                            return;
                    }
                }
            }

            aggregationMap.put(bucket.getKey().toString(), bucket.getDocCount());
        });

        SearchHits hits = response.getHits();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        hits.forEach((SearchHit searchHit) -> {
            result.add(searchHit.getSource());
        });
        EsResultEntity esEntity = new EsResultEntity(result, hits.getTotalHits(), queryPage.getBeginNumber(),
                queryPage.getPageSize());
        esEntity.setAggregationMap(aggregationMap);
        return esEntity.setScrollId(response.getScrollId());
    }

    /**
     * 获得属性结果集
     * 
     * @author jwSun
     * @date 2017年5月2日 下午4:43:33
     * @param builder
     * @param queryPage
     * @return
     */
    public static EsResultEntity getFieldEsResultEntity(ActionRequestBuilder<?, SearchResponse, ?, ?> builder,
            BasePage queryPage) {
        SearchResponse response = builder.get();

        SearchHits hits = response.getHits();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        hits.forEach((SearchHit searchHit) -> {
            Map<String, Object> map = new HashMap<>();
            searchHit.getFields().forEach((String field, SearchHitField searchHitField) -> {
                map.put(field, searchHitField.getValues());
            });
            result.add(map);
        });

        return new EsResultEntity(result, hits.getTotalHits(), queryPage.getBeginNumber(), queryPage.getPageSize())
                .setScrollId(response.getScrollId());
    }

}
