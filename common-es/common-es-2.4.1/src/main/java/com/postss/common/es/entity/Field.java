package com.postss.common.es.entity;

/**
 * ES属性实体类
 * <pre>
 *  name:indexFieldName
 *  value:indexFieldType
 * </pre>
 * @author jwSun
 * @date 2017年5月2日 上午11:26:58
 */
public class Field {

    private String name;
    private String value;

    public Field(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
