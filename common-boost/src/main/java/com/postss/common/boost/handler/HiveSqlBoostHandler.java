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
        String split = request.getParameter("split");
        if (split == null)
            split = "\t";
        if (StringUtil.notEmpty(sql)) {
            List<Object> list = (List<Object>) executeSql(null, sql);
            StringBuilder sb = new StringBuilder();
            for (Object obj : list) {
                Object[] row = (Object[]) obj;
                int count = 0;
                for (Object one : row) {
                    //                    sb.append(one + "\t");
                    count++;
                    if (count != row.length) {
                        sb.append(one + split);
                    } else {
                        sb.append(one);
                    }
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
