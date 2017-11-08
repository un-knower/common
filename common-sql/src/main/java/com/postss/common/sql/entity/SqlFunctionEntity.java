package com.postss.common.sql.entity;

import com.postss.common.sql.enums.SqlFunction;

public class SqlFunctionEntity implements SqlBuild {

    private SqlFunction sqlFunction;
    private Object[] values;

    public SqlFunction getSqlFunction() {
        return sqlFunction;
    }

    public SqlFunctionEntity(SqlFunction sqlFunction) {
        super();
        this.sqlFunction = sqlFunction;
    }

    public SqlFunctionEntity setSqlFunction(com.postss.common.sql.enums.SqlFunction sqlFunction) {
        this.sqlFunction = sqlFunction;
        return this;
    }

    public Object[] getValues() {
        return values;
    }

    public SqlFunctionEntity setValues(Object... values) {
        this.values = values;
        return this;
    }

    public String getSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(sqlFunction + "(");
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]);
                if (i != values.length - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
