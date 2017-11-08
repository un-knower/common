package com.postss.common.kylin.sql;

import org.hibernate.dialect.Dialect;

import com.postss.common.base.page.BasePage;
import com.postss.common.sql.builder.SqlCondition;
import com.postss.common.sql.builder.SqlHavingCondition;
import com.postss.common.sql.dialect.KylinDialect;
import com.postss.common.sql.dialect.KylinSqlWarp;
import com.postss.common.sql.dialect.SqlWarp;
import com.postss.common.sql.enums.SqlFunction;
import com.postss.common.sql.fragment.Fragment;
import com.postss.common.sql.fragment.JoinType;
import com.postss.common.sql.fragment.QueryJoinFragment;
import com.postss.common.sql.fragment.QuerySelect;

/**
 * sql创建器
 * @className KylinSqlBuilder
 * @author jwSun
 * @date 2017年8月22日 下午7:19:36
 * @version 1.0.0
 */
public class DefaultKylinSqlBuilder {

    public static QuerySelect createQuery() {
        Dialect dialect = new KylinDialect();
        SqlWarp sqlWarp = new KylinSqlWarp();
        QuerySelect querySelect = new QuerySelect(dialect, sqlWarp);
        return querySelect;
    }

    public static QuerySelect addSelectColumn(QuerySelect querySelect, String tableAlias, String column) {
        querySelect.addSelectColumn(tableAlias, column);
        return querySelect;
    }

    public static QuerySelect addSelectColumn(QuerySelect querySelect, String alias, SqlFunction sqlFunction,
            Object... objects) {
        querySelect.addSelectColumn(alias, sqlFunction, objects);
        return querySelect;
    }

    public static QueryJoinFragment createFrom(QuerySelect querySelect, String factTable, String factTableAlias) {
        Dialect dialect = new KylinDialect();
        QueryJoinFragment join = new QueryJoinFragment(dialect, false);
        join.addFromFragmentString(" " + factTable + " " + factTableAlias + " ");
        querySelect.setJoinFragment(join);
        return join;
    }

    public static QueryJoinFragment addJoin(QueryJoinFragment queryJoinFragment, String tableName, String alias,
            String[] fkColumns, String[] pkColumns) {
        return addJoin(queryJoinFragment, tableName, alias, fkColumns, pkColumns, JoinType.INNER_JOIN);
    }

    public static QueryJoinFragment addJoin(QueryJoinFragment queryJoinFragment, String tableName, String alias,
            String joinTableAlias, String[] fkColumns, String[] pkColumns) {
        String[] strArray = new String[fkColumns.length];
        for (int i = 0; i < strArray.length; i++) {
            strArray[i] = joinTableAlias + "." + fkColumns[i];
        }
        return addJoin(queryJoinFragment, tableName, alias, strArray, pkColumns, JoinType.INNER_JOIN);
    }

    public static QueryJoinFragment addJoin(QueryJoinFragment queryJoinFragment, String tableName, String alias,
            String joinTableAlias, String fkColumn, String pkColumn) {
        return addJoin(queryJoinFragment, tableName, alias, new String[] { joinTableAlias + "." + fkColumn },
                new String[] { pkColumn }, JoinType.INNER_JOIN);
    }

    public static QueryJoinFragment addJoin(QueryJoinFragment queryJoinFragment, String tableName, String alias,
            String joinTableAlias, String[] fkColumns, String[] pkColumns, JoinType joinType) {
        String[] strArray = new String[fkColumns.length];
        for (int i = 0; i < strArray.length; i++) {
            strArray[i] = joinTableAlias + "." + fkColumns[i];
        }
        return addJoin(queryJoinFragment, tableName, alias, strArray, pkColumns, joinType);
    }

    public static QueryJoinFragment addJoin(QueryJoinFragment queryJoinFragment, String tableName, String alias,
            String[] fkColumns, String[] pkColumns, JoinType joinType) {
        queryJoinFragment.addJoin(tableName, alias, fkColumns, pkColumns, joinType);
        return queryJoinFragment;
    }

    public static QuerySelect addGroupBy(QuerySelect querySelect, String tableAlias, String column) {
        querySelect.setGroupByToken(tableAlias, column);
        return querySelect;
    }

    public static QuerySelect addOrderBy(QuerySelect querySelect, String tableAlias, String column) {
        querySelect.setOrderByToken(tableAlias, column);
        return querySelect;
    }

    /**
     * KylinHavingCondition 创建条件
     * @see com.postss.common.sql.builder.KylinHavingCondition
     * @param querySelect
     * @param fragment
     * @return
     */
    public static QuerySelect addHaving(QuerySelect querySelect, Fragment fragment) {
        querySelect.setHavingToken(fragment);
        return querySelect;
    }

    public static void main(String[] args) {
        QuerySelect querySelect = createQuery();
        querySelect.addSelectColumn("DEV_ID_DIM", "KK_LOCATION_NAME");
        addSelectColumn(querySelect, "DATE_DIM", "YF");
        addSelectColumn(querySelect, "TIME_DIM", "XS");
        addSelectColumn(querySelect, "sum_speed", SqlFunction.SUM, "KAKOU_FACT", "SPEED");
        QueryJoinFragment join = createFrom(querySelect, "KAKOU_FACT", "KAKOU_FACT");
        addJoin(join, "DEV_ID_DIM", "DEV_ID_DIM", "KAKOU_FACT", "DEV_ID_UP_KEY", "KK_DWV_ID");
        addJoin(join, "TIME_DIM", "TIME_DIM", "KAKOU_FACT", "TIME_KEY", "TIME_KEY");
        addJoin(join, "DATE_DIM", "DATE_DIM", "KAKOU_FACT", "DATE_KEY", "DATE_KEY");
        querySelect
                .setHavingToken(SqlHavingCondition.between("TIME_DIM", "XS", SqlFunction.valueOf("SUM"), 1000, 2000));
        System.out.println(querySelect.prependWhereConditions(SqlCondition.in("TIME_DIM", "XS", 1, 2))
                .prependWhereConditions(SqlCondition.in("DEV_ID_DIM", "KK_LOCATION_NAME", "康宁路石桥路口北口3", "南山路杨公堤北口"))
                .setGroupByToken("DATE_DIM", "YF").setGroupByToken("DEV_ID_DIM", "KK_LOCATION_NAME")
                .setGroupByToken("TIME_DIM", "XS").setBasePage(new BasePage(1, 5)).toRealQueryString());

    }
}
