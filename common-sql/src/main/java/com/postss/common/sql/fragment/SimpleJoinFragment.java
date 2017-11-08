package com.postss.common.sql.fragment;

import java.util.LinkedHashSet;
import java.util.Set;

public class SimpleJoinFragment extends ANSIJoinFragment {

    private String tableName;
    private String tableAlias;
    private Set<SimpleJoinFragment> joinSimpleJoinFragment = new LinkedHashSet<>();
    private JoinType joinType;

    public SimpleJoinFragment(String tableName, String tableAlias, JoinType joinType) {
        super();
        this.tableName = tableName;
        this.tableAlias = tableAlias;
        this.joinType = joinType;
    }

    public SimpleJoinFragment(String tableName, String tableAlias) {
        super();
        this.tableName = tableName;
        this.tableAlias = tableAlias;
    }

    /*public void addJoin(SimpleJoinFragment simpleJoinFragment, String[] lhs, String[] rhs) {
        String[] str = new String[lhs.length]
        super.addJoin(simpleJoinFragment.getTableName(), simpleJoinFragment.getTableAlias(), fkColumns, pkColumns, joinType);
    }*/

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public Set<SimpleJoinFragment> getJoinSimpleJoinFragment() {
        return joinSimpleJoinFragment;
    }

    public void setJoinSimpleJoinFragment(Set<SimpleJoinFragment> joinSimpleJoinFragment) {
        this.joinSimpleJoinFragment = joinSimpleJoinFragment;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

}
