package com.postss.common.sql.enums;

public enum TableJoin {

    INNER_JOIN(" INNER JOIN "), LEFT_JOIN(" LEFT JOIN "), RIGHT_JOIN(" RIGHT JOIN ");

    private String sql;

    private TableJoin(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return this.sql;
    }

}
