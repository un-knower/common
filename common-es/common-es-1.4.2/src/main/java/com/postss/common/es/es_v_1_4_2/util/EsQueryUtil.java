package com.postss.common.es.es_v_1_4_2.util;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.util.Assert;

import com.postss.common.base.page.BasePage;
import com.postss.common.base.page.PageBuilder;
import com.postss.common.es.es_v_1_4_2.entity.EsCountSearch;
import com.postss.common.es.es_v_1_4_2.entity.EsFieldSearch;
import com.postss.common.es.es_v_1_4_2.entity.EsFilter;
import com.postss.common.es.es_v_1_4_2.entity.EsResultEntity;
import com.postss.common.es.es_v_1_4_2.entity.EsSearch;

/**
 * ES查询工具类
 * @author jwSun
 * @date 2017年4月18日 上午10:29:00
 */
public class EsQueryUtil extends AbstractEsUtil {

    /**
     * 查询数据,获取所有字段值
     * @author jwSun
     * @date 2017年4月21日 下午5:58:26
     * @param search
     * @param queryPage
     * @return
     */
    public static EsResultEntity querySimpleList(EsSearch search, BasePage queryPage, EsFilter... filters) {
        QueryBuilder boolQueryBuilder = EsQueryAction.getBoolQueryBuilder(search, filters);
        if (queryPage == null)
            queryPage = countPage;
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(search, queryPage, boolQueryBuilder);
        if (search.isScroll()) {
            EsQueryAction.addScroll(builder, search.getTimeValue());
        }
        EsResultEntity entity = EsQueryAction.getSimpleEsResultEntity(builder, queryPage);
        if (search.isScroll()) {
            entity.setTimeValue(search.getTimeValue());
        }
        return entity;
    }

    /**
     * 聚合查询
     * @author jwSun
     * @date 2017年4月21日 下午5:58:26
     * @param search
     * @param queryPage
     * @return
     */
    public static EsResultEntity queryAggregationList(EsCountSearch search, BasePage queryPage, EsFilter... filters) {
        BoolQueryBuilder boolQueryBuilder = EsQueryAction.getBoolQueryBuilder(search, filters);
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(search, queryPage, boolQueryBuilder);
        EsQueryAction.addAggregation(search, builder);
        EsResultEntity entity = EsQueryAction.getAggregationEsResultEntity(builder, queryPage,
                search.getEsCountFilter());
        if (search.isScroll()) {
            entity.setTimeValue(search.getTimeValue());
        }
        return entity;
    }

    /**
     * 查询数据,获取指定部分字段值
     * @author jwSun
     * @date 2017年4月18日 下午1:56:28
     * @param search
     * @return
     */
    public static EsResultEntity queryFieldList(EsFieldSearch search, BasePage queryPage, EsFilter... filters) {
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(search, queryPage,
                EsQueryAction.getBoolQueryBuilder(search, filters));
        builder.addFields(search.getFields());
        EsResultEntity entity = EsQueryAction.getFieldEsResultEntity(builder, queryPage);
        if (search.isScroll()) {
            entity.setTimeValue(search.getTimeValue());
        }
        return entity;
    }

    /**
     * 查询数量
     * @author jwSun
     * @date 2017年4月24日 下午1:41:53
     * @param search
     * @return
     */
    public static EsResultEntity queryCount(EsSearch search, EsFilter... filters) {
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(search, countPage,
                EsQueryAction.getBoolQueryBuilder(search, filters));
        EsResultEntity entity = EsQueryAction.getSimpleEsResultEntity(builder, countPage);
        if (search.isScroll()) {
            entity.setTimeValue(search.getTimeValue());
        }
        return entity;
    }

    /**
     * scroll查询
     * @author jwSun
     * @date 2017年4月24日 下午1:41:53
     * @param search
     * @return
     */
    public static EsResultEntity queryByScroll(EsResultEntity esResultEntity) {
        Assert.isTrue(esResultEntity.getTimeValue() != 0L);
        Assert.notNull(esResultEntity.getScrollId());
        SearchScrollRequestBuilder builder = EsQueryAction.getSearchScrollRequestBuilder(esResultEntity.getScrollId(),
                esResultEntity.getTimeValue());
        EsResultEntity entity = EsQueryAction.getSimpleEsResultEntity(builder,
                PageBuilder.begin().setBeginNumber(0).setPageSize(0).build());
        entity.setTimeValue(esResultEntity.getTimeValue());
        return entity;
    }

}
