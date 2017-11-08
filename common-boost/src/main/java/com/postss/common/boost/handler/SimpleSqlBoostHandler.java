package com.postss.common.boost.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.base.annotation.AutoWebApplicationConfig;
import com.postss.common.util.HtmlUtil;
import com.postss.common.util.StringUtil;

@AutoWebApplicationConfig("simpleSqlBoostHandler")
public class SimpleSqlBoostHandler extends AbstractSqlBoostHandler {

    @Override
    public ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response) {
        String sql = request.getParameter("sql");
        if (StringUtil.notEmpty(sql)) {
            HtmlUtil.writerJson(response, JSONObject.toJSONString(executeSql(null, sql)));
        } else {
            HtmlUtil.writerJson(response, "no sql");
        }
        return null;
    }

    @Override
    public String getUrlMapping() {
        return "/boost/sql";
    }

}
