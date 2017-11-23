package com.postss.common.sql.fragment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.RowSelection;

import com.postss.common.base.page.BasePage;
import com.postss.common.sql.dialect.SqlWarp;
import com.postss.common.sql.enums.SqlFunction;
import com.postss.common.util.StringUtil;

public class QuerySelect implements Fragment {
    private Dialect dialect;
    private SqlWarp sqlWarp;
    private JoinFragment joins;
    private StringBuilder select = new StringBuilder();
    private StringBuilder where = new StringBuilder();
    private StringBuilder groupBy = new StringBuilder();
    private StringBuilder orderBy = new StringBuilder();
    private StringBuilder having = new StringBuilder();
    private BasePage basePage;
    private String comment;
    private boolean distinct;

    private static final HashSet<String> DONT_SPACE_TOKENS = new HashSet<String>();

    static {
        //dontSpace.add("'");
        DONT_SPACE_TOKENS.add(".");
        DONT_SPACE_TOKENS.add("+");
        DONT_SPACE_TOKENS.add("-");
        DONT_SPACE_TOKENS.add("/");
        DONT_SPACE_TOKENS.add("*");
        DONT_SPACE_TOKENS.add("<");
        DONT_SPACE_TOKENS.add(">");
        DONT_SPACE_TOKENS.add("=");
        DONT_SPACE_TOKENS.add("#");
        DONT_SPACE_TOKENS.add("~");
        DONT_SPACE_TOKENS.add("|");
        DONT_SPACE_TOKENS.add("&");
        DONT_SPACE_TOKENS.add("<=");
        DONT_SPACE_TOKENS.add(">=");
        DONT_SPACE_TOKENS.add("=>");
        DONT_SPACE_TOKENS.add("=<");
        DONT_SPACE_TOKENS.add("!=");
        DONT_SPACE_TOKENS.add("<>");
        DONT_SPACE_TOKENS.add("!#");
        DONT_SPACE_TOKENS.add("!~");
        DONT_SPACE_TOKENS.add("!<");
        DONT_SPACE_TOKENS.add("!>");
        DONT_SPACE_TOKENS.add("("); //for MySQL
        DONT_SPACE_TOKENS.add(")");
    }

    /*public QuerySelect(Dialect dialect) {
        this.dialect = dialect;
        joins = new QueryJoinFragment(dialect, false);
    }*/

    public QuerySelect(Dialect dialect, SqlWarp sqlWarp) {
        this.dialect = dialect;
        this.sqlWarp = sqlWarp;
    }

    public JoinFragment getJoinFragment() {
        return joins;
    }

    public QuerySelect setJoinFragment(JoinFragment joinFragment) {
        joins = joinFragment;
        return this;
    }

    public QuerySelect addSelectFragmentString(String fragment) {
        if (fragment.length() > 0 && fragment.charAt(0) == ',') {
            fragment = fragment.substring(1);
        }
        fragment = fragment.trim();
        if (fragment.length() > 0) {
            if (select.length() > 0) {
                select.append(", ");
            }
            select.append(fragment);
        }
        return this;
    }

    public QuerySelect addSelectColumn(String columnName, String alias) {
        addSelectFragmentString(columnName + '.' + alias);
        return this;
    }

    public QuerySelect addSelectColumn(String alias, SqlFunction sqlFunction, Object... objects) {
        addSelectFragmentString(sqlFunction.getSqlFunctionFragment().toFragmentString(alias, objects));
        return this;
    }

    public QuerySelect setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public QuerySelect setWhereTokens(Iterator<String> tokens) {
        //if ( conjunctiveWhere.length()>0 ) conjunctiveWhere.append(" and ");
        appendTokens(where, tokens);
        return this;
    }

    public QuerySelect prependWhereConditions(String conditions) {
        if (where.length() > 0) {
            where.insert(0, conditions + " and ");
        } else {
            where.append(conditions);
        }
        return this;
    }

    public QuerySelect prependWhereConditions(Fragment fragment) {
        if (where.length() > 0) {
            where.insert(0, fragment.toFragmentString() + " and ");
        } else {
            where.append(fragment.toFragmentString());
        }
        return this;
    }

    public QuerySelect setGroupByTokens(Iterator<String> tokens) {
        //if ( groupBy.length()>0 ) groupBy.append(" and ");
        appendTokens(groupBy, tokens);
        return this;
    }

    public QuerySelect setGroupByToken(String tableAlias, String column) {
        if (groupBy.length() > 0) {
            appendTokens(groupBy, "," + tableAlias + "." + column);
        } else {
            appendTokens(groupBy, tableAlias + "." + column);
        }
        return this;
    }

    public QuerySelect setOrderByTokens(Iterator<String> tokens) {
        //if ( orderBy.length()>0 ) orderBy.append(" and ");
        appendTokens(orderBy, tokens);
        return this;
    }

    public QuerySelect setOrderByToken(String tableAlias, String column) {
        if (orderBy.length() > 0) {
            appendTokens(orderBy, "," + tableAlias + "." + column);
        } else {
            appendTokens(orderBy, tableAlias + "." + column);
        }
        return this;
    }

