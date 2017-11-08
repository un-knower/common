package com.postss.common.util;

import java.util.HashMap;

/**
 * 快速增加hashmap
 * @className QuickHashMap
 * @author jwSun
 * @date 2017年6月30日 上午11:30:27
 * @version 1.0.0
 * @param <k>
 * @param <v>
 */
public class QuickHashMap<k, v> extends HashMap<k, v> {

    private static final long serialVersionUID = 499738867454050295L;

    /**
     * 快速增加
     * @author jwSun
     * @date 2017年4月24日 下午5:08:48
     * @param key
     * @param val
     * @return
     */
    public QuickHashMap<k, v> quickPut(k key, v val) {
        this.quickPut(key, val, Boolean.TRUE);
        return this;
    }

    /**
     * 快速增加
     * @author jwSun
     * @date 2017年4月24日 下午5:08:13
     * @param key
     * @param val
     * @param canNull 是否可以为空
     * @return
     */
    public QuickHashMap<k, v> quickPut(k key, v val, Boolean canNull) {
        if (val == null) {
            if (!canNull)
                return this;
        }
        super.put(key, val);
        return this;
    }

}
