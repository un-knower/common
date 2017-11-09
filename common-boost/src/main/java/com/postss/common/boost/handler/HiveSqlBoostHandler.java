package com.postss.common.boost.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.postss.common.base.annotation.AutoWebApplicationConfig;
import com.postss.common.util.HtmlUtil;
import com.postss.common.util.StringUtil;

@AutoWebApplicationConfig("hiveSqlBoostHandler")
public class HiveSqlBoostHandler extends AbstractSqlBoostHandler {

    @Override
    @SuppressWarnings("unchecked")
    public ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response) {
        String sql = request.getParameter("sql");
        if (StringUtil.notEmpty(sql)) {
            List<Object> list = (List<Object>) executeSql(null, sql);
            StringBuilder sb = new StringBuilder();
            for (Object obj : list) {
                Object[] row = (Object[]) obj;
                for (Object one : row) {
                    sb.append(one + "\t");
                }
                sb.append("\n");
            }
            HtmlUtil.writerJson(response, sb.toString());
        } else {
            HtmlUtil.writerJson(response, "no sql");
        }
        return null;
    }

    @Override
    public String getUrlMapping() {
        return "/boost/sql/hive";
    }

}
