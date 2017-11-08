package com.postss.common.sql.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.postss.common.sql.entity.SqlFunctionEntity;
import com.postss.common.sql.entity.SqlJoinTable;
import com.postss.common.sql.enums.SqlFunction;

public class SimpleSqlBuilder {

    public static DefaultSqlBuilder create() {
        return new DefaultSqlBuilderImpl();
    }

    public interface SqlBuilder<B> {

        String build();

        B setColumn(String tableName, String columnName);

        B setFunction(SqlFunction function, Object... values);

        B setFactTable(String factTableName);

        B setJoinTable(SqlJoinTable sqlJoinTable);

    }

    public interface DefaultSqlBuilder extends SqlBuilder<DefaultSqlBuilder> {

    }

    private static class DefaultSqlBuilderImpl implements DefaultSqlBuilder {

        public DefaultSqlBuilderImpl() {

        }

        private String factTable;
        /**tableName:columnSet**/
        private Map<String, Set<String>> columnMap = new HashMap<>();
        private Set<SqlFunctionEntity> function = new HashSet<>();
        private Set<SqlJoinTable> joinTable = new HashSet<>();

        @Override
        public String build() {
            StringBuilder sb = new StringBuilder();
            sb.append("select ");

            Iterator<Entry<String, Set<String>>> columnEntryInterator = columnMap.entrySet().iterator();
            while (columnEntryInterator.hasNext()) {
                Entry<String, Set<String>> columnEntry = columnEntryInterator.next();
                Iterator<String> columns = columnEntry.getValue().iterator();
                while (columns.hasNext()) {
                    String column = columns.next();
                    sb.append(" " + columnEntry.getKey() + "." + column);
                    if (columns.hasNext()) {
                        sb.append(",");
                    }
                }
                if (columnEntryInterator.hasNext()) {
                    sb.append(",");
                }
            }

            if (function.size() > 0) {
                sb.append(",");
            }
            Iterator<SqlFunctionEntity> functionIterator = function.iterator();
            while (functionIterator.hasNext()) {
                SqlFunctionEntity sqlFunctionEntity = functionIterator.next();
                sb.append(sqlFunctionEntity.getSql());
                if (functionIterator.hasNext()) {
                    sb.append(",");
                }
            }
            sb.append(" from " + factTable);
            Iterator<SqlJoinTable> joinTableIterator = joinTable.iterator();
            while (joinTableIterator.hasNext()) {
                SqlJoinTable sqlJoinTable = joinTableIterator.next();
                sb.append(sqlJoinTable.getSql());
            }
            return sb.toString();
        }

        @Override
        public DefaultSqlBuilderImpl setColumn(String tableName, String columnName) {
            if (this.columnMap.get(tableName) == null) {
                Set<String> set = new HashSet<String>();
                set.add(columnName);
                this.columnMap.put(tableName, set);
            } else {
                this.columnMap.get(tableName).add(columnName);
            }
            return this;
        }

        @Override
        public DefaultSqlBuilderImpl setFactTable(String factTableName) {
            this.factTable = factTableName;
            return this;
        }

        @Override
        public DefaultSqlBuilderImpl setJoinTable(SqlJoinTable sqlJoinTable) {
            this.joinTable.add(sqlJoinTable);
            return this;
        }

        @Override
        public DefaultSqlBuilderImpl setFunction(SqlFunction function, Object... values) {
            this.function.add(new SqlFunctionEntity(function).setValues(values));
            return this;
        }

    }

}