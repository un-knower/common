package com.postss.common.base.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.postss.common.base.enums.ResultStatus;
import com.postss.common.constant.Constant;
import com.postss.common.system.code.SystemCode;
import com.postss.common.util.JSONObjectUtil;
import com.postss.common.util.QuickHashMap;

/**
 * 最终返回解析类
 * @className ResolveEntity
 * @author jwSun
 * @date 2017年6月28日 下午7:54:25
 * @version 1.0.0
 */
public class ResultEntity implements Serializable {

    private static final long serialVersionUID = -2287447606101207254L;

    /**返回数据**/
    private Object data;
    /**返回系统提示参数(返回码,提示信息)**/
    private ReturnInfo info = new ReturnInfo();
    /**验证状态,仅在success为false时才可能出现,false表明登陆失效**/
    private Boolean validate;
    /**成功状态**/
    private int status;
    /**过滤字段(返回不显示)**/
    private String[] filterNames;
    /**返回提示信息字段(返回不显示)**/
    private Object[] promptMessage;
    /**异常(返回不显示)**/
    private Throwable throwable;

    /**
     * 创建成功实体类
     * @param code 业务返回码
     * @return {@link com.postss.common.base.entity.ResultEntity.ResultBodyBuilder}
     */
    public static ResultBodyBuilder success(int code) {
        return new DefaultResultBodyBuilder().setCode(code).setStatus(ResultStatus.SUCCESS);
    }

    /**
     * 创建成功实体类
     * @param systemCode 系统返回码
     * @return {@link com.postss.common.base.entity.ResultEntity.ResultBodyBuilder}
     */
    public static ResultBodyBuilder success(SystemCode systemCode) {
        return new DefaultResultBodyBuilder().setSystemCode(systemCode).setStatus(ResultStatus.SUCCESS);
    }

    /**
     * 创建失败实体类
     * @param code 业务返回码
     * @return {@link com.postss.common.base.entity.ResultEntity.ResultBodyBuilder}
     */
    public static ResultBodyBuilder fail(int code) {
        return new DefaultResultBodyBuilder().setCode(code).setStatus(ResultStatus.FAIL);
    }

    /**
     * 创建失败实体类
     * @param systemCode 系统返回码
     * @return {@link com.postss.common.base.entity.ResultEntity.ResultBodyBuilder}
     */
    public static ResultBodyBuilder fail(SystemCode systemCode) {
        return new DefaultResultBodyBuilder().setSystemCode(systemCode).setStatus(ResultStatus.FAIL);
    }

    public static ResultBodyBuilder method(SystemCode systemCode, String functionParams) {
        return new DefaultResultFunctionBuilder().setFunctionParams(functionParams).setSystemCode(systemCode)
                .setStatus(ResultStatus.FAIL);
    }

    public Object getData() {
        return data;
    }

    public ResultEntity setData(Object data) {
        this.data = data;
        return this;
    }

    public ReturnInfo getInfo() {
        return info;
    }

    public ResultEntity setInfo(ReturnInfo info) {
        this.info = info;
        return this;
    }

    public Boolean getValidate() {
        return validate;
    }

