package com.postss.common.kylin.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.postss.common.kylin.api.search.KylinApiSearch;
import com.postss.common.kylin.entity.ApiSqlResultEntity;
import com.postss.common.kylin.search.ApiSqlSearch;
import com.postss.common.kylin.search.KylinBodySearch;
import com.postss.common.util.CollectionUtil;
import com.postss.common.util.JSONObjectUtil;

/**
 * kylin查询工具类
 * @className KylinQueryUtil
 * @author jwSun
 * @date 2017年8月22日 下午7:24:24
 * @version 1.0.0
 */
public class KylinQueryUtil {

    /**
     * 查询列表
     * @param kylinApiSearch
     * @return
     */
    public static List<JSONObject> queryList(KylinApiSearch kylinApiSearch) {
        JSONArray apiListFind = KylinQueryAction.queryByApiSearch(kylinApiSearch, JSONArray.class);
        List<JSONObject> returnList = new ArrayList<>();
        if (!CollectionUtil.isEmpty(apiListFind)) {
            apiListFind.forEach((result) -> {
                returnList.add((JSONObject) result);
            });
        }
        return returnList;
    }

    /**
     * 查询列表并转化
     * @param kylinApiSearch
     * @param clazz
     * @return
     */
    public static <T> List<T> queryList(KylinApiSearch kylinApiSearch, Class<T> clazz) {
        String cubesListFind = KylinQueryAction.queryByApiSearch(kylinApiSearch);
        return JSONObjectUtil.parseArray(cubesListFind, clazz);
    }

    /**
     * 查询单个数据
     * @param kylinApiSearch
     * @return
     */
    public static JSONObject queryOne(KylinApiSearch kylinApiSearch) {
        return KylinQueryAction.queryByApiSearch(kylinApiSearch, JSONObject.class);
    }

    /**
     * 查询单个数据并转化
     * @param kylinApiSearch
     * @param clazz
     * @return
     */
    public static <T> T queryOne(KylinApiSearch kylinApiSearch, Class<T> clazz) {
        return KylinQueryAction.queryByApiSearch(kylinApiSearch, clazz);
    }

    /**
     * 查询sql
     * @param apiSqlSearch
     * @return
     */
    public static ApiSqlResultEntity queryByApiSql(ApiSqlSearch apiSqlSearch) {
        String response = queryBodySearch(apiSqlSearch).toJSONString();
        ApiSqlResultEntity apiSqlResultEntity = JSONObject.parseObject(response, ApiSqlResultEntity.class);
        return apiSqlResultEntity;
    }

    /**
     * 请求体查询
     * @param kylinBodySearch
     * @return
     */
    public static JSONObject queryBodySearch(KylinBodySearch kylinBodySearch) {
        String response = KylinQueryAction.queryByBodySearch(kylinBodySearch);
        return JSONObject.parseObject(response);
    }
}
