package com.postss.common.boost.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.postss.common.annotation.Explain;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

public abstract class AbstractBoostHandler implements BoostHandler {

    protected Logger log = LoggerUtil.getLogger(getClass());

    @Override
    @Explain(exceptionCode = 0, value = "boost execute sql")
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("boostHandler handle...");
        }
        return handleInternal(request, response);
    }

    /**
     * 执行sql操作
     * @param request
     * @param response
     * @return
     */
    @Explain(exceptionCode = 0, value = "boost execute sql")
    public abstract ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response);
}