    public ResultEntity setValidate(Boolean validate) {
        this.validate = validate;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ResultEntity setStatus(Integer status) {
        this.status = status == null ? ResultStatus.SUCCESS.getValue() : status;
        return this;
    }

    private ResultEntity(int code) {
        this.info.setCode(code);
    }

    private ResultEntity(SystemCode systemCode) {
        this.info.setSystemCode(systemCode);
    }

    public Object[] getPromptMessage() {
        return promptMessage;
    }

    public void setPromptMessage(Object[] promptMessage) {
        this.promptMessage = promptMessage;
    }

    /**
     * 默认返回builder
     * @className ResultBuilder
     * @author jwSun
     * @date 2017年7月3日 下午2:31:50
     * @version 1.0.0
     * @param <B>
     */
    public interface ResultBuilder<B extends ResultBuilder<B>> {

        /**
         * 执行结果状态
         * @param ResultStatus {@link com.postss.common.base.enums.ResultStatus}
         * @return
         */
        B setStatus(ResultStatus resultStatus);

        /**
         * 是否验证成功,如果为否则同时设置status为ResultStatus.FAIL
         * @param validate 验证布尔值
         * @return
         */
        B setValidate(boolean validate);

        /**
         * 设置返回码
         * @param code 返回码
         * @return
         */
        B setCode(int code);

        /**
         * 设置系统返回码
         * @param systemCode 返回码
         * @return
         */
        B setSystemCode(SystemCode systemCode);

        /**
         * 设置提示信息参数
         * @param promptMessage
         * @return
         */
        B setPromptMessage(Object... promptMessage);

        /**
         * 设置异常
         * @param throwable 异常
         * @return
         */
        B setThrowable(Throwable throwable);

        /**
         * 生成返回实体类
         * @return {@link com.postss.common.entity.ResultEntity}
         */
        ResultEntity build();
    }

    /**
     * 生成返回执行实体类
     * @className FunctionBuilder
     * @author jwSun
     * @date 2017年7月3日 下午2:33:47
     * @version 1.0.0
     */
    public interface ResultBodyBuilder extends ResultBuilder<ResultBodyBuilder> {
        /**
         * 存入数据
         * @param data 任意类型数据
         * @return ResultBodyBuilder
         */
        ResultBodyBuilder setData(Object data);

        /**
         * 存入集合
         * @param rows 集合数据
         * @return ResultBodyBuilder
         */
        ResultBodyBuilder setRows(List<?> rows);

        /**
         * 存入数据总数
         * @param total 总数
         * @return ResultBodyBuilder
         */
        ResultBodyBuilder setTotal(long total);

        /**
         * 存入spring-data-jpa查询出的Page
         * @param page spring-data-jpa查询结果
         * @return ResultBodyBuilder
         */
        ResultBodyBuilder setPage(org.springframework.data.domain.Page<?> page);

        /**
         * 存入过滤字段
         * @param filterNames 过滤字段
         * @return ResultBodyBuilder
         */
        ResultBodyBuilder setFilterNames(String... filterNames);
    }

    /**
     * 生成返回执行实体类
     * @className FunctionBuilder
     * @author jwSun
     * @date 2017年7月3日 下午2:33:47
     * @version 1.0.0
     */
    public interface FunctionBuilder extends ResultBodyBuilder {

        /**
         * 存入执行参数
         * @param params
         * @return FunctionBuilder
         */
        FunctionBuilder setFunctionParams(String functionParams);
    }

    private static class DefaultResultFunctionBuilder extends DefaultResultBodyBuilder implements FunctionBuilder {

        /**执行类**/
        private FunctionEntity functionEntity = new FunctionEntity();

        @Override
        public ResultEntity build() {
            ResultEntity resultEntity;
            if (this.info.getSystemCode() == null) {
                resultEntity = new ResultEntity(this.info.getCode());
            } else {
                resultEntity = new ResultEntity(this.info.getSystemCode());
            }
            resultEntity.setStatus(resultStatus.getValue()).setValidate(validate);
            if (this.data != null) {
                resultEntity.setData(data);
            } else if (this.rows != null) {
                Map<String, Object> returnMap = new QuickHashMap<String, Object>().quickPut(Constant.HTMLVALIDATE.ROWS,
                        this.rows);
                if (this.total != null) {
                    returnMap.put(Constant.HTMLVALIDATE.TOTAL, this.total);
                }
                resultEntity.setData(returnMap);
            }
            resultEntity.getInfo().setFunctionEntity(this.functionEntity);
            resultEntity.setPromptMessage(promptMessage);
            return resultEntity;
        }

        @Override
        public FunctionBuilder setFunctionParams(String functionParams) {
            this.functionEntity.setFunctionParams(functionParams);
            return this;
        }
    }

    private static class DefaultResultBodyBuilder implements ResultBodyBuilder {
        /**验证状态**/
        protected Boolean validate;
        /**成功状态**/
        protected ResultStatus resultStatus;
        /**返回系统提示参数(返回码,提示信息)**/
        protected final ReturnInfo info = new ReturnInfo();
        /**返回数据**/
        protected Object data;
        /**返回列表**/
        protected List<?> rows;
        /**数据总条数**/
        protected Long total;
        /**过滤字段**/
        protected String[] filterNames;
        /**提示参数**/
        protected Object[] promptMessage;
        /**异常**/
        protected Throwable throwable;

        @Override
        public ResultBodyBuilder setStatus(ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
            return this;
        }

        @Override
        public ResultBodyBuilder setValidate(boolean validate) {
            if (!validate) {
                this.setStatus(ResultStatus.FAIL);
            }
            this.validate = validate;
            return this;
        }

        @Override
        public ResultBodyBuilder setCode(int code) {
            this.info.setCode(code);
            return this;
        }

        @Override
        public ResultBodyBuilder setData(Object data) {
            this.data = data;
            return this;
        }

        @Override
        public ResultBodyBuilder setRows(List<?> rows) {
            this.rows = rows;
            return this;
        }

        @Override
        public ResultBodyBuilder setTotal(long total) {
            this.total = total;
            return this;
        }

        @Override
        public ResultBodyBuilder setPage(Page<?> page) {
            if (page == null) {
                this.setTotal(0L);
                this.setRows(new ArrayList<Object>());
            } else {
                this.setTotal(page.getTotalElements());
                this.setRows(page.getContent());
            }
            return this;
        }

        @Override
        public ResultBodyBuilder setSystemCode(SystemCode systemCode) {
            this.info.setSystemCode(systemCode);
            return this;
        }

        @Override
        public ResultEntity build() {
            ResultEntity resultEntity;
            if (this.info.getSystemCode() == null) {
                resultEntity = new ResultEntity(this.info.getCode());
            } else {
                resultEntity = new ResultEntity(this.info.getSystemCode());
            }
            resultEntity.setStatus(resultStatus.getValue()).setValidate(validate);
            if (this.data != null) {
                resultEntity.setData(data);
            } else if (this.rows != null) {
                Map<String, Object> returnMap = new QuickHashMap<String, Object>().quickPut(Constant.HTMLVALIDATE.ROWS,
                        this.rows);
                if (this.total != null) {
                    returnMap.put(Constant.HTMLVALIDATE.TOTAL, this.total);
                }
                resultEntity.setData(returnMap);
            }
            resultEntity.setThrowable(throwable);
            resultEntity.setFilterNames(filterNames);
            resultEntity.setPromptMessage(promptMessage);
            return resultEntity;
        }

        @Override
        public ResultBodyBuilder setFilterNames(String... filterNames) {
            this.filterNames = filterNames;
            return this;
        }

        @Override
        public ResultBodyBuilder setPromptMessage(Object... promptMessage) {
            this.promptMessage = promptMessage;
            return this;
        }

        @Override
        public ResultBodyBuilder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }
    }

    private String[] defaultFilterNames = new String[] { "promptMessage", "filterNames", "throwable" };

    /**
     * 获得所有过滤字段
     * @return 过滤字段数组
     */
    public String[] getFilterNames() {
        if (filterNames != null) {
            String[] array = new String[filterNames.length + 3];
            System.arraycopy(filterNames, 0, array, 0, filterNames.length);
            System.arraycopy(defaultFilterNames, 0, array, array.length - 3, defaultFilterNames.length);
            return array;
        } else {
            return defaultFilterNames;
        }
    }

    public ResultEntity setFilterNames(String... filterNames) {
        this.filterNames = filterNames;
        return this;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return JSONObjectUtil.toJSONString(this, getFilterNames());
    }

}
