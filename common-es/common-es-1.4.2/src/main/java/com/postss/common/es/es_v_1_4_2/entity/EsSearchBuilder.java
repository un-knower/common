package com.postss.common.es.es_v_1_4_2.entity;

public class EsSearchBuilder {

    public static EsSearch buildEsSearch(Class<? extends EsSearch> clazz) throws Exception {
        return clazz.getConstructor(String.class, String.class).newInstance("kakou_201707", "pass");
    }

}