    public QuerySelect setHavingTokens(Iterator<String> tokens) {
        //if ( having.length()>0 ) having.append(" and ");
        appendTokens(having, tokens);
        return this;
    }

    /**
     * KylinHavingCondition 条件
     * @see com.postss.common.sql.builder.KylinHavingCondition
     * @param fragment KylinHavingCondition 中的条件返回值
     * @return
     */
    public QuerySelect setHavingToken(Fragment fragment) {
        appendTokens(having, fragment.toFragmentString());
        return this;
    }

    public QuerySelect addOrderByToken(String orderByString) {
        if (orderBy.length() > 0) {
            orderBy.append(", ");
        }
        orderBy.append(orderByString);
        return this;
    }

    public String toQueryString() {
        StringBuilder buf = new StringBuilder(50);
        if (comment != null) {
            buf.append("/* ").append(comment).append(" */ ");
        }
        buf.append("select ");
        if (distinct) {
            buf.append("distinct ");
        }
        String from = joins.toFromFragmentString();
        if (from.startsWith(",")) {
            from = from.substring(1);
        } else if (from.startsWith(" inner join")) {
            from = from.substring(11);
        }

        buf.append(select.toString()).append(" from").append(from);

        String outerJoinsAfterWhere = joins.toWhereFragmentString().trim();
        String whereConditions = where.toString().trim();
        boolean hasOuterJoinsAfterWhere = outerJoinsAfterWhere.length() > 0;
        boolean hasWhereConditions = whereConditions.length() > 0;
        if (hasOuterJoinsAfterWhere || hasWhereConditions) {
            buf.append(" where ");
            if (hasOuterJoinsAfterWhere) {
                buf.append(outerJoinsAfterWhere.substring(4));
            }
            if (hasWhereConditions) {
                if (hasOuterJoinsAfterWhere) {
                    buf.append(" and (");
                }
                buf.append(whereConditions);
                if (hasOuterJoinsAfterWhere) {
                    buf.append(")");
                }
            }
        }

        if (groupBy.length() > 0) {
            buf.append(" group by ").append(groupBy.toString());
        }
        if (having.length() > 0) {
            buf.append(" having ").append(having.toString());
        }
        if (orderBy.length() > 0) {
            buf.append(" order by ").append(orderBy.toString());
        }
        if (basePage != null) {
            RowSelection selection = new RowSelection();
            selection.setFetchSize(basePage.getPageSize());
            selection.setFirstRow(basePage.getBeginNumber());
            return dialect.getLimitHandler().processSql(buf.toString(), selection);
        }
        return dialect.transformSelectString(buf.toString());
    }

    public String toRealQueryString() {
        String sql = toQueryString();
        if (sqlWarp.getParamterPattern() != null) {
            List<String> list = StringUtil.getPatternMattcherList(sql, sqlWarp.getParamterPattern(), 1);
            for (String param : list) {
                sql = sql.replaceFirst(sqlWarp.getParamterPattern(), param);
            }
        }
        return sql;
    }

    private static void appendTokens(StringBuilder buf, String token) {
        boolean lastSpaceable = true;
        boolean lastQuoted = false;
        boolean spaceable = !DONT_SPACE_TOKENS.contains(token);
        boolean quoted = token.startsWith("'");
        if (spaceable && lastSpaceable) {
            if (!quoted || !lastQuoted) {
                buf.append(' ');
            }
        }
        lastSpaceable = spaceable;
        buf.append(token);
        lastQuoted = token.endsWith("'");

    }

    private static void appendTokens(StringBuilder buf, Iterator<String> iter) {
        boolean lastSpaceable = true;
        boolean lastQuoted = false;
        while (iter.hasNext()) {
            String token = (String) iter.next();
            boolean spaceable = !DONT_SPACE_TOKENS.contains(token);
            boolean quoted = token.startsWith("'");
            if (spaceable && lastSpaceable) {
                if (!quoted || !lastQuoted) {
                    buf.append(' ');
                }
            }
            lastSpaceable = spaceable;
            buf.append(token);
            lastQuoted = token.endsWith("'");
        }
    }

    public QuerySelect setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public BasePage getBasePage() {
        return basePage;
    }

    public QuerySelect setBasePage(BasePage basePage) {
        this.basePage = basePage;
        return this;
    }

    public QuerySelect copy() {
        QuerySelect copy = new QuerySelect(dialect, sqlWarp);
        copy.joins = this.joins.copy();
        copy.select.append(this.select.toString());
        copy.where.append(this.where.toString());
        copy.groupBy.append(this.groupBy.toString());
        copy.orderBy.append(this.orderBy.toString());
        copy.having.append(this.having.toString());
        copy.comment = this.comment;
        copy.distinct = this.distinct;
        copy.basePage = this.basePage;
        return copy;
    }

    @Override
    public String toFragmentString() {
        return toRealQueryString();
    }

}