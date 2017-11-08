package com.postss.common.kylin.enums;

public enum MeasuresPType {

    CONSTANT("constant"), COLUMN("column");

    private String value;

    private MeasuresPType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
