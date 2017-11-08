package com.postss.common.log.entity;

import java.io.Serializable;

/**
 * 基础日志
 * @className BaseLog
 * @author jwSun
 * @date 2017年7月4日 下午5:53:26
 * @version 1.0.0
 */
public class BaseLog implements Serializable {

    private static final long serialVersionUID = -5239387078477718207L;

    private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
