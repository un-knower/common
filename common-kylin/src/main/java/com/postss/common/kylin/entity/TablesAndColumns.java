package com.postss.common.kylin.entity;

import java.util.List;

public class TablesAndColumns {

    private String tableSchem;
    private String tableCat;
    private String tableName;
    private String tableType;

    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public static class Column {
        private Integer nullable;
        private Integer columnSize;
        private String tableSchem;
        private Integer dataType;
        private Integer sqlDatetimeSub;
        private Integer sqlDataType;
        private Integer sourceDataType;
        private String isNullable;
        private Integer decimalDigits;
        private String typeName;//字段类型
        private Integer bufferLength;
        private String columnName;
        private String tableCat;
        private Integer charOctetLength;
        private Integer ordinalPosition;
        private Integer numPrecRadix;
        private String tableName;//表名

        public Column() {
            super();
        }

        public Integer getNullable() {
            return nullable;
        }

        public Integer getColumnSize() {
            return columnSize;
        }

        public String getTableSchem() {
            return tableSchem;
        }

        public Integer getDataType() {
            return dataType;
        }

        public Integer getSqlDatetimeSub() {
            return sqlDatetimeSub;
        }

        public Integer getSqlDataType() {
            return sqlDataType;
        }

        public Integer getSourceDataType() {
            return sourceDataType;
        }

        public String getIsNullable() {
            return isNullable;
        }

        public Integer getDecimalDigits() {
            return decimalDigits;
        }

        public String getTypeName() {
            return typeName;
        }

        public Integer getBufferLength() {
            return bufferLength;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getTableCat() {
            return tableCat;
        }

        public Integer getCharOctetLength() {
            return charOctetLength;
        }

        public Integer getOrdinalPosition() {
            return ordinalPosition;
        }

        public Integer getNumPrecRadix() {
            return numPrecRadix;
        }

        public String getTableName() {
            return tableName;
        }

        public void setNullable(Integer nullable) {
            this.nullable = nullable;
        }

        public void setColumn_SIZE(Integer columnSize) {
            this.columnSize = columnSize;
        }

        public void setTable_SCHEM(String tableSchem) {
            this.tableSchem = tableSchem;
        }

        public void setData_TYPE(Integer dataType) {
            this.dataType = dataType;
        }

        public void setSql_DATETIME_SUB(Integer sqlDatetimeSub) {
            this.sqlDatetimeSub = sqlDatetimeSub;
        }

        public void setSql_DATA_TYPE(Integer sqlDataType) {
            this.sqlDataType = sqlDataType;
        }

        public void setSource_DATA_TYPE(Integer sourceDataType) {
            this.sourceDataType = sourceDataType;
        }

        public void setIs_NULLABLE(String isNullable) {
            this.isNullable = isNullable;
        }

        public void setDecimal_DIGITS(Integer decimalDigits) {
            this.decimalDigits = decimalDigits;
        }

        public void setType_NAME(String typeName) {
            this.typeName = typeName;
        }

        public void setBuffer_LENGTH(Integer bufferLength) {
            this.bufferLength = bufferLength;
        }

        public void setColumn_NAME(String columnName) {
            this.columnName = columnName;
        }

        public void setTable_CAT(String tableCat) {
            this.tableCat = tableCat;
        }

        public void setChar_OCTET_LENGTH(Integer charOctetLength) {
            this.charOctetLength = charOctetLength;
        }

        public void setOrdinal_POSITION(Integer ordinalPosition) {
            this.ordinalPosition = ordinalPosition;
        }

        public void setNum_PREC_RADIX(Integer numPrecRadix) {
            this.numPrecRadix = numPrecRadix;
        }

        public void setTable_NAME(String tableName) {
            this.tableName = tableName;
        }

    }

    public void setTable_SCHEM(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public void setTable_CAT(String tableCat) {
        this.tableCat = tableCat;
    }

    public void setTable_NAME(String tableName) {
        this.tableName = tableName;
    }

    public void setTable_TYPE(String tableType) {
        this.tableType = tableType;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public String getTableCat() {
        return tableCat;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableType() {
        return tableType;
    }

}
