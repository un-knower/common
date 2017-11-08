package com.postss.common.kylin.entity;

import java.util.List;

public class ProjectsReadable {

    private String owner;
    private long create_time_utc;
    private String description;
    private String uuid;
    private String version;
    private String name;
    private long last_modified;
    private List<String> models;
    private List<String> tables;
    private List<String> ext_filters;
    private List<Realizations> realizations;

    public class Realizations {
        private String type;
        private String realization;

        public Realizations() {
            super();
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRealization() {
            return realization;
        }

        public void setRealization(String realization) {
            this.realization = realization;
        }

        @Override
        public String toString() {
            return "Realizations [type=" + type + ", realization=" + realization + "]";
        }

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getCreate_time_utc() {
        return create_time_utc;
    }

    public void setCreate_time_utc(long create_time_utc) {
        this.create_time_utc = create_time_utc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(long last_modified) {
        this.last_modified = last_modified;
    }

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public List<String> getExt_filters() {
        return ext_filters;
    }

    public void setExt_filters(List<String> ext_filters) {
        this.ext_filters = ext_filters;
    }

    public List<Realizations> getRealizations() {
        return realizations;
    }

    public void setRealizations(List<Realizations> realizations) {
        this.realizations = realizations;
    }

    @Override
    public String toString() {
        return "ProjectsReadable [owner=" + owner + ", create_time_utc=" + create_time_utc + ", description="
                + description + ", uuid=" + uuid + ", version=" + version + ", name=" + name + ", last_modified="
                + last_modified + ", models=" + models + ", tables=" + tables + ", ext_filters=" + ext_filters
                + ", realizations=" + realizations + "]";
    }

}
