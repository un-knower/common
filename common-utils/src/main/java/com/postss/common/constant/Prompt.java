package com.postss.common.constant;

/**
 * 公共提示常量
 * ClassName: Prompt 
 * @author jwSun
 * @date 2016年10月6日下午4:04:00
 */
public class Prompt {

    /******************ERROR**************************/
    public static String SERVER_ERROR = "sorry~服务器出错,请稍后再试";
    public static String PERSON_ERROR = "error";
    /******************VALIDATE**************************/
    public static String VALIDATE_BAD_PARAM = "sorry~参数不合法！";

    public static String VALIDATE_EXCEPTION = "sorry~程序出现异常,请稍候再试";
    public static String VALIDATE_NULL = "不能为空!";

    public static String VALIDATE_HAS_RECORD = "已有此记录";
    public static String VALIDATE_NO_RECORD = "没有此记录";

    /******************UPLOAD**************************/
    public static String UPLOAD_EMPTY_FILE = "sorry~您上传的是空文件";
    public static String UPLOAD_FAIL = "sorry~上传失败";

    /******************SQL**************************/
    public static String QUERY_SUCCESS = "查询成功！";
    public static String QUERY_FAIL = "查询失败！";
    public static String CHANGE_ADD_SUCCESS = "保存成功！";
    public static String CHANGE_ADD_FAIL = "保存失败！";
    public static String CHANGE_UPDATE_SUCCESS = "修改成功！";
    public static String CHANGE_UPDATE_FAIL = "修改失败！";
    public static String CHANGE_DEL_SUCCESS = "删除成功！";
    public static String CHANGE_DEL_FAIL = "删除失败！";
    public static String CHANGE_UPDATE_STATUS_SUCCESS = "更改状态成功";
    public static String CHANGE_UPDATE_STATUS_FAIL = "更改状态失败";

    /******************ACCOUNT**************************/
    public static String ACCOUNT_BAD_EMAIL = "sorry~验证失败,请从邮件点击链接,若确定为正确,可联系管理员~";
    public static String ACCOUNT_EMAIL_ERROR = "sorry~验证失败,请从邮件点击链接,若确定为正确,可联系管理员~";
    public static String ACCOUNT_LOGIN_ERROR = "sorry~您还没有注册或密码错误~";
    public static String ACCOUNT_NO_LOGIN = "sorry~您还没登陆,不能使用此功能哦~";

}
