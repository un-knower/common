package com.postss.common.es.es_v_1_4_2.enums;

public enum IndexField {

    type(new String[] { "string", "long" }), analyzer(new String[] { "standard" }), search_analyzer(
            new String[] { "ik_max_word" }), include_in_all(new String[] {}), boost(new String[] {});

    public String[] valArray;

    private IndexField(String[] valArray) {
        this.valArray = valArray;
    }
}