package com.postss.common.boost.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface BoostHandler {

    /**
     * 执行操作
     * @param request
     * @param response
     * @return
     */
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获得执行的url
     * @return
     */
    public String getUrlMapping();

}
