package com.postss.common.sql.dialect;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.JDBCException;
import org.hibernate.NullPrecedence;
import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.LimitHelper;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.LockTimeoutException;
import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.hibernate.hql.spi.id.IdTableSupportStandardImpl;
import org.hibernate.hql.spi.id.MultiTableBulkIdStrategy;
import org.hibernate.hql.spi.id.local.AfterUseAction;
import org.hibernate.hql.spi.id.local.LocalTemporaryTableBulkIdStrategy;
import org.hibernate.internal.util.JdbcExceptionHelper;

public class KylinDialect extends Dialect {

    private static final LimitHandler LIMIT_HANDLER = new AbstractLimitHandler() {
        @Override
        public String processSql(String sql, RowSelection selection) {
            final boolean hasOffset = LimitHelper.hasFirstRow(selection);
            return sql + (hasOffset
                    ? " limit ({?" + selection.getFetchSize() + "})" + " offset ({?" + selection.getFirstRow() + "})"
                    : " limit ({?" + selection.getFetchSize() + "})");
        }

        @Override
        public boolean supportsLimit() {
            return true;
        }
    };

    /**
     * Constructs a MySQLDialect
     */
    public KylinDialect() {

    }

    @Override
    public String getAddColumnString() {
        throw new DialectException("no getAddColumnString");
    }

    @Override
    public boolean qualifyIndexName() {
        throw new DialectException("no qualifyIndexName");
    }

    @Override
    public boolean supportsIdentityColumns() {
        throw new DialectException("no supportsIdentityColumns");
    }

    @Override
    public String getIdentitySelectString() {
        throw new DialectException("no getIdentitySelectString");
    }

    @Override
    public String getIdentityColumnString() {
        throw new DialectException("no getIdentityColumnString");
    }

