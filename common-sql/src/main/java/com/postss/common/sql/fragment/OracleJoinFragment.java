package com.postss.common.sql.fragment;

import java.util.HashSet;
import java.util.Set;

public class OracleJoinFragment extends JoinFragment {

    private StringBuilder afterFrom = new StringBuilder();
    private StringBuilder afterWhere = new StringBuilder();

    public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType) {
        addCrossJoin(tableName, alias);

        for (int j = 0; j < fkColumns.length; j++) {
            setHasThetaJoins(true);
            afterWhere.append(" and ").append(fkColumns[j]);
            if (joinType == JoinType.RIGHT_OUTER_JOIN || joinType == JoinType.FULL_JOIN) {
                afterWhere.append("(+)");
            }
            afterWhere.append('=').append(alias).append('.').append(pkColumns[j]);
            if (joinType == JoinType.LEFT_OUTER_JOIN || joinType == JoinType.FULL_JOIN) {
                afterWhere.append("(+)");
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
        OracleJoinFragment copy = new OracleJoinFragment();
        copy.afterFrom = new StringBuilder(afterFrom.toString());
        copy.afterWhere = new StringBuilder(afterWhere.toString());
        return copy;
    }

    public void addCondition(String alias, String[] columns, String condition) {
        for (String column : columns) {
            afterWhere.append(" and ").append(alias).append('.').append(column).append(condition);
        }
    }

    public void addCrossJoin(String tableName, String alias) {
        afterFrom.append(", ").append(tableName).append(' ').append(alias);
    }

    public void addCondition(String alias, String[] fkColumns, String[] pkColumns) {
        throw new UnsupportedOperationException();
    }

    public boolean addCondition(String condition) {
        return addCondition(afterWhere, condition);
    }

    public void addFromFragmentString(String fromFragmentString) {
        afterFrom.append(fromFragmentString);
    }

    public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType,
            String on) {
        //arbitrary on clause ignored!!
        addJoin(tableName, alias, fkColumns, pkColumns, joinType);
        if (joinType == JoinType.INNER_JOIN) {
            addCondition(on);
        } else if (joinType == JoinType.LEFT_OUTER_JOIN) {
            addLeftOuterJoinCondition(on);
        } else {
            throw new UnsupportedOperationException(
                    "join type not supported by OracleJoinFragment (use Oracle9iDialect/Oracle10gDialect)");
        }
    }

    /**
     * This method is a bit of a hack, and assumes
     * that the column on the "right" side of the
     * join appears on the "left" side of the
     * operator, which is extremely wierd if this
     * was a normal join condition, but is natural
     * for a filter.
     */
    private void addLeftOuterJoinCondition(String on) {
        StringBuilder buf = new StringBuilder(on);
        for (int i = 0; i < buf.length(); i++) {
            char character = buf.charAt(i);
            final boolean isInsertPoint = OPERATORS.contains(Character.valueOf(character))
                    || (character == ' ' && buf.length() > i + 3 && "is ".equals(buf.substring(i + 1, i + 4)));
            if (isInsertPoint) {
                buf.insert(i, "(+)");
                i += 3;
            }
        }
        addCondition(buf.toString());
    }

    private static final Set OPERATORS = new HashSet();

    static {
        OPERATORS.add(Character.valueOf('='));
        OPERATORS.add(Character.valueOf('<'));
        OPERATORS.add(Character.valueOf('>'));
    }
}