package com.postss.common.extend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.postss.common.base.entity.ResultEntity;
import com.postss.common.extend.web.context.support.SupportXmlWebApplicationContext;
import com.postss.common.system.code.SystemCode;
import com.postss.common.system.spring.converter.ParamMethodArgumentResolver;
import com.postss.common.util.HtmlUtil;
import com.postss.common.util.JSONObjectUtil;
import com.postss.common.util.ResponseUtil;

/**
 * springmvc servlet 扩展
 * @className DispatcherServlet
 * @author jwSun
 * @date 2017年9月24日 下午7:51:37
 * @version 1.0.0
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {

    private static final long serialVersionUID = -2067077725269764849L;

    /**
     * 404处理
     */
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(404);
        HtmlUtil.writerJson(response, JSONObjectUtil.toJSONString(ResponseUtil
                .codePrompt(ResultEntity.fail(SystemCode.NO_PAGE).setData(request.getRequestURI()).build())));
    }

    /**扩展功能**/

    /**
     * 使用自定义扩展上下文环境
     */
    public DispatcherServlet() {
        setContextClass(SupportXmlWebApplicationContext.class);
    }

    /**自定义converter生效-begin**/
    private List<HandlerAdapter> handlerAdapters;
    private boolean detectAllHandlerAdapters = true;

    protected void initStrategies(ApplicationContext context) {
        super.initStrategies(context);
        initHandlerAdapters(context);
    }

    /**
     * 初始化handlerAdapters,若有{@link RequestMappingHandlerAdapter}则增加自定义参数解析器
     * @param context
     */
    protected void initHandlerAdapters(ApplicationContext context) {
        this.handlerAdapters = null;

        if (this.detectAllHandlerAdapters) {
            Map<String, HandlerAdapter> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context,
                    HandlerAdapter.class, true, false);
            if (!matchingBeans.isEmpty()) {
                this.handlerAdapters = new ArrayList<HandlerAdapter>(matchingBeans.values());
                AnnotationAwareOrderComparator.sort(this.handlerAdapters);
                handlerAdapters.forEach((adapter) -> {
                    //增加自定义解析转换
                    if (adapter.getClass() == RequestMappingHandlerAdapter.class) {
                        RequestMappingHandlerAdapter requestMappingHandlerAdapter = (org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter) adapter;
                        List<HandlerMethodArgumentResolver> resolversAdd = new ArrayList<>();
                        Map<String, ParamMethodArgumentResolver> paramMethodArgumentResolver = BeanFactoryUtils
                                .beansOfTypeIncludingAncestors(context, ParamMethodArgumentResolver.class, true, false);
                        paramMethodArgumentResolver.forEach((beanName, bean) -> {
                            resolversAdd.add(bean);
                        });
                        requestMappingHandlerAdapter.setCustomArgumentResolvers(resolversAdd);
                        requestMappingHandlerAdapter.setArgumentResolvers(null);
                        requestMappingHandlerAdapter.afterPropertiesSet();
                    }
                });
            }
        } else {
            try {
                HandlerAdapter ha = context.getBean(HANDLER_ADAPTER_BEAN_NAME, HandlerAdapter.class);
                this.handlerAdapters = Collections.singletonList(ha);
            } catch (NoSuchBeanDefinitionException ex) {
                // Ignore, we'll add a default HandlerAdapter later.
            }
        }

        // Ensure we have at least some HandlerAdapters, by registering
        // default HandlerAdapters if no other adapters are found.
        if (this.handlerAdapters == null) {
            this.handlerAdapters = getDefaultStrategies(context, HandlerAdapter.class);
            if (logger.isDebugEnabled()) {
                logger.debug("No HandlerAdapters found in servlet '" + getServletName() + "': using default");
            }
        }
    }

    /**
     * 获得处理类
     */
    protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
        for (HandlerAdapter ha : this.handlerAdapters) {
            if (logger.isTraceEnabled()) {
                logger.trace("Testing handler adapter [" + ha + "]");
            }
            if (ha.supports(handler)) {
                return ha;
            }
        }
        throw new ServletException("No adapter for handler [" + handler
                + "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
    }
    /**自定义converter生效-end**/

}
