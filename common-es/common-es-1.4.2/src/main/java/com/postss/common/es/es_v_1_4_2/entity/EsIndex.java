package com.postss.common.es.es_v_1_4_2.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.postss.common.es.es_v_1_4_2.enums.IndexField;

/**
 * ES索引实体类
 * @author jwSun
 * @date 2017年4月17日 下午4:53:25
 */
public class EsIndex extends BaseEsIndex {
    private Integer number_of_shards; //分片数  
    private Integer number_of_replicas; //备份数  
    private Map<String, Set<Field>> fields = new HashMap<>();//字段

    public EsIndex(String index, String type, Integer number_of_shards, Integer number_of_replicas) {
        super(index, type);
        this.number_of_shards = number_of_shards;
        this.number_of_replicas = number_of_replicas;
    }

    public EsIndex putField(String objName, IndexField indexField, String indexFieldType) {
        Set<String> set = new HashSet<String>();
        Collections.addAll(set, indexField.valArray);
        if (!set.contains(indexFieldType)) {
            throw new IllegalArgumentException("indexFieldType not in indexField");
        }
        Field field = new Field(indexField.toString(), indexFieldType);
        if (fields.containsKey(objName)) {
            fields.get(objName).add(field);
        } else {
            Set<Field> fieldSet = new HashSet<Field>();
            fieldSet.add(field);
            fields.put(objName, fieldSet);
        }
        return this;
    }

    public Map<String, Set<Field>> getFields() {
        return fields;
    }

    public void setFields(Map<String, Set<Field>> fields) {
        this.fields = fields;
    }

    public XContentBuilder getProperties() {
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder().startObject().startObject("properties");
            for (Entry<String, Set<Field>> entry : getFields().entrySet()) {
                mapping.startObject(entry.getKey());
                for (Field field : entry.getValue()) {
                    mapping.field(field.getName(), field.getValue());
                }
                mapping.endObject();
            }
            mapping.endObject().endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping;
    }

    public Integer getNumber_of_shards() {
        return number_of_shards;
    }

    public void setNumber_of_shards(Integer number_of_shards) {
        this.number_of_shards = number_of_shards;
    }

    public Integer getNumber_of_replicas() {
        return number_of_replicas;
    }

    public void setNumber_of_replicas(Integer number_of_replicas) {
        this.number_of_replicas = number_of_replicas;
    }
}
