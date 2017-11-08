package com.postss.common.sql.fragment;

import org.hibernate.HibernateException;

import com.postss.common.system.exception.BusinessException;

public enum JoinType {
    NONE(-666), INNER_JOIN(0), LEFT_OUTER_JOIN(1), RIGHT_OUTER_JOIN(2), FULL_JOIN(4);
    private int joinTypeValue;

    JoinType(int joinTypeValue) {
        this.joinTypeValue = joinTypeValue;
    }

    public int getJoinTypeValue() {
        return joinTypeValue;
    }

    public static JoinType parse(int joinType) {
        if (joinType < 0) {
            return NONE;
        }
        switch (joinType) {
        case 0:
            return INNER_JOIN;
        case 1:
            return LEFT_OUTER_JOIN;
        case 2:
            return RIGHT_OUTER_JOIN;
        case 4:
            return FULL_JOIN;
        default:
            throw new HibernateException("unknown join type: " + joinType);
        }
    }

    public static JoinType parse(String type) {
        JoinType jointype;
        switch (type) {
        case "inner":
            jointype = INNER_JOIN;
            break;
        case "left":
            jointype = LEFT_OUTER_JOIN;
            break;
        case "right":
            jointype = RIGHT_OUTER_JOIN;
            break;
        case "full":
            jointype = FULL_JOIN;
            break;
        default:
            throw new BusinessException("undefined join type");
        }
        return jointype;
    }
}