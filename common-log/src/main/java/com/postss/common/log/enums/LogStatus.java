package com.postss.common.log.enums;

/**
 * 日志状态
 * @className LogStatus
 * @author jwSun
 * @date 2017年7月4日 下午7:07:36
 * @version 1.0.0
 */
public enum LogStatus {

    OK(0, "正常"), ERROR(1, "异常");

    public Integer key;
    public String value;

    private LogStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getByKey(Integer key) {
        if (key == null)
            return null;

        for (LogStatus d : LogStatus.values()) {
            if (key.equals(d.key)) {
                return d.value;
            }
        }
        return null;
    }

}
