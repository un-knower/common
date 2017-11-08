package com.postss.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.postss.common.util.StringUtil;

/**
 * 解决跨域问题过滤器
 * @className AccessControlFilter
 * @author jwSun
 * @date 2017年7月21日 上午10:44:42
 * @version 1.0.0
 */
public class AccessControlFilter implements Filter {

    //private Logger log = LoggerUtil.getLogger(AccessControlFilter.class);

    /**允许访问站点**/
    private final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    /**允许访问站点值**/
    private final String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";
    /**允许访问方法**/
    private final String ACCESS_CONTROL_ALLOW_METHOD = "Access-Control-Allow-Method";
    /**允许访问方法值**/
    private final String ACCESS_CONTROL_ALLOW_METHOD_VALUE = "POST,GET,PUT,DELETE";
    private final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    private final String ACCESS_CONTROL_ALLOW_CREDENTIALS_VALUE = "true";
    //请求域名
    private final String ORIGIN = "Origin";

    public AccessControlFilter() {

    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(ACCESS_CONTROL_ALLOW_METHOD, ACCESS_CONTROL_ALLOW_METHOD_VALUE);
        httpServletResponse.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, ACCESS_CONTROL_ALLOW_CREDENTIALS_VALUE);
        String origin = httpServletRequest.getHeader(ORIGIN);
        if (StringUtil.notEmpty(origin)) {
            httpServletResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        } else {
            httpServletResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }
}