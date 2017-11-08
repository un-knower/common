package com.postss.common.base.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求返回实体类
 * @author jwSun
 * @date 2017年5月8日 下午7:42:51
 */
public class BaseResponseEntity {

    public enum RetData {
        ROWS("rows"), INFO("info");
        public String val;

        private RetData(String val) {
            this.val = val;
        }
    }

    public boolean success;//响应状态
    public boolean validate;//验证状态
    public int code;//返回码
    public Map<RetData, Object> data;//返回数据

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public int getCode() {
        return code;
    }

    public BaseResponseEntity setCode(int code) {
        this.code = code;
        return this;
    }

    public Map<RetData, Object> getData() {
        return data;
    }

    public BaseResponseEntity setData(Map<RetData, Object> data) {
        this.data = data;
        return this;
    }

    public BaseResponseEntity putData(RetData retData, Object val) {
        if (this.data == null) {
            this.data = new HashMap<RetData, Object>();
        }
        this.data.put(retData, val);
        return this;
    }

}