    @Override
    public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
            String[] primaryKey, boolean referencesPrimaryKey) {
        throw new DialectException("no getAddForeignKeyConstraintString");
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getDropForeignKeyString() {
        throw new DialectException("no getDropForeignKeyString");
    }

    @Override
    public LimitHandler getLimitHandler() {
        return LIMIT_HANDLER;
    }

    @Override
    public String getLimitString(String sql, boolean hasOffset) {
        return sql + (hasOffset ? " limit ?" + " offset ?" : " limit ?");
    }

    @Override
    public char closeQuote() {
        return '`';
    }

    @Override
    public char openQuote() {
        return '`';
    }

    @Override
    public boolean canCreateCatalog() {
        return true;
    }

    @Override
    public String[] getCreateCatalogCommand(String catalogName) {
        return new String[] { "create database " + catalogName };
    }

    @Override
    public String[] getDropCatalogCommand(String catalogName) {
        return new String[] { "drop database " + catalogName };
    }

    @Override
    public boolean canCreateSchema() {
        return false;
    }

    @Override
    public String[] getCreateSchemaCommand(String schemaName) {
        throw new UnsupportedOperationException(
                "MySQL does not support dropping creating/dropping schemas in the JDBC sense");
    }

    @Override
    public String[] getDropSchemaCommand(String schemaName) {
        throw new UnsupportedOperationException(
                "MySQL does not support dropping creating/dropping schemas in the JDBC sense");
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public String getSelectGUIDString() {
        return "select uuid()";
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    @Override
    public String getTableComment(String comment) {
        return " comment='" + comment + "'";
    }

    @Override
    public String getColumnComment(String comment) {
        return " comment '" + comment + "'";
    }

    @Override
    public MultiTableBulkIdStrategy getDefaultMultiTableBulkIdStrategy() {
        return new LocalTemporaryTableBulkIdStrategy(new IdTableSupportStandardImpl() {
            @Override
            public String getCreateIdTableCommand() {
                return "create temporary table if not exists";
            }

            @Override
            public String getDropIdTableCommand() {
                return "drop temporary table";
            }
        }, AfterUseAction.DROP, TempTableDdlTransactionHandling.NONE);
    }

    @Override
    public String getCastTypeName(int code) {
        throw new DialectException("no getCastTypeName");
    }

    /**
     * Determine the cast target for {@link Types#INTEGER}, {@link Types#BIGINT} and {@link Types#SMALLINT}
     *
     * @return The proper cast target type.
     */
    protected String smallIntegerCastTarget() {
        return "signed";
    }

    /**
     * Determine the cast target for {@link Types#FLOAT} and {@link Types#REAL} (DOUBLE)
     *
     * @return The proper cast target type.
     */
    protected String floatingPointNumberCastTarget() {
        // MySQL does not allow casting to DOUBLE nor FLOAT, so we have to cast these as DECIMAL.
        // MariaDB does allow casting to DOUBLE, although not FLOAT.
        return fixedPointNumberCastTarget();
    }

    /**
     * Determine the cast target for {@link Types#NUMERIC}
     *
     * @return The proper cast target type.
     */
    protected String fixedPointNumberCastTarget() {
        throw new DialectException("no fixedPointNumberCastTarget");
    }

    @Override
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    @Override
    public String getCurrentTimestampSelectString() {
        return "select now()";
    }

    @Override
    public int registerResultSetOutParameter(CallableStatement statement, int col) throws SQLException {
        return col;
    }

    @Override
    public ResultSet getResultSet(CallableStatement ps) throws SQLException {
        boolean isResultSet = ps.execute();
        while (!isResultSet && ps.getUpdateCount() != -1) {
            isResultSet = ps.getMoreResults();
        }
        return ps.getResultSet();
    }

    @Override
    public boolean supportsRowValueConstructorSyntax() {
        return true;
    }

    @Override
    public String renderOrderByElement(String expression, String collation, String order, NullPrecedence nulls) {
        final StringBuilder orderByElement = new StringBuilder();
        if (nulls != NullPrecedence.NONE) {
            // Workaround for NULLS FIRST / LAST support.
            orderByElement.append("case when ").append(expression).append(" is null then ");
            if (nulls == NullPrecedence.FIRST) {
                orderByElement.append("0 else 1");
            } else {
                orderByElement.append("1 else 0");
            }
            orderByElement.append(" end, ");
        }
        // Nulls precedence has already been handled so passing NONE value.
        orderByElement.append(super.renderOrderByElement(expression, collation, order, NullPrecedence.NONE));
        return orderByElement.toString();
    }

    // locking support

    @Override
    public String getForUpdateString() {
        return " for update";
    }

    @Override
    public String getWriteLockString(int timeout) {
        return " for update";
    }

    @Override
    public String getReadLockString(int timeout) {
        return " lock in share mode";
    }

    // Overridden informational metadata ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public boolean supportsEmptyInList() {
        return false;
    }

    @Override
    public boolean areStringComparisonsCaseInsensitive() {
        return true;
    }

    @Override
    public boolean supportsLobValueChangePropogation() {
        // note: at least my local MySQL 5.1 install shows this not working...
        return false;
    }

    @Override
    public boolean supportsSubqueryOnMutatingTable() {
        return false;
    }

    @Override
    public boolean supportsLockTimeouts() {
        // yes, we do handle "lock timeout" conditions in the exception conversion delegate,
        // but that's a hardcoded lock timeout period across the whole entire MySQL database.
        // MySQL does not support specifying lock timeouts as part of the SQL statement, which is really
        // what this meta method is asking.
        return false;
    }

    @Override
    public SQLExceptionConversionDelegate buildSQLExceptionConversionDelegate() {
        return new SQLExceptionConversionDelegate() {
            @Override
            public JDBCException convert(SQLException sqlException, String message, String sql) {
                final String sqlState = JdbcExceptionHelper.extractSqlState(sqlException);

                if ("41000".equals(sqlState)) {
                    return new LockTimeoutException(message, sqlException, sql);
                }

                if ("40001".equals(sqlState)) {
                    return new LockAcquisitionException(message, sqlException, sql);
                }

                return null;
            }
        };
    }

    @Override
    public String getNotExpression(String expression) {
        return "not (" + expression + ")";
    }
}