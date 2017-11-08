package com.postss.common.system.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.postss.common.annotation.Explain;
import com.postss.common.base.annotation.AutoWebApplicationConfig;
import com.postss.common.base.entity.ResultEntity;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.entity.MethodLog;
import com.postss.common.log.enums.LogStatus;
import com.postss.common.log.interfaces.LogManager;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.code.CodeMessageManager;
import com.postss.common.system.code.SystemCode;
import com.postss.common.system.exception.AbstractBaseCodeException;
import com.postss.common.system.exception.BaseException;
import com.postss.common.system.exception.SystemCodeException;
import com.postss.common.util.HtmlUtil;
import com.postss.common.util.ResponseUtil;
import com.postss.common.util.SpringBeanUtil;
import com.postss.common.util.XmlWebApplicationContextUtil;

/**
 * 控制层aop执行类
 * <pre>@Around -> @Before -> invokeMethod -> @After</pre>
 * @see com.postss.common.annotation.Explain
 * @className ExplainAspect
 * @author jwSun
 * @date 2017年6月28日 下午7:36:36
 * @version 1.0.0
 */
@Aspect
@AutoWebApplicationConfig("defaultExplainAspect")
public class ExplainAspect implements Ordered, ApplicationListener<ContextRefreshedEvent> {

    private Logger log = LoggerUtil.getLogger(getClass());

    //spring boot 暂时不兼容,使用直接实例化对象方式
    //@Autowired
    //@Qualifier("logManager")
    @Autowired
    @Qualifier("logManager")
    private LogManager<MethodLog> logManager;

    @Pointcut("@annotation(com.postss.common.annotation.Explain)")
    public void explainAspect() {
    }

    /**
     * 方法执行之前
     * @param joinPoint 目标类连接点对象
     */
    @Before("explainAspect()")
    public void beforeAop(JoinPoint joinPoint) {
        MethodLog methodLog = getLog(joinPoint, LogStatus.OK);
        logManager.sendLog(methodLog);
    }

