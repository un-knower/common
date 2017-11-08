package com.postss.common.sql.entity;

import org.springframework.util.Assert;

import com.postss.common.sql.enums.ComparisonOperator;
import com.postss.common.util.StringUtil;

/**
 * join方式条件
 * @className SqlCondition
 * @author jwSun
 * @date 2017年8月9日 下午3:34:20
 * @version 1.0.0
 */
public class SqlJoinCondition implements SqlBuild {

    /**本表字段**/
    private String column;
    /**本表名**/
    private String table;
    /**对应表**/
    private String referencedTable;
    /**对应表字段**/
    private String referencedColumnName;
    /**比较方式**/
    private ComparisonOperator comparisonOperator;

    public SqlJoinCondition setReferenced(String referencedTable, String referencedColumnName) {
        setReferencedTable(referencedTable);
        setReferencedColumnName(referencedColumnName);
        return this;
    }

    public SqlJoinCondition setJoin(String table, String column) {
        setColumn(column);
        setTable(table);
        return this;
    }

    public String getColumn() {
        return column;
    }

    public SqlJoinCondition setColumn(String column) {
        this.column = column;
        return this;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public SqlJoinCondition setReferencedTable(String referencedTable) {
        this.referencedTable = referencedTable;
        return this;
    }

    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    public SqlJoinCondition setReferencedColumnName(String referencedColumnName) {
        this.referencedColumnName = referencedColumnName;
        return this;
    }

    public String getTable() {
        return table;
    }

    public SqlJoinCondition setTable(String table) {
        this.table = table;
        return this;
    }

    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public SqlJoinCondition setComparisonOperator(ComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
        return this;
    }

    @Override
    public String getSql() {
        Assert.notNull(column, "column 不能为空");
        Assert.notNull(table, "table 不能为空");
        Assert.notNull(referencedTable, "referencedTable 不能为空");
        Assert.notNull(referencedColumnName, "referencedColumnName 不能为空");
        Assert.notNull(comparisonOperator, "comparisonOperator 不能为空");
        return StringUtil.format("on", table + "." + column, comparisonOperator.rendered(),
                referencedTable + "." + referencedColumnName);
    }

}
