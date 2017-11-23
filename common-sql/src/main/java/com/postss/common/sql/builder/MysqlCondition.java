package com.postss.common.sql.builder;

import org.hibernate.sql.ConditionFragment;
import org.hibernate.sql.InFragment;

public class MysqlCondition {

    public static InFragment in() {
        return new InFragment();
    }

    public static InFragment in(String tableAlias, String column, Object... values) {
        return in().setColumn(tableAlias + "." + column).addValues(values);
    }

    public static ConditionFragment equals() {
        return new ConditionFragment().setOp(" = ");
    }

    public static ConditionFragment equals(String tableAlias, String column, String value) {
        return equals().setTableAlias(tableAlias).setCondition(new String[] { column }, new String[] { value });
    }

    public static ConditionFragment high() {
        return new ConditionFragment().setOp(" > ");
    }

    public static ConditionFragment high(String tableAlias, String column, String value) {
        return high().setTableAlias(tableAlias).setCondition(new String[] { column }, new String[] { value });
    }

    public static ConditionFragment lower() {
        return new ConditionFragment().setOp(" < ");
    }

    public static ConditionFragment lower(String tableAlias, String column, String value) {
        return lower().setTableAlias(tableAlias).setCondition(new String[] { column }, new String[] { value });
    }

    public static ConditionFragment between() {
        return new ConditionFragment().setOp(" between ");
    }

    public static ConditionFragment between(String tableAlias, String column, String lValue, String rValue) {
        return between().setTableAlias(tableAlias).setCondition(new String[] { column },
                new String[] { lValue + " and " + rValue });
    }

    public static ConditionFragment like() {
        return new ConditionFragment().setOp(" like ");
    }

    public static ConditionFragment like(String tableAlias, String column, String value) {
        return like().setTableAlias(tableAlias).setCondition(new String[] { column }, new String[] { value });
    }

    public static String toFragmentString(ConditionFragment... fragments) {
        if (fragments == null || fragments.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fragments.length; i++) {
                sb.append(fragments[i].toFragmentString());
                if (i != fragments.length - 1) {
                    sb.append(" and ");
                }
            }
            return sb.toString();
        }
    }

}
