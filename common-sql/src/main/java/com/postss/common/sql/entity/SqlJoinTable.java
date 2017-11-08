package com.postss.common.sql.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.postss.common.sql.enums.TableJoin;
import com.postss.common.util.CollectionUtil;
import com.postss.common.util.StringUtil;

public class SqlJoinTable implements SqlBuild {

    private String table;
    private TableJoin tableJoin;
    private List<SqlJoinCondition> joinConditionList;

    public SqlJoinTable() {
        super();
        joinConditionList = new ArrayList<>();
    }

    public String getTable() {
        return table;
    }

    public SqlJoinTable setTable(String table) {
        this.table = table;
        return this;
    }

    public TableJoin getTableJoin() {
        return tableJoin;
    }

    public SqlJoinTable setTableJoin(TableJoin tableJoin) {
        this.tableJoin = tableJoin;
        return this;
    }

    public List<SqlJoinCondition> getJoinConditionList() {
        return joinConditionList;
    }

    public SqlJoinTable setJoinCondition(SqlJoinCondition joinCondition) {
        this.joinConditionList.add(joinCondition);
        return this;
    }

    @Override
    public String getSql() {
        Assert.notNull(table, "table 不能为空");
        Assert.notNull(tableJoin, "tableJoin 不能为空");
        Assert.isTrue(!CollectionUtil.isEmpty(joinConditionList), "joinConditionList 不能为空");
        String sql = StringUtil.format(getTableJoin().getSql(), table);
        for (SqlJoinCondition sqlJoinCondition : joinConditionList) {
            sql = StringUtil.format(sql, sqlJoinCondition.getSql());
        }
        return sql;
    }

}
