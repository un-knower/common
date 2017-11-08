package com.postss.common.kylin.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 维度
 * @className Dimension
 * @author jwSun
 * @date 2017年7月28日 下午3:15:27
 * @version 1.0.0
 */
public class Dimension implements java.io.Serializable {

    private static final long serialVersionUID = 7102189246253099176L;
    private String id;
    private String name;//表名
    private String table;//维度表名
    private List<String> column;
    private List<String> derived;
    private Boolean hierarchy;//是否分层
    /**column 与 derived 总和**/
    private Set<Object> columns = new HashSet<Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getColumn() {
        return column;
    }

    public void setColumn(List<String> column) {
        this.column = column;
        this.columns.addAll(column);
    }

    public List<String> getDerived() {
        return derived;
    }

    public void setDerived(List<String> derived) {
        this.derived = derived;
        this.columns.addAll(derived);
    }

    public Boolean getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Boolean hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Set<Object> getColumns() {
        return columns;
    }

}
