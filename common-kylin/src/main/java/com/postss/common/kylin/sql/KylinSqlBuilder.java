package com.postss.common.kylin.sql;

import com.postss.common.base.page.BasePage;
import com.postss.common.kylin.exception.KylinSqlBuilderException;
import com.postss.common.sql.builder.SqlCondition;
import com.postss.common.sql.builder.SqlHavingCondition;
import com.postss.common.sql.enums.SqlFunction;
import com.postss.common.sql.fragment.Fragment;
import com.postss.common.sql.fragment.JoinType;
import com.postss.common.sql.fragment.QueryJoinFragment;
import com.postss.common.sql.fragment.QuerySelect;

/**
 * kylin sql 创建
 * @className KylinSqlBuilder
 * @author jwSun
 * @date 2017年8月27日 上午11:41:37
 * @version 1.0.0
 */
public class KylinSqlBuilder {

    /**
     * 使用示例
     * @param args
     */
    public static void main(String[] args) {
        DefaultSqlBodyBuilder sqlBuilder = KylinSqlBuilder.createBuilder("KAKOU_FACT", "KAKOU_FACT");
        sqlBuilder.addSelectColumn("DEV_ID_DIM", "KK_LOCATION_NAME");
        sqlBuilder.addSelectColumn("DATE_DIM", "YF");
        sqlBuilder.addSelectColumn("TIME_DIM", "XS");
        sqlBuilder.addSelectColumn("sum_speed", SqlFunction.SUM, "KAKOU_FACT", "SPEED");
        sqlBuilder.addJoin("DEV_ID_DIM", "DEV_ID_DIM", "KAKOU_FACT", "DEV_ID_UP_KEY", "KK_DWV_ID");
        sqlBuilder.addJoin("DEV_ID_DIM", "DEV_ID_DIM", "KAKOU_FACT", "DEV_ID_UP_KEY", "KK_DWV_ID");
        sqlBuilder.addJoin("DATE_DIM", "DATE_DIM", "KAKOU_FACT", "DATE_KEY", "DATE_KEY");
        sqlBuilder.addGroupBy("DATE_DIM", "YF");
        sqlBuilder.addGroupBy("DEV_ID_DIM", "KK_LOCATION_NAME");
        sqlBuilder.addGroupBy("TIME_DIM", "XS");
        sqlBuilder.addHaving(SqlHavingCondition.between("TIME_DIM", "XS", SqlFunction.valueOf("SUM"), 1000, 2000));
        sqlBuilder.prependWhereConditions(SqlCondition.in("TIME_DIM", "XS", 1, 2));
        sqlBuilder.prependWhereConditions(SqlCondition.in("DEV_ID_DIM", "KK_LOCATION_NAME", "康宁路石桥路口北口3", "南山路杨公堤北口"));
        sqlBuilder.setPage(new BasePage(1, 5));
        System.out.println(sqlBuilder.build().toRealQueryString());
    }

    /**
     * 创建器
     * @param factTable 事实表
     * @param factTableAlias 事实表别名
     * @return
     */
    public static DefaultSqlBodyBuilder createBuilder(String factTable, String factTableAlias) {
        return new DefaultSqlBodyBuilderImpl(factTable, factTableAlias);
    }

    public interface SqlBody<T extends SqlBody<T>> {

        /**
         * 增加查找字段
         * @param tableAlias 查找表别名
         * @param column 查找字段名
         * @return
         */
        T addSelectColumn(String tableAlias, String column);

        /**
         * 增加查找函数
         * @param alias 函数字段别名
         * @param sqlFunction {@link com.postss.common.sql.enums.SqlFunction}
         * @param objects sql函数拼接值
         * @return
         */
        T addSelectColumn(String alias, SqlFunction sqlFunction, Object... objects);

        /**
         * 增加inner join表
         * @param tableName join表名
         * @param alias join表别名
         * @param fkColumns 关联外键 如关联 fact.column 则填写 fact.column
         * @param pkColumns 关联主键
         * @return
         */
        T addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns);

        /**
         * 增加inner join表
         * @param tableName join表名
         * @param alias join表别名
         * @param joinTableAlias 关联表别名
         * @param fkColumns 关联外键 如关联 fact.column 则填写 column
         * @param pkColumns 关联主键
         * @return
         */
        T addJoin(String tableName, String alias, String joinTableAlias, String[] fkColumns, String[] pkColumns);

        /**
         * 增加inner join表
         * @param tableName join表名
         * @param alias join表别名
         * @param joinTableAlias 关联表别名
         * @param fkColumn 关联外键 如关联 fact.column 则填写 column
         * @param pkColumn 关联主键
         * @return
         */
        T addJoin(String tableName, String alias, String joinTableAlias, String fkColumn, String pkColumn);

        /**
         * 增加join表
         * @param tableName join表名
         * @param alias join表别名
         * @param joinTableAlias 关联表别名
         * @param fkColumns 关联外键 如关联 fact.column 则填写 column
         * @param pkColumns 关联主键
         * @param joinType 关联类型 {@link com.postss.common.sql.fragment.JoinType}
         * @return
         */
        T addJoin(String tableName, String alias, String joinTableAlias, String[] fkColumns, String[] pkColumns,
                JoinType joinType);

