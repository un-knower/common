package com.postss.common.sql.fragment;

import org.hibernate.AssertionFailure;

public class CacheJoinFragment extends ANSIJoinFragment {

    public void addJoin(String rhsTableName, String rhsAlias, String[] lhsColumns, String[] rhsColumns,
            JoinType joinType, String on) {
        if (joinType == JoinType.FULL_JOIN) {
            throw new AssertionFailure("Cache does not support full outer joins");
        }
        super.addJoin(rhsTableName, rhsAlias, lhsColumns, rhsColumns, joinType, on);
    }

}