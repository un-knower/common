package com.postss.common.boost.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.system.exception.SystemException;
import com.postss.common.util.Checker;
import com.postss.common.util.ClassPathDataUtil;
import com.postss.common.util.HtmlUtil;
import com.postss.common.util.PropertiesUtil;

//@AutoWebApplicationConfig("propertiesSqlBoostHandler")
public class PropertiesSqlBoostHandler extends AbstractSqlBoostHandler implements InitializingBean {

    private String propertiesName;
    private Map<String, String> sqlMap = new ConcurrentHashMap<String, String>();
    private String boostName = "/boost/prosql/**";

    public PropertiesSqlBoostHandler(String propertiesName) {
        super();
        this.propertiesName = propertiesName;
    }

    @Override
    public ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String requestURI = request.getRequestURI();
        String propertiesName = pathMatcher.extractPathWithinPattern(getUrlMapping(), requestURI);
        String sql = sqlMap.get(propertiesName);
        Checker.notNull(sql, propertiesName + "对应的sql");
        Map<String, Object> params = new HashMap<>();
        for (Entry<String, String[]> entry : paramMap.entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }
        List<Map<String, Object>> result = executeSqlAuto(params, sqlMap.get(propertiesName));
        HtmlUtil.writerJson(response, JSONObject.toJSONString(result));
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = PropertiesUtil.loadProperties(propertiesName);
        if (properties == null)
            throw new SystemException("cant load properties:" + propertiesName + ", null");
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String sql = ClassPathDataUtil.loadData(entry.getValue().toString());
            sqlMap.put(entry.getKey().toString(), sql);
        }
        log.info("load sql properties:{}", sqlMap);
    }

    @Override
    public String getUrlMapping() {
        return this.boostName;
    }

}
