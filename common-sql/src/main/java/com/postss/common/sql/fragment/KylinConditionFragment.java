package com.postss.common.sql.fragment;

import java.util.Arrays;
import java.util.Date;

import com.postss.common.util.DateUtil;

public class KylinConditionFragment implements Fragment {
    private String tableAlias;
    private Object[] lho;
    private Object[] rho;
    private String op = "=";

    /**
     * Sets the op.
     * @param op The op to set
     */
    public KylinConditionFragment setOp(String op) {
        this.op = op;
        return this;
    }

    /**
     * Sets the tableAlias.
     * @param tableAlias The tableAlias to set
     */
    public KylinConditionFragment setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
        return this;
    }

    public KylinConditionFragment setCondition(Object[] lho, Object[] rho) {
        this.lho = lho;
        this.rho = rho;
        return this;
    }

    public KylinConditionFragment setCondition(Object[] lho, Object rho) {
        this.lho = lho;
        Object[] result = new Object[lho.length];
        Arrays.fill(result, lho);
        this.rho = result;
        return this;
    }

    public String warp(Object val) {
        if (val instanceof String) {
            return "'" + (String) val + "'";
        } else if (val instanceof Date) {
            return "to_date(" + DateUtil.format((Date) val) + ",yyyy-mm-dd hh24:mi:ss)";
        } else {
            return String.valueOf(val);
        }
    }

    public String toFragmentString() {
        StringBuilder buf = new StringBuilder(lho.length * 10);
        for (int i = 0; i < lho.length; i++) {
            String lhsNow = warp(lho[i]);
            String rhsNow = warp(rho[i]);
            buf.append(tableAlias).append('.').append(lhsNow).append(op).append(rhsNow);
            if (i < lho.length - 1) {
                buf.append(" and ");
            }
        }
        return buf.toString();
    }

}