package com.postss.common.base.enums;

/**
 * 有效性枚举类
 * @className DelFlag
 * @author jwSun
 * @date 2017年9月2日 上午11:07:53
 * @version 1.0.0
 */
public enum DelFlag {

    /**有效**/
    YES(0, "有效"),
    /**无效**/
    NO(1, "无效");

    public Integer key;
    public String value;

    private DelFlag(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getByKey(Integer key) {
        if (key == null)
            return null;

        for (DelFlag d : DelFlag.values()) {
            if (key.equals(d.key)) {
                return d.value;
            }
        }
        return null;
    }
}
