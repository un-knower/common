package com.postss.common.es.enums;

public enum IndexField {

    type(new String[] { "string", "long" }), analyzer(new String[] { "standard" }), search_analyzer(
            new String[] { "ik_max_word" }), include_in_all(
                    new String[] {}), boost(new String[] {}), index(new String[] { "not_analyzed" });

    public String[] valArray;

    private IndexField(String[] valArray) {
        this.valArray = valArray;
    }
}