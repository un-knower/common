package com.postss.common.sql.builder;

import com.postss.common.sql.dialect.KylinSqlWarp;
import com.postss.common.sql.dialect.SqlWarp;
import com.postss.common.sql.enums.SqlFunction;
import com.postss.common.sql.fragment.ConditionFragment;
import com.postss.common.sql.fragment.InFragment;
import com.postss.common.sql.fragment.SqlFunctionConditionFragment;

/**
 * having条件创建
 * @className KylinHavingCondition
 * @author jwSun
 * @date 2017年8月27日 上午11:43:45
 * @version 1.0.0
 */
public class SqlHavingCondition {

    public static SqlWarp sqlWarp = new KylinSqlWarp();

    public static InFragment in() {
        return new InFragment(sqlWarp);
    }

    public static InFragment in(String tableAlias, String column, SqlFunction sqlFunction, Object... values) {
        return in().setColumn(resolveSqlFuntion(tableAlias, column, sqlFunction)).addValues(values);
    }

    public static SqlFunctionConditionFragment equals() {
        return new SqlFunctionConditionFragment().setOp(" = ");
    }

    public static SqlFunctionConditionFragment equals(String tableAlias, String column, SqlFunction sqlFunction,
            Object value) {
        return equals().setTableAlias(tableAlias).setSqlFunction(column, sqlFunction, value);
    }

    public static SqlFunctionConditionFragment high() {
        return new SqlFunctionConditionFragment().setOp(" > ");
    }

    public static SqlFunctionConditionFragment high(String tableAlias, String column, SqlFunction sqlFunction,
            Object value) {
        return high().setTableAlias(tableAlias).setSqlFunction(column, sqlFunction, value);
    }

    public static SqlFunctionConditionFragment lower() {
        return new SqlFunctionConditionFragment().setOp(" < ");
    }

    public static SqlFunctionConditionFragment lower(String tableAlias, String column, SqlFunction sqlFunction,
            Object value) {
        return lower().setTableAlias(tableAlias).setSqlFunction(column, sqlFunction, value);
    }

    public static SqlFunctionConditionFragment between() {
        return new SqlFunctionConditionFragment().setOp(" between ");
    }

    public static SqlFunctionConditionFragment between(String tableAlias, String column, SqlFunction sqlFunction,
            Object lValue, Object rValue) {
        return between().setTableAlias(tableAlias).setSqlFunction(column, sqlFunction,
                sqlWarp.warp(lValue) + " and " + sqlWarp.warp(rValue));
    }

    public static SqlFunctionConditionFragment like() {
        return new SqlFunctionConditionFragment().setOp(" like ");
    }

    public static SqlFunctionConditionFragment like(String tableAlias, String column, SqlFunction sqlFunction,
            Object value) {
        return like().setTableAlias(tableAlias).setSqlFunction(column, sqlFunction, value);
    }

    public static String toFragmentString(ConditionFragment... fragments) {
        if (fragments == null || fragments.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fragments.length; i++) {
                sb.append(fragments[i].toFragmentString());
                if (i != fragments.length - 1) {
                    sb.append(" and ");
                }
            }
            return sb.toString();
        }
    }

    private static String resolveSqlFuntion(String tableAlias, String column, SqlFunction sqlFunction) {
        return sqlFunction.getSqlFunctionFragment().toFragmentString(null, tableAlias + "." + column);
    }

}
