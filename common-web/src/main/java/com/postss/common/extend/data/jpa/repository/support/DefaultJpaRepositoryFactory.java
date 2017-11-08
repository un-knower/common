package com.postss.common.extend.data.jpa.repository.support;

import java.io.Serializable;
import java.lang.reflect.Proxy;

import javax.persistence.EntityManager;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.manager.DefaultAnnotationManager;

/**
 * 1.强制使用DefaultJpaRepository执行操作
 * 2.结果转换
 * @author jwSun
 * @date 2017年6月9日 下午2:55:33
 */
public class DefaultJpaRepositoryFactory extends JpaRepositoryFactory {

    private Logger log = LoggerUtil.getLogger(getClass());

    public DefaultJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    private ClassLoader classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected <T, ID extends Serializable> DefaultJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata,
            EntityManager entityManager) {
        JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
        DefaultJpaRepository<?, ?> repo = new DefaultJpaRepository(entityInformation, entityManager);
        return repo;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return DefaultJpaRepository.class;
    }

    @SuppressWarnings("unchecked")
    public <T> T getRepository(Class<T> repositoryInterface, Object customImplementation) {
        ProxyFactory result = new ProxyFactory();
        T t = super.getRepository(repositoryInterface, customImplementation);
        if (Proxy.isProxyClass(t.getClass())) {
            log.debug("addAdvice for FieldsConvert");
            result.setTarget(t);
            result.addAdvice(new SetResultInterceptor(t));
        }
        return (T) result.getProxy(classLoader);
    }

    public class SetResultInterceptor implements MethodInterceptor {

        private final Object target;

        public SetResultInterceptor(Object target) {
            super();
            this.target = target;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Object result = invocation.getMethod().invoke(target, invocation.getArguments());
            return DefaultAnnotationManager.resolveFieldsConvert(invocation.getMethod(), result);
        }

    }

}
