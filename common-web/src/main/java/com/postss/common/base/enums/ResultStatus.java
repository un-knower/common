package com.postss.common.base.enums;

/**
 * 执行结果状态没枚举类
 * @className ResultStatus
 * @author jwSun
 * @date 2017年7月8日 下午1:46:22
 * @version 1.0.0
 */
public enum ResultStatus {

    SUCCESS(0), FAIL(1);

    private int value;

    private ResultStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
