package TeseProxy;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

import com.postss.common.extend.org.springframework.context.config.AutoProxy;

public class TestProxy implements AutoProxy {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("123");
        return null;
    }

    @Override
    public AutoProxy getObject(Class<?> clazz, Map<String, Object> config) {
        return new TestProxy();
    }

}
