package com.postss.common.es.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ES基本类
 * @author jwSun
 * @date 2017年5月2日 上午9:47:46
 */
public class BaseEsIndex {

    protected List<EsIndexEntity> esIndexs = new ArrayList<>();//ES索引集合

    public BaseEsIndex(String index, String type) {
        super();
        this.esIndexs.add(new EsIndexEntity(index, type));
    }

    public String getFirstIndex() {
        return esIndexs.get(0).getIndex();
    }

    public String getFirstType() {
        return esIndexs.get(0).getType();
    }

    public void multiIndex(EsIndexEntity... entities) {
        for (EsIndexEntity entity : entities) {
            this.esIndexs.add(entity);
        }
    }

    public List<EsIndexEntity> getEsIndexs() {
        return esIndexs;
    }

}
