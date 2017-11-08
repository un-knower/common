package com.postss.common.kylin.search;

import com.postss.common.kylin.api.KylinApi;
import com.postss.common.kylin.api.search.KylinApiSearch;
import com.postss.common.util.JSONObjectUtil;

public class ApiSqlSearch extends KylinApiSearch implements KylinBodySearch {

    private String sql;
    private String project;
    private Integer offset;
    private Integer limit;
    private Boolean acceptPartial = true;

    public ApiSqlSearch(String project, String sql) {
        super(KylinApi.SQL);
        this.sql = sql;
        this.project = project;
    }

    public String getSql() {
        return sql + (getLimit() == null ? "" : " limit " + getLimit())
                + (getOffset() == null ? "" : " offset " + getOffset());
    }

    public ApiSqlSearch setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public ApiSqlSearch setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public ApiSqlSearch setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Boolean getAcceptPartial() {
        return acceptPartial;
    }

    public ApiSqlSearch setAcceptPartial(Boolean acceptPartial) {
        this.acceptPartial = acceptPartial;
        return this;
    }

    public String getProject() {
        return project;
    }

    public ApiSqlSearch setProject(String project) {
        this.project = project;
        return this;
    }

    @Override
    public String searchJson() {
        return JSONObjectUtil.toJSONString(this, "method", "url", "offset", "limit");
    }
}
