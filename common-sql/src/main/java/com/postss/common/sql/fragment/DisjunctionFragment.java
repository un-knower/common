package com.postss.common.sql.fragment;

public class DisjunctionFragment {
    private StringBuilder buffer = new StringBuilder();

    public DisjunctionFragment addCondition(ConditionFragment fragment) {
        addCondition(fragment.toFragmentString());
        return this;
    }

    public DisjunctionFragment addCondition(String fragment) {
        if (buffer.length() > 0) {
            buffer.append(" or ");
        }
        buffer.append('(').append(fragment).append(')');
        return this;
    }

    public String toFragmentString() {
        return buffer.toString();
    }
}
