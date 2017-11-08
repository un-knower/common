package com.postss.common.sql.fragment;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.dialect.Dialect;

public class QueryJoinFragment extends JoinFragment {

    private StringBuilder afterFrom = new StringBuilder();
    private StringBuilder afterWhere = new StringBuilder();
    private Dialect dialect;
    private boolean useThetaStyleInnerJoins;

    private Set<String> joinTable = new HashSet<>();

    public Set<String> getJoinTables() {
        return joinTable;
    }

    public QueryJoinFragment(Dialect dialect, boolean useThetaStyleInnerJoins) {
        this.dialect = dialect;
        this.useThetaStyleInnerJoins = useThetaStyleInnerJoins;
    }

    public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType) {
        addJoin(tableName, alias, alias, fkColumns, pkColumns, joinType, null);
    }

    public void addJoin(String tableName, String alias, String joinTable, String[] fkColumns, String[] pkColumns,
            JoinType joinType) {
        String[] fk = new String[fkColumns.length];
        for (int i = 0; i < fk.length; i++) {
            fk[i] = joinTable + "." + fkColumns[i];
        }
        addJoin(tableName, alias, alias, fk, pkColumns, joinType, null);
    }

    public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType,
            String on) {
        addJoin(tableName, alias, alias, fkColumns, pkColumns, joinType, on);
    }

    private void addJoin(String tableName, String alias, String concreteAlias, String[] fkColumns, String[] pkColumns,
            JoinType joinType, String on) {
        if (joinTable.contains(tableName)) {
            return;
        } else {
            joinTable.add(tableName);
            if (!useThetaStyleInnerJoins || joinType != JoinType.INNER_JOIN) {
                JoinFragment jf = new NotRepeatJoinFragment();
                jf.addJoin(tableName, alias, fkColumns, pkColumns, joinType, on);
                addFragment(jf);
            } else {
                addCrossJoin(tableName, alias);
                addCondition(concreteAlias, fkColumns, pkColumns);
                addCondition(on);
            }
        }
    }

    public String toFromFragmentString() {
        return afterFrom.toString();
    }

    public String toWhereFragmentString() {
        return afterWhere.toString();
    }

    public void addJoins(String fromFragment, String whereFragment) {
        afterFrom.append(fromFragment);
        afterWhere.append(whereFragment);
    }

    public JoinFragment copy() {
        QueryJoinFragment copy = new QueryJoinFragment(dialect, useThetaStyleInnerJoins);
        copy.afterFrom = new StringBuilder(afterFrom.toString());
        copy.afterWhere = new StringBuilder(afterWhere.toString());
        return copy;
    }

    public void addCondition(String alias, String[] columns, String condition) {
        for (int i = 0; i < columns.length; i++) {
            afterWhere.append(" and ").append(alias).append('.').append(columns[i]).append(condition);
        }
    }

    public void addCrossJoin(String tableName, String alias) {
        afterFrom.append(", ").append(tableName).append(' ').append(alias);
    }

    public void addCondition(String alias, String[] fkColumns, String[] pkColumns) {
        for (int j = 0; j < fkColumns.length; j++) {
            afterWhere.append(" and ").append(fkColumns[j]).append('=').append(alias).append('.').append(pkColumns[j]);
        }
    }

    /**
     * Add the condition string to the join fragment.
     *
     * @param condition
     * @return true if the condition was added, false if it was already in the fragment.
     */
    public boolean addCondition(String condition) {
        // if the condition is not already there...
        if (afterFrom.toString().indexOf(condition.trim()) < 0 && afterWhere.toString().indexOf(condition.trim()) < 0) {
            if (!condition.startsWith(" and ")) {
                afterWhere.append(" and ");
            }
            afterWhere.append(condition);
            return true;
        } else {
            return false;
        }
    }

    public void addFromFragmentString(String fromFragmentString) {
        afterFrom.append(fromFragmentString);
    }

    public void clearWherePart() {
        afterWhere.setLength(0);
    }
}