        /**
         * 增加join表
         * @param tableName join表名
         * @param alias join表别名
         * @param fkColumns 关联外键 如关联 fact.column 则填写 column
         * @param pkColumns 关联主键
         * @param joinType 关联类型 {@link com.postss.common.sql.fragment.JoinType}
         * @return
         */
        T addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType);

        /**
         * 增加 where条件
         * @param fragment 见{@link com.postss.common.sql.builder.SqlCondition}
         * @return
         */
        T prependWhereConditions(Fragment fragment);

        /**
         * 增加 分组条件
         * @param tableAlias 表别名
         * @param column 字段
         * @return
         */
        T addGroupBy(String tableAlias, String column);

        /**
         * 增加 排序条件
         * @param tableAlias 表别名
         * @param column 字段
         * @return
         */
        T addOrderBy(String tableAlias, String column);

        /**
         * 增加 having条件
         * @param fragment 见{@link com.postss.common.sql.builder.SqlHavingCondition}
         * @return
         */
        T addHaving(Fragment fragment);

        /**
         * 增加分页条件
         * @param page {@link com.postss.common.base.page.BasePage}
         * @return
         */
        T setPage(BasePage page);

        /**
         * 创建查询对象
         * @return QuerySelect
         */
        QuerySelect build();
    }

    /**
     * 默认kylin sql创建器
     * @className DefaultSqlBodyBuilder
     * @author jwSun
     * @date 2017年8月27日 上午11:42:29
     * @version 1.0.0
     */
    public interface DefaultSqlBodyBuilder extends SqlBody<DefaultSqlBodyBuilder> {

    }

    private static class DefaultSqlBodyBuilderImpl implements DefaultSqlBodyBuilder {

        private QuerySelect querySelect;
        private QueryJoinFragment queryJoinFragment;
        @SuppressWarnings("unused")
        private String factTable;
        @SuppressWarnings("unused")
        private String factTableAlias;

        public DefaultSqlBodyBuilderImpl(String factTable, String factTableAlias) {
            super();
            this.factTable = factTable;
            this.factTableAlias = factTableAlias;
            this.querySelect = DefaultKylinSqlBuilder.createQuery();
            this.queryJoinFragment = DefaultKylinSqlBuilder.createFrom(querySelect, factTable, factTableAlias);
        }

        public QuerySelect getQuerySelect() {
            return querySelect;
        }

        /*public void setQuerySelect(QuerySelect querySelect) {
            this.querySelect = querySelect;
        }*/

        public QueryJoinFragment getQueryJoinFragment() {
            return queryJoinFragment;
        }

        /*public void setQueryJoinFragment(QueryJoinFragment queryJoinFragment) {
            this.queryJoinFragment = queryJoinFragment;
        }*/

        @Override
        public DefaultSqlBodyBuilder addSelectColumn(String tableAlias, String column) {
            querySelect.addSelectColumn(tableAlias, column);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addSelectColumn(String alias, SqlFunction sqlFunction, Object... objects) {
            querySelect.addSelectColumn(alias, sqlFunction, objects);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns) {
            addJoin(tableName, alias, fkColumns, pkColumns, JoinType.INNER_JOIN);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addJoin(String tableName, String alias, String joinTableAlias, String[] fkColumns,
                String[] pkColumns) {
            String[] strArray = new String[fkColumns.length];
            for (int i = 0; i < strArray.length; i++) {
                strArray[i] = joinTableAlias + "." + fkColumns[i];
            }
            addJoin(tableName, alias, strArray, pkColumns, JoinType.INNER_JOIN);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addJoin(String tableName, String alias, String joinTableAlias, String fkColumn,
                String pkColumn) {
            addJoin(tableName, alias, new String[] { joinTableAlias + "." + fkColumn }, new String[] { pkColumn },
                    JoinType.INNER_JOIN);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addJoin(String tableName, String alias, String joinTableAlias, String[] fkColumns,
                String[] pkColumns, JoinType joinType) {
            String[] strArray = new String[fkColumns.length];
            for (int i = 0; i < strArray.length; i++) {
                strArray[i] = joinTableAlias + "." + fkColumns[i];
            }
            addJoin(tableName, alias, strArray, pkColumns, joinType);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns,
                JoinType joinType) {
            getQueryJoinFragment().addJoin(tableName, alias, fkColumns, pkColumns, joinType);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addGroupBy(String tableAlias, String column) {
            getQuerySelect().setGroupByToken(tableAlias, column);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addOrderBy(String tableAlias, String column) {
            getQuerySelect().setOrderByToken(tableAlias, column);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder addHaving(Fragment fragment) {
            getQuerySelect().setHavingToken(fragment);
            return this;
        }

        @Override
        public QuerySelect build() {
            return getQuerySelect();
        }

        @Override
        public DefaultSqlBodyBuilder prependWhereConditions(Fragment fragment) {
            getQuerySelect().prependWhereConditions(fragment);
            return this;
        }

        @Override
        public DefaultSqlBodyBuilder setPage(BasePage page) {
            if (page == null) {
                throw new KylinSqlBuilderException("page is null");
            }
            getQuerySelect().setBasePage(page);
            return this;
        }

    }

}
