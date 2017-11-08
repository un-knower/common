package com.postss.common.kylin.support.repository.factory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import com.postss.common.base.page.PageResolver;
import com.postss.common.kylin.api.KylinApi;
import com.postss.common.kylin.entity.ApiSqlResultEntity;
import com.postss.common.kylin.search.ApiSqlSearch;
import com.postss.common.kylin.support.repository.annotation.KylinQuery;
import com.postss.common.kylin.support.repository.annotation.KylinRepositoryBean;
import com.postss.common.kylin.support.repository.exception.KylinRepositoryNoProjectException;
import com.postss.common.kylin.support.repository.exception.KylinRepositoryParseSqlException;
import com.postss.common.kylin.util.KylinBusinessUtil;
import com.postss.common.kylin.util.KylinQueryUtil;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.manager.DefaultAnnotationManager;
import com.postss.common.sql.fragment.Fragment;
import com.postss.common.system.exception.SystemException;
import com.postss.common.util.PropertiesUtil;
import com.postss.common.util.StringUtil;

public class KylinRepositoryProxy implements MethodInterceptor {

    private static ClassLoader classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();

    private Logger log = LoggerUtil.getLogger(getClass());

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<? extends T> repositoryInterface, String configPath) {
        ProxyFactory result = new ProxyFactory();
        KylinRepositoryBean kylinRepository = repositoryInterface.getAnnotation(KylinRepositoryBean.class);
        if (kylinRepository == null) {
            throw new SystemException("no kylinRepository annotation");
        }
        result.addInterface(repositoryInterface);
        result.addAdvice(new KylinRepositoryProxy(repositoryInterface, configPath));
        return (T) result.getProxy(classLoader);
    }

    private Map<String, String> sqlMap = new ConcurrentHashMap<String, String>();
    private Class<?> clazz;
    private String project;

    public KylinRepositoryProxy(Class<?> clazz, String configPath) {
        super();
        this.clazz = clazz;
        KylinRepositoryBean kylinRepository = clazz.getAnnotation(KylinRepositoryBean.class);
        if (kylinRepository == null) {
            return;
        }
        this.project = getProject(clazz);
        log.debug("class:{} load,project is {}", clazz, project);
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String configName = getSqlConfigName(clazz, method);
            KylinQuery kylinQuery = method.getAnnotation(KylinQuery.class);
            String configSql = null;
            if (kylinQuery == null) {
                configSql = PropertiesUtil.getProperty(configName, configPath);
            } else {
                configSql = kylinQuery.value();
            }
            if (!StringUtil.notEmpty(configSql)) {
                throw new KylinRepositoryParseSqlException(
                        "cant find sql config:" + configName + " ,for class:" + clazz);
            }
            sqlMap.put(configName, configSql);
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String sql = sqlMap.get(getSqlConfigName(this.clazz, method));
        String realSql = replaceSql(sql, invocation.getArguments());
        ApiSqlSearch sqlSearch = KylinApi.createApiSqlSearch(this.project, realSql);
        Object[] arguments = invocation.getArguments();
        if (arguments != null && arguments.length > 0) {
            if (PageResolver.class.isAssignableFrom(arguments[arguments.length - 1].getClass())) {
                PageResolver pageable = (PageResolver) arguments[arguments.length - 1];
                if (pageable.getBasePage() != null) {
                    sqlSearch.setLimit(pageable.getPageSize());
                    sqlSearch.setOffset(pageable.getOffset());
                } else {
                    log.debug("basePage is null,ignore");
                }
            }
        }
        log.debug("query by sql : {}", sqlSearch.getSql());
        if (realSql == null) {
            throw new KylinRepositoryParseSqlException("sql is null");
        }
        ApiSqlResultEntity resultEntity = KylinQueryUtil.queryByApiSql(sqlSearch);
        Object result = resultEntity.getResults();
        if (method.getReturnType() == ApiSqlResultEntity.class) {
            return resultEntity;
        } else if (method.getReturnType() == String.class) {
            return result.toString();
        } else {
            //FieldsConvert注解生效
            Object resultReturn = DefaultAnnotationManager.resolveFieldsConvert(method, result);
            return resultReturn;
        }
    }

    // 匹配{}
    private static final String PATTERN_COMPILE = "\\{(.*?)\\}";

    private String replaceSql(String sql, Object[] params) {
        if (params == null || sql == null)
            return sql;
        try {
            Object[] replaceParams = params;
            List<String> replaceList = StringUtil.getPatternMattcherList(sql, PATTERN_COMPILE, 1);
            for (String replaceIndex : replaceList) {
                int index = Integer.parseInt(replaceIndex);
                if (index < replaceParams.length && index >= 0) {
                    if (replaceParams[index] instanceof Fragment) {
                        sql = sql.replaceAll("\\{" + index + "\\}",
                                ((Fragment) replaceParams[index]).toFragmentString());
                    } else {
                        sql = sql.replaceAll("\\{" + index + "\\}", replaceParams[index].toString());
                    }
                }
            }
        } catch (Exception e) {
            //log.error(" replace message error ,{}", e.getLocalizedMessage());
        }
        return sql;
    }

    private String getSqlConfigName(Class<?> clazz, Method method) {
        String clazzName = clazz.getSimpleName();
        String realName = clazzName.substring(0, clazzName.indexOf("Repository"));
        realName = Character.toLowerCase(realName.charAt(0)) + realName.substring(1);
        String methodName = method.getName();
        String configName = realName + "-" + methodName;
        return configName;
    }

    private String getProject(Class<?> clazz) {
        KylinRepositoryBean kylinRepository = this.clazz.getAnnotation(KylinRepositoryBean.class);
        if (kylinRepository == null) {
            throw new SystemException("kylinRepository is null");
        }
        if ((!StringUtil.notEmpty(kylinRepository.project()) && (!StringUtil.notEmpty(kylinRepository.cubeName())))) {
            throw new KylinRepositoryNoProjectException(clazz.getName());
        }
        if (StringUtil.notEmpty(kylinRepository.project())) {
            return kylinRepository.project();
        } else {
            return KylinBusinessUtil.queryProjectByCubeName(kylinRepository.cubeName()).getName();
        }
    }

}