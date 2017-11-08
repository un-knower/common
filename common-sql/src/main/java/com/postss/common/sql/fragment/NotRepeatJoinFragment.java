package com.postss.common.sql.fragment;

import java.util.HashSet;
import java.util.Set;

public class NotRepeatJoinFragment extends ANSIJoinFragment {

    private Set<String> joinTable = new HashSet<>();

    public void addJoin(String rhsTableName, String rhsAlias, String[] lhsColumns, String[] rhsColumns,
            JoinType joinType, String on) {
        if (joinTable.contains(rhsTableName)) {
            return;
        } else {
            joinTable.add(rhsTableName);
            super.addJoin(rhsTableName, rhsAlias, lhsColumns, rhsColumns, joinType, on);
        }
    }

}
