package com.postss.common.es.es_v_1_4_2.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ES存储数据实体类
 * @Param putMaps:key:属性名,val:属性值
 * @author jwSun
 * @date 2017年5月2日 上午11:02:10
 */
public class EsPut extends BaseEsIndex {

    private List<Map<String, Object>> putMaps = new ArrayList<>();

    public EsPut(String index, String type) {
        super(index, type);
    }

    public EsPut(String index, String type, List<Map<String, Object>> putMaps) {
        super(index, type);
        this.putMaps = putMaps;
    }

    public List<Map<String, Object>> getPutMaps() {
        return putMaps;
    }

    public EsPut setPutMaps(List<Map<String, Object>> putMaps) {
        this.putMaps.addAll(putMaps);
        return this;
    }

    public EsPut setPutMap(Map<String, Object> putMap) {
        this.putMaps.add(putMap);
        return this;
    }
}
