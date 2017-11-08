package com.postss.common.sql.builder;

import com.postss.common.sql.dialect.KylinSqlWarp;
import com.postss.common.sql.dialect.SqlWarp;
import com.postss.common.sql.fragment.ConditionFragment;
import com.postss.common.sql.fragment.InFragment;

/**
 * where 条件创建
 * @className SqlCondition
 * @author jwSun
 * @date 2017年8月27日 上午11:44:41
 * @version 1.0.0
 */
public class SqlCondition {

    public static SqlWarp sqlWarp = new KylinSqlWarp();

    public static InFragment in() {
        return new InFragment(sqlWarp);
    }

    public static InFragment in(String tableAlias, String column, Object... values) {
        return in().setColumn(tableAlias + "." + column).addValues(values);
    }

    public static ConditionFragment equals() {
        return new ConditionFragment().setOp(" = ");
    }

    public static ConditionFragment equals(String tableAlias, String column, Object value) {
        return equals().setTableAlias(tableAlias).setCondition(new Object[] { column }, new Object[] { value });
    }

    public static ConditionFragment equals(String tableAlias, String column, Object rTableAlias, Object value) {
        return equals().setTableAlias(tableAlias).setCondition(new Object[] { column },
                new Object[] { rTableAlias + "." + value });
    }

    public static ConditionFragment high() {
        return new ConditionFragment().setOp(" > ");
    }

    public static ConditionFragment high(String tableAlias, String column, Object value) {
        return high().setTableAlias(tableAlias).setCondition(new Object[] { column }, new Object[] { value });
    }

    public static ConditionFragment lower() {
        return new ConditionFragment().setOp(" < ");
    }

    public static ConditionFragment lower(String tableAlias, String column, Object value) {
        return lower().setTableAlias(tableAlias).setCondition(new Object[] { column }, new Object[] { value });
    }

    public static ConditionFragment between() {
        return new ConditionFragment().setOp(" between ");
    }

    public static ConditionFragment between(String tableAlias, String column, Object lValue, Object rValue) {
        return between().setTableAlias(tableAlias).setCondition(new String[] { column },
                new String[] { sqlWarp.warp(lValue) + " and " + sqlWarp.warp(rValue) });
    }

    public static ConditionFragment like() {
        return new ConditionFragment().setOp(" like ");
    }

    public static ConditionFragment like(String tableAlias, String column, Object value) {
        return like().setTableAlias(tableAlias).setCondition(new String[] { column }, new Object[] { value });
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

}
