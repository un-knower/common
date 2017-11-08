package com.postss.common.kylin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.postss.common.util.ConvertUtil;

/**
 * kylin 查询sql结果组装类
 * @className ApiSqlResultEntity
 * @author jwSun
 * @date 2017年8月22日 下午7:13:35
 * @version 1.0.0
 */
public class ApiSqlResultEntity {

    private List<ColumnMetas> columnMetas;
    private JSONArray results;
    private String cube;
    private long affectedRowCount;
    private boolean isException;
    private String exceptionMessage;
    private long duration;
    private boolean partial;

    public List<ColumnMetas> getColumnMetas() {
        return columnMetas;
    }

    public void setColumnMetas(List<ColumnMetas> columnMetas) {
        this.columnMetas = columnMetas;
    }

    public JSONArray getResults() {
        return results;
    }

    public void setResults(JSONArray results) {
        this.results = results;
    }

    /**
     * 获得组装后的结果集 key-value形式
     * @return
     */
    public List<Map<String, Object>> getRealResults() {
        List<Object> results = getResults();
        List<ColumnMetas> columnMetas = getColumnMetas();
        List<String> fieldNames = new ArrayList<>();
        columnMetas.forEach((columnMeta) -> {
            fieldNames.add(columnMeta.getLabel());
        });
        return ConvertUtil.parseObjToMap(results, fieldNames.toArray(new String[fieldNames.size()]));
    }

    public String getCube() {
        return cube;
    }

    public void setCube(String cube) {
        this.cube = cube;
    }

    public long getAffectedRowCount() {
        return affectedRowCount;
    }

    public void setAffectedRowCount(long affectedRowCount) {
        this.affectedRowCount = affectedRowCount;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean isException) {
        this.isException = isException;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isPartial() {
        return partial;
    }

    public void setPartial(boolean partial) {
        this.partial = partial;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public class ColumnMetas {
        private Boolean definitelyWritable;
        private Boolean caseSensitive;
        private Long precision;
        private Boolean autoIncrement;
        private Long scale;
        private Boolean signed;
        private Boolean readOnly;
        private String label;
        private String schemaName;
        private Long displaySize;
        private Boolean searchable;
        private String tableName;
        private Boolean writable;
        private Long columnType;
        private Long isNullable;
        private String name;
        private Boolean currency;
        private String columnTypeName;

        public Boolean getDefinitelyWritable() {
            return definitelyWritable;
        }

        public void setDefinitelyWritable(Boolean definitelyWritable) {
            this.definitelyWritable = definitelyWritable;
        }

        public Boolean getCaseSensitive() {
            return caseSensitive;
        }

        public void setCaseSensitive(Boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
        }

        public Long getPrecision() {
            return precision;
        }

        public void setPrecision(Long precision) {
            this.precision = precision;
        }

        public Boolean getAutoIncrement() {
            return autoIncrement;
        }

        public void setAutoIncrement(Boolean autoIncrement) {
            this.autoIncrement = autoIncrement;
        }

        public Long getScale() {
            return scale;
        }

        public void setScale(Long scale) {
            this.scale = scale;
        }

        public Boolean getSigned() {
            return signed;
        }

        public void setSigned(Boolean signed) {
            this.signed = signed;
        }

        public Boolean getReadOnly() {
            return readOnly;
        }

        public void setReadOnly(Boolean readOnly) {
            this.readOnly = readOnly;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getSchemaName() {
            return schemaName;
        }

        public void setSchemaName(String schemaName) {
            this.schemaName = schemaName;
        }

        public Long getDisplaySize() {
            return displaySize;
        }

        public void setDisplaySize(Long displaySize) {
            this.displaySize = displaySize;
        }

        public Boolean getSearchable() {
            return searchable;
        }

        public void setSearchable(Boolean searchable) {
            this.searchable = searchable;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public Boolean getWritable() {
            return writable;
        }

        public void setWritable(Boolean writable) {
            this.writable = writable;
        }

        public Long getColumnType() {
            return columnType;
        }

        public void setColumnType(Long columnType) {
            this.columnType = columnType;
        }

        public Long getIsNullable() {
            return isNullable;
        }

        public void setIsNullable(Long isNullable) {
            this.isNullable = isNullable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getCurrency() {
            return currency;
        }

        public void setCurrency(Boolean currency) {
            this.currency = currency;
        }

        public String getColumnTypeName() {
            return columnTypeName;
        }

        public void setColumnTypeName(String columnTypeName) {
            this.columnTypeName = columnTypeName;
        }

    }

}
