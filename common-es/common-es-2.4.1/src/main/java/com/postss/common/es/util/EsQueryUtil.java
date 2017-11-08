package com.postss.common.es.util;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.util.Assert;

import com.postss.common.base.page.BasePage;
import com.postss.common.base.page.PageBuilder;
import com.postss.common.es.entity.EsFilter;
import com.postss.common.es.entity.EsResultEntity;
import com.postss.common.es.search.EsAggregationSearch;
import com.postss.common.es.search.EsFieldSearch;
import com.postss.common.es.search.EsScrollFieldSearch;
import com.postss.common.es.search.EsScrollSearch;
import com.postss.common.es.search.EsSearch;

/**
 * ES查询工具类
 * @ClassName EsQueryUtil
 * @author jwSun
 * @Date 2017年6月24日 下午7:17:43
 * @version 1.0.0
 */
public class EsQueryUtil extends AbstractEsUtil {

    /**
     * 查询数据,获取所有字段值
     * @author jwSun
     * @date 2017年4月21日 下午5:58:26
     * @param esSearch Es查询实体类
     * @param queryPage 查询分页实体类
     * @return EsResultEntity
     */
    public static EsResultEntity querySimpleList(EsSearch esSearch, BasePage queryPage, EsFilter... filters) {
        QueryBuilder boolQueryBuilder = EsQueryAction.getBoolQueryBuilder(esSearch, filters);
        if (queryPage == null)
            queryPage = countPage;
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(esSearch, queryPage, boolQueryBuilder);
        EsResultEntity entity = EsQueryAction.getSimpleEsResultEntity(builder, queryPage);
        return entity;
    }

    /**
     * 使用滚动(Scroll)方式搜索
     * @param esScrollSearch esScroll实体类
     * @param queryPage 查询分页实体类
     * @param filters ES过滤器
     * @return EsResultEntity
     */
    public static EsResultEntity queryScrollList(EsScrollSearch esScrollSearch, BasePage queryPage,
            EsFilter... filters) {
        QueryBuilder boolQueryBuilder = EsQueryAction.getBoolQueryBuilder(esScrollSearch, filters);
        if (queryPage == null)
            queryPage = countPage;
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(esScrollSearch, queryPage,
                boolQueryBuilder);
        EsQueryAction.addScroll(builder, esScrollSearch.getTimeValue());
        EsResultEntity entity = EsQueryAction.getSimpleEsResultEntity(builder, queryPage);
        entity.setTimeValue(esScrollSearch.getTimeValue());

        return entity;
    }

    /**
     * 使用滚动(Scroll)方式搜索
     * @param esScrollSearch esScroll实体类
     * @param queryPage 查询分页实体类
     * @param filters ES过滤器
     * @return EsResultEntity
     */
    public static EsResultEntity queryScrollFieldList(EsScrollFieldSearch esScrollFieldSearch, BasePage queryPage,
            EsFilter... filters) {
        QueryBuilder boolQueryBuilder = EsQueryAction.getBoolQueryBuilder(esScrollFieldSearch, filters);
        if (queryPage == null)
            queryPage = countPage;
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(esScrollFieldSearch, queryPage,
                boolQueryBuilder);
        EsQueryAction.addScroll(builder, esScrollFieldSearch.getTimeValue());
        builder.addFields(esScrollFieldSearch.getFields());
        EsResultEntity entity = EsQueryAction.getFieldEsResultEntity(builder, queryPage);
        entity.setTimeValue(esScrollFieldSearch.getTimeValue());
        return entity;
    }

    /**
     * 聚合查询
     * @date 2017年4月21日 下午5:58:26
     * @param esAggregationSearch Es聚合查询实体类
     * @param queryPage 查询分页实体类
     * @return EsResultEntity
     */
    public static EsResultEntity queryAggregationList(EsAggregationSearch esAggregationSearch, BasePage queryPage,
            EsFilter... filters) {
        if (queryPage == null)
            queryPage = countPage;
        BoolQueryBuilder boolQueryBuilder = EsQueryAction.getBoolQueryBuilder(esAggregationSearch, filters);
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(esAggregationSearch, queryPage,
                boolQueryBuilder);
        EsQueryAction.addAggregation(esAggregationSearch, builder);
        EsResultEntity entity = EsQueryAction.getAggregationEsResultEntity(builder, queryPage,
                esAggregationSearch.getEsCountFilter());
        return entity;
    }

    /**
     * 查询数据,获取指定部分字段值
     * @param esFieldSearch es属性值查询实体类
     * @param queryPage 查询分页实体类
     * @param filters ES过滤器
     * @return EsResultEntity
     */
    public static EsResultEntity queryFieldList(EsFieldSearch esFieldSearch, BasePage queryPage, EsFilter... filters) {
        if (queryPage == null)
            queryPage = countPage;
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(esFieldSearch, queryPage,
                EsQueryAction.getBoolQueryBuilder(esFieldSearch, filters));
        builder.addFields(esFieldSearch.getFields());
        EsResultEntity entity = EsQueryAction.getFieldEsResultEntity(builder, queryPage);
        return entity;
    }

    /**
     * 查询数量
     * @date 2017年4月24日 下午1:41:53
     * @param esSearch es基本查询实体类
     * @return EsResultEntity
     */
    public static EsResultEntity queryCount(EsSearch esSearch, EsFilter... filters) {
        SearchRequestBuilder builder = EsQueryAction.getSearchRequestBuilder(esSearch, countPage,
                EsQueryAction.getBoolQueryBuilder(esSearch, filters));
        EsResultEntity entity = EsQueryAction.getSimpleEsResultEntity(builder, countPage);
        return entity;
    }

    /**
     * 二次或以上滚动(scroll)查询
     * @param esResultEntity 上一次查询返回的实体类
     * @return EsResultEntity
     */
    public static EsResultEntity queryByScroll(EsResultEntity esResultEntity) {
        Assert.isTrue(esResultEntity.getTimeValue() != 0L);
        Assert.notNull(esResultEntity.getScrollId());
        SearchScrollRequestBuilder builder = EsQueryAction.getSearchScrollRequestBuilder(esResultEntity.getScrollId(),
                esResultEntity.getTimeValue());
        EsResultEntity entity = EsQueryAction.getFieldEsResultEntity(builder,
                PageBuilder.begin().setBeginNumber(esResultEntity.getBeginNumber() + esResultEntity.getPageSize())
                        .setPageSize(esResultEntity.getPageSize()).build());
        entity.setTimeValue(esResultEntity.getTimeValue());
        return entity;
    }
}
