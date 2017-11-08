package com.postss.common.es.util;

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
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService.ScriptType;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.pipeline.having.BucketSelectorBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.postss.common.base.page.BasePage;
import com.postss.common.es.entity.EsCountFilter;
import com.postss.common.es.entity.EsFilter;
import com.postss.common.es.entity.EsResultEntity;
import com.postss.common.es.exception.EsSearchException;
import com.postss.common.es.exception.NoSuchIndexException;
import com.postss.common.es.search.EsAggregationSearch;
import com.postss.common.es.search.EsSearch;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.QuickHashMap;

/**
 * ES查询各步骤封装,推荐使用EsQueryUtil封装,否则要按步骤来
 * @author jwSun
 * @date 2017年5月2日 下午4:02:53
 */
public class EsQueryAction {

    private static Logger log = LoggerUtil.getLogger(EsQueryAction.class);

    /**
     * 聚合名
     */
    public static final String TERMS_NAME = "count_show_result";

    /**
     * 1.获得多条件查询builder
     * @date 2017年5月2日 下午3:47:16
     * @param search Es查询实体类
     * @param filters 大小过滤器
     * @return BoolQueryBuilder
     */
    public static BoolQueryBuilder getBoolQueryBuilder(EsSearch search, EsFilter... filters) {
        try {
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
                        //boolQueryBuilder.must(QueryBuilders.termQuery(key, val));
                        boolQueryBuilder.must(QueryBuilders.queryStringQuery(val).defaultField(key)
                                .defaultOperator(search.getOperator()));
                    });
                });
            }
            if (search.getMustNotMap().size() != 0) {
                search.getMustNotMap().forEach((String key, Set<String> valSet) -> {
                    valSet.forEach((String val) -> {
                        boolQueryBuilder.mustNot(QueryBuilders.queryStringQuery(val).defaultField(key)
                                .defaultOperator(search.getOperator()));
                    });
                });
            }
            if (search.getShouldMap().size() != 0) {
                search.getShouldMap().forEach((String key, Set<String> valSet) -> {
                    valSet.forEach((String val) -> {
                        //boolQueryBuilder.should(QueryBuilders.(key, val.toLowerCase()));
                        //boolQueryBuilder.should(QueryBuilders.termQuery(key, val));
                        boolQueryBuilder.should(QueryBuilders.queryStringQuery(val).defaultField(key)
                                .defaultOperator(search.getOperator()));
                    });
                });
            }
            if (search.getMinimumNumberShouldMatch() != null)
                boolQueryBuilder.minimumNumberShouldMatch(search.getMinimumNumberShouldMatch());
            return boolQueryBuilder;
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

    /**
     * 2.获得QueryBuilder
     * @date 2017年6月21日 上午11:53:04
     * @param search Es查询实体类
     * @param queryPage Es查询分页实体类
     * @param boolQueryBuilder {@link EsQueryAction.getBoolQueryBuilder} 返回值
     * @return SearchRequestBuilder
     */
    public static SearchRequestBuilder getSearchRequestBuilder(EsSearch search, BasePage queryPage,
            QueryBuilder boolQueryBuilder) {
        try {
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
                throw new NoSuchIndexException("ES 无选择的索引");
            String[] indexs = indexsSet.toArray(new String[indexsSet.size()]);
            String[] types = typesSet.toArray(new String[typesSet.size()]);
            SearchRequestBuilder builder = EsQueryUtil.getClient().prepareSearch(indexs).setTypes(types)
                    .setSearchType(search.getSearchType()).setQuery(boolQueryBuilder)
                    .setFrom(queryPage.getBeginNumber()).setSize(queryPage.getPageSize());
            return builder;
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

    /**
     * 2.4设置使用Scroll方式查询
     * @date 2017年6月21日 上午11:57:17
     * @param builder
     * @param millis
     */
    public static void addScroll(SearchRequestBuilder builder, long millis) {
        builder.setScroll(new Scroll(new TimeValue(millis)));
    }

    /**
     * 2.5获得Scroll查询Builder，第二次及以上Scroll查询时使用
     * @date 2017年6月21日 上午11:54:39
     * @param scrollId 上一次查询返回的scrollId
     * @param timeValue 查询后数据保存时间
     * @return SearchScrollRequestBuilder
     */
    public static SearchScrollRequestBuilder getSearchScrollRequestBuilder(String scrollId, long timeValue) {
        try {
            return EsQueryUtil.getClient().prepareSearchScroll(scrollId).setScroll(new TimeValue(timeValue));
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

    /**
     * 2.5多条件查询
     * @date 2017年4月21日 下午4:51:26
     * @param search Es查询实体类
     * @return MultiSearchRequestBuilder
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
     * 2.5聚合查询则增加所需参数
     * @date 2017年6月21日 上午11:58:21
     * @param esAggregationSearch Es聚合查询实体类
     * @param builder {@link EsQueryAction.getBoolQueryBuilder} 返回值
     * @since ES2.4.1
     */
    public static void addAggregation(EsAggregationSearch esAggregationSearch, SearchRequestBuilder builder) {
        try {
            esAggregationSearch.getOrderMap().forEach((String field, SortOrder order) -> {
                builder.addSort(field, order);
            });
            TermsBuilder termsBuilder = new TermsBuilder(TERMS_NAME).field(esAggregationSearch.getField())
                    .size(esAggregationSearch.getSize());

            if (esAggregationSearch.getInline() != null) {
                BucketSelectorBuilder bucketSelectorBuilder = new BucketSelectorBuilder("count_show")
                        .setBucketsPathsMap(new QuickHashMap<String, String>().quickPut("count", "_count"));
                Script script = new Script(esAggregationSearch.getInline(), ScriptType.INLINE, "expression", null);
                bucketSelectorBuilder.script(script);
                termsBuilder.subAggregation(bucketSelectorBuilder);
            }

            builder.addAggregation(termsBuilder);
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

    /**
     * 3.获得一般结果集
     * @date 2017年6月21日 上午11:58:55
     * @param builder {@link EsQueryAction.getBoolQueryBuilder} 返回值
     * @param queryPage Es查询分页实体类
     * @return EsResultEntity
     */
    public static EsResultEntity getSimpleEsResultEntity(ActionRequestBuilder<?, SearchResponse, ?> builder,
            BasePage queryPage) {
        try {
            SearchResponse response = builder.get();
            SearchHits hits = response.getHits();
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            hits.forEach((SearchHit searchHit) -> {
                result.add(searchHit.getSource());
            });
            return new EsResultEntity(result, hits.getTotalHits(), queryPage.getBeginNumber(), queryPage.getPageSize())
                    .setScrollId(response.getScrollId());
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

    /**
     * 获得聚合结果集
     * @date 2017年6月21日 上午11:59:24
     * @param builder {@link EsQueryAction.getBoolQueryBuilder} 必须先执行{@link EsQueryAction.addAggregation}
     * @param queryPage Es查询分页实体类
     * @param filters 聚合数量过滤器
     * @return EsResultEntity
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static EsResultEntity getAggregationEsResultEntity(ActionRequestBuilder<?, SearchResponse, ?> builder,
            BasePage queryPage, EsCountFilter... filters) {
        /*if (builder.toString().indexOf("aggregations") == -1 || builder.toString().indexOf(TERMS_NAME) == -1) {
            throw new RuntimeException("must invoke this before addAggregation method");
        }*/
        try {
            log.debug(builder.toString());
            SearchResponse response = builder.get();
            // 有序，从大->小
            Map<String, Long> aggregationMap = new LinkedHashMap<>();
            Aggregation aggregation = response.getAggregations().get(TERMS_NAME);
            InternalTerms internalTerms = (InternalTerms) aggregation;
            List<Terms.Bucket> list = internalTerms.getBuckets();
            list.forEach((Terms.Bucket bucket) -> {
                if (filters != null) {
                    for (EsCountFilter filter : filters) {
                        if (filter != null) {
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
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

    /**
     * 获得属性结果集
     * @date 2017年6月21日 下午12:00:55
     * @param builder {@link EsQueryAction.getBoolQueryBuilder}
     * @param queryPage Es查询分页实体类
     * @return EsResultEntity
     */
    public static EsResultEntity getFieldEsResultEntity(ActionRequestBuilder<?, SearchResponse, ?> builder,
            BasePage queryPage) {
        try {
            SearchResponse response = builder.get();
            SearchHits hits = response.getHits();
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            hits.forEach((SearchHit searchHit) -> {
                Map<String, Object> map = new HashMap<>();
                searchHit.getFields().forEach((String field, SearchHitField searchHitField) -> {
                    if (searchHitField.getValues() != null && searchHitField.getValues().size() == 1) {
                        map.put(field, searchHitField.getValues().get(0));
                    } else {
                        map.put(field, searchHitField.getValues());
                    }

                });
                result.add(map);
            });
            return new EsResultEntity(result, hits.getTotalHits(), queryPage.getBeginNumber(), queryPage.getPageSize())
                    .setScrollId(response.getScrollId());
        } catch (Exception e) {
            throw new EsSearchException(e);
        }
    }

}
