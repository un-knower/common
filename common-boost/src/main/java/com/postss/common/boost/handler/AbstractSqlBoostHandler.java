package com.postss.common.boost.handler;

import java.util.List;
import java.util.Map;

import org.springframework.util.AntPathMatcher;

import com.postss.common.sql.dialect.DefaultSqlWarp;
import com.postss.common.sql.manager.DefaultSqlWildCardManager;
import com.postss.common.util.ConvertUtil;
import com.postss.common.util.JpaUtil;

@SuppressWarnings("unchecked")
public abstract class AbstractSqlBoostHandler extends AbstractBoostHandler {

    private DefaultSqlWildCardManager sqlWildCardManager = new DefaultSqlWildCardManager(new DefaultSqlWarp());

    protected AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 执行sql语句
     * @param sql 要执行的sql语句
     * @return
     */
    protected List<?> executeSql(Map<String, Object> replaceMap, String sql) {
        String executeSql = replaceSql(replaceMap, sql);
        log.debug("execute sql : {}", executeSql);
        return JpaUtil.execute(executeSql);
    }

    /**
     * 执行并组装sql语句
     * @param sql 要执行的sql语句
     * @param fieldsName 组装名
     * @return
     */
    protected List<Map<String, Object>> executeSql(Map<String, Object> replaceMap, String sql, String[] fieldsName) {
        return ConvertUtil.parseObjToMap((List<Object>) executeSql(replaceMap, sql), fieldsName);
    }

    /**
     * 执行并组装sql语句
     * @param sql 要执行的sql语句
     * @param fieldsName 组装名
     * @return
     */
    protected List<Map<String, Object>> executeSqlAuto(Map<String, Object> replaceMap, String sql) {
        List<String> autoFieldsName = sqlWildCardManager.getAutoFieldsNames(sql);
        log.debug("auto get fieldsName:{}", autoFieldsName);
        return executeSql(replaceMap, sql, autoFieldsName.toArray(new String[autoFieldsName.size()]));
    }

    /**
     * 替换sql通配符
     * @param replaceMap
     * @param sql
     * @return
     */
    protected String replaceSql(Map<String, Object> replaceMap, String sql) {
        return sqlWildCardManager.replace(sql, replaceMap);
    }
}
