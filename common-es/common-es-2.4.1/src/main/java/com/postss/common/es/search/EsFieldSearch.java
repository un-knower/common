package com.postss.common.es.search;

import java.util.HashSet;
import java.util.Set;

import com.postss.common.util.ConvertUtil;

/**
 * ES查询field实体类,查询结果只会显示指定的field
 * @author jwSun
 * @date 2017年5月2日 上午9:48:54
 */
public class EsFieldSearch extends EsSearch {

    /**
     * 取属性
     */
    private Set<String> fields = new HashSet<>();

    public EsFieldSearch(String index, String type) {
        super(index, type);
    }

    public String[] getFields() {
        return (String[]) ConvertUtil.parseArraytoClassArray(fields.toArray(), String.class);
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    public EsFieldSearch putField(String field) {
        this.fields.add(field);
        return this;
    }

}
