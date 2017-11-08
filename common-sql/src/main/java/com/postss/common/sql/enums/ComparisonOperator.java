package com.postss.common.sql.enums;

public enum ComparisonOperator {

    EQUAL {
        public ComparisonOperator negated() {
            return NOT_EQUAL;
        }

        public String rendered() {
            return "=";
        }
    },
    NOT_EQUAL {
        public ComparisonOperator negated() {
            return EQUAL;
        }

        public String rendered() {
            return "<>";
        }
    },
    LESS_THAN {
        public ComparisonOperator negated() {
            return GREATER_THAN_OR_EQUAL;
        }

        public String rendered() {
            return "<";
        }
    },
    LESS_THAN_OR_EQUAL {
        public ComparisonOperator negated() {
            return GREATER_THAN;
        }

        public String rendered() {
            return "<=";
        }
    },
    GREATER_THAN {
        public ComparisonOperator negated() {
            return LESS_THAN_OR_EQUAL;
        }

        public String rendered() {
            return ">";
        }
    },
    GREATER_THAN_OR_EQUAL {
        public ComparisonOperator negated() {
            return LESS_THAN;
        }

        public String rendered() {
            return ">=";
        }
    };

    public abstract ComparisonOperator negated();

    public abstract String rendered();

}