    /**
     * 环绕
     * @param proceedingJoinPoint 目标类连接点对象
     * @return Object
     * @throws Throwable
     */
    @Around("explainAspect()")
    public Object aroundAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (SystemCodeException throwable) {
            MethodLog methodLog = getThrowableLog(proceedingJoinPoint, throwable);
            log.warn("SystemCodeException : {}", methodLog);
            if (throwable.isWriteLog()) {
                logManager.sendLog(methodLog);
            }
            HtmlUtil.writerJson(HtmlUtil.getResponse(),
                    ResponseUtil.codeResolveJson(ResultEntity.fail(throwable.getSystemCode())
                            .setPromptMessage(throwable.getPromptMessage()).setThrowable(throwable).build()));
        } catch (AbstractBaseCodeException throwable) {
            MethodLog methodLog = getThrowableLog(proceedingJoinPoint, throwable);
            log.warn("BaseCodeException : {}", methodLog);
            if (throwable.isWriteLog()) {
                logManager.sendLog(methodLog);
            }
            HtmlUtil.writerJson(HtmlUtil.getResponse(),
                    ResponseUtil.codeResolveJson(ResultEntity
                            .fail(throwable.getBusinessCode() == CodeMessageManager.NO_MESSAGE_CODE
                                    ? methodLog.getExceptionCode() : throwable.getBusinessCode())
                            .setPromptMessage(throwable.getPromptMessage()).setThrowable(throwable).build()));
        } catch (BaseException throwable) {
            MethodLog methodLog = getThrowableLog(proceedingJoinPoint, throwable);
            log.warn("BaseException : {}", methodLog);
            if (throwable.isWriteLog()) {
                logManager.sendLog(methodLog);
            }
            HtmlUtil.writerJson(HtmlUtil.getResponse(),
                    ResponseUtil.codeResolveJson(ResultEntity.fail(SystemCode.FAIL).setThrowable(throwable).build()));
        } catch (Exception throwable) {
            MethodLog methodLog = getThrowableLog(proceedingJoinPoint, throwable);
            log.error("Exception : {} ", methodLog.toString(), throwable);
            logManager.sendLog(methodLog);
            HtmlUtil.writerJson(HtmlUtil.getResponse(), ResponseUtil
                    .codeResolveJson(ResultEntity.fail(methodLog.getExceptionCode()).setThrowable(throwable).build()));
        } catch (Throwable throwable) {
            MethodLog methodLog = getThrowableLog(proceedingJoinPoint, throwable);
            log.error("Throwable : {}", throwable);
            logManager.sendLog(methodLog);
            HtmlUtil.writerJson(HtmlUtil.getResponse(),
                    ResponseUtil.codeResolveJson(ResultEntity.fail(SystemCode.ERROR).setThrowable(throwable).build()));
        }
        return null;
    }

    /**
     * 执行注解方法之后/aroundAop之后
     * @date 2016年10月2日上午9:02:17
     * @param joinPoint 目标连接点对象
     */
    @After("explainAspect()")
    public void afterAop(JoinPoint joinPoint) {
        //System.out.println("@After");
    }

    /**
     * 注解方法发生异常时执行,由于使用了环绕,此方法不会被调用
     * @date 2016年10月2日上午9:37:28
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(throwing = "exception", pointcut = "explainAspect()")
    public void errorAop(JoinPoint joinPoint, Throwable exception) {
        //log.warn("@AfterThrowing");
    }

    /**
     * 获得组装的Log类
     * @param joinPoint
     * @return
     */
    private MethodLog getLog(JoinPoint joinPoint, LogStatus logStatus) {
        Explain explain = JoinPointUtil.getAnnotationByJoinPoint(joinPoint, Explain.class);
        if (explain == null)
            throw new SystemCodeException(SystemCode.NO_EXPLAIN);
        boolean hasSystemErrorCode = false;
        if (explain.systemCode() != SystemCode.NOTHING) {
            hasSystemErrorCode = true;
        }
        HttpServletRequest request = HtmlUtil.getRequest();
        String ipAddress = HtmlUtil.getIP(request);
        String controllerName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String params = ParamUtil.getParamJson(joinPoint);
        MethodLog methodLog = MethodLog.buildLog().setIp(ipAddress).setRequestMethod(request.getMethod())
                .setParams(params).setCreateTime(new Date()).setTargetName(controllerName)
                .setRequestUri(request.getRequestURI()).setMethodName(methodName)
                .setExceptionCode(hasSystemErrorCode ? explain.systemCode().getCode() : explain.exceptionCode())
                .setStatus(logStatus.key).setTitle(explain.value());

        return methodLog;
    }

    private void setThrowable(Throwable throwable, MethodLog methodLog) {
        Class<? extends Throwable> exceptionClass = throwable.getClass();
        if (throwable.getCause() != null) {
            methodLog.setException(exceptionClass.getName() + " : promptMessage is " + throwable.getLocalizedMessage()
                    + " Causeby " + throwable.getCause().getLocalizedMessage());
        } else {
            methodLog
                    .setException(exceptionClass.getName() + " : promptMessage is  " + throwable.getLocalizedMessage());
        }
    }

    private MethodLog getThrowableLog(JoinPoint joinPoint, Throwable throwable) {
        MethodLog methodLog = getLog(joinPoint, LogStatus.ERROR);
        setThrowable(throwable, methodLog);
        return methodLog;
    }

    /**
     * 事物order:3
     */
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext().getParent();
        if (context.getClass() == XmlWebApplicationContext.class) {
            if (SpringBeanUtil.getApplicationContext() == null) {
                throw new RuntimeException("XmlWebApplicationContextUtil ApplicationContext 不存在，请检查SpringBeanUtil是否配置");
            }
            RequestMappingHandlerMapping mapping = XmlWebApplicationContextUtil
                    .getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> methodmMap = mapping.getHandlerMethods();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> methodEntry : methodmMap.entrySet()) {
                //判断返回值是否为ResultEntity
                Method doMethod = methodEntry.getValue().getMethod();
                if (ResultEntity.class == doMethod.getReturnType()) {
                    //如果是则检查是否有@Explain注解,如没有则抛出异常，启动失败。
                    Explain explain = doMethod.getDeclaredAnnotation(Explain.class);
                    if (explain == null) {
                        throw new SystemCodeException(SystemCode.NO_EXPLAIN, "method:" + doMethod);
                    }
                }
            }
        }
    }

}
