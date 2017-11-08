package com.postss.common.boost.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

public class BoostHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof BoostHandler;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        BoostHandler boostHandler = (BoostHandler) handler;
        return boostHandler.handle(request, response);
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return 0;
    }

}
