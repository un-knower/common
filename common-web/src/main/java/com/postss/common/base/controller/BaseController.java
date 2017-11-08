package com.postss.common.base.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.postss.common.base.entity.ResultEntity;
import com.postss.common.base.page.QueryPage;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.HtmlUtil;

/**
 * Controller公共基础类
 * ClassName: BaseController 
 * @author jwSun
 * @date 2016年7月17日
 */
public class BaseController {

    protected Logger log = LoggerUtil.getLogger(getClass());

    //参数绑定编辑器
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        /*binder.registerCustomEditor(int.class, new MyEditor());
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());*/
    }

    public static ResultEntity getResolveEntity(int businessCode) {
        return ResultEntity.success(businessCode).build();
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String successMessage(QueryPage basePage, List<?> list, String... filterName) {
        return HtmlUtil.successMessage(basePage, list, filterName);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String successMessage(int total, List<?> list, String... filterName) {
        return HtmlUtil.successMessage(total, list, filterName);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String successMessage(long total, List<?> list, String... filterName) {
        return HtmlUtil.successMessage(total, list, filterName);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String successMessage(org.springframework.data.domain.Page<?> page, String... filterName) {
        return HtmlUtil.successMessage(page, filterName);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String successMessage(Object obj, String... filterName) {
        return HtmlUtil.successMessage(obj, filterName);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String successMessage(String info) {
        return HtmlUtil.successMessage(info);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String successMessage() {
        return HtmlUtil.successMessage();
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String failMessage() {
        return HtmlUtil.failMessage();
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String failMessage(Map<String, Object> result, String... filterName) {
        return HtmlUtil.failMessage(result, filterName);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String failMessage(String info) {
        return HtmlUtil.failMessage(info);
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String tokenErrorMessage() {
        return HtmlUtil.tokenErrorMessage();
    }

    /*******************************由于系统管理正在使用,所以暂时留着,新系统不可使用****************************************/
    @Deprecated
    public static String tokenErrorMessage(String info) {
        return HtmlUtil.tokenErrorMessage(info);
    }
}
