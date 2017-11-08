package com.postss.common.sql.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.sql.Template;

import com.postss.common.sql.dialect.DefaultSqlWarp;
import com.postss.common.sql.dialect.SqlWarp;

public class InFragment implements Fragment {

    public static final String NULL = "null";
    public static final String NOT_NULL = "not null";
    public SqlWarp sqlWarp;

    private String columnName;
    private List<Object> values = new ArrayList<Object>();

    public InFragment() {
        super();
        this.sqlWarp = new DefaultSqlWarp();
    }

    public InFragment(SqlWarp sqlWarp) {
        super();
        this.sqlWarp = sqlWarp;
    }

    /**
     * @param value an SQL literal, NULL, or NOT_NULL
     *
     * @return {@code this}, for method chaining
     */
    public InFragment addValue(Object value) {
        values.add(sqlWarp.warp(value));
        return this;
    }

    public InFragment addValues(Object[] values) {
        Collections.addAll(this.values, sqlWarp.warp(values));
        return this;
    }

    public InFragment setColumn(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public InFragment setColumn(String alias, String columnName) {
        this.columnName = StringHelper.qualify(alias, columnName);
        return setColumn(this.columnName);
    }

    public InFragment setFormula(String alias, String formulaTemplate) {
        this.columnName = StringHelper.replace(formulaTemplate, Template.TEMPLATE, alias);
        return setColumn(this.columnName);
    }

    public String toFragmentString() {
        if (values.size() == 0) {
            return "1=2";
        }

        StringBuilder buf = new StringBuilder(values.size() * 5);

        //by jwsun 2017-09-02 本来一个会将in改为= 由于kylin中日期不能用= 所以统一改为使用in
        /*if (values.size() == 1) {
            Object value = values.get(0);
            buf.append(columnName);
        
            if (NULL.equals(value)) {
                buf.append(" is null");
            } else {
                if (NOT_NULL.equals(value)) {
                    buf.append(" is not null");
                } else {
                    buf.append('=').append(value);
                }
            }
            return buf.toString();
        }*/

        boolean allowNull = false;

        for (Object value : values) {
            if (NULL.equals(value)) {
                allowNull = true;
            } else {
                if (NOT_NULL.equals(value)) {
                    throw new IllegalArgumentException("not null makes no sense for in expression");
                }
            }
        }

        if (allowNull) {
            buf.append('(').append(columnName).append(" is null or ").append(columnName).append(" in (");
        } else {
            buf.append(columnName).append(" in (");
        }

        for (Object value : values) {
            if (!NULL.equals(value)) {
                buf.append(value);
                buf.append(", ");
            }
        }

        buf.setLength(buf.length() - 2);

        if (allowNull) {
            buf.append("))");
        } else {
            buf.append(')');
        }

        return buf.toString();

    }
}