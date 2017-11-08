package com.postss.common.es.entity;

public class EsIndexEntity {

    protected String index; //索引名
    protected String type; //type表名

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EsIndexEntity(String index, String type) {
        super();
        this.index = index;
        this.type = type;
    }

}
