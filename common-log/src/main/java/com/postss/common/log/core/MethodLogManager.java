package com.postss.common.log.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.postss.common.base.annotation.AutoRootApplicationConfig;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.entity.MethodLog;
import com.postss.common.log.interfaces.LogManager;
import com.postss.common.log.util.LoggerUtil;

/**
 * 方法日志管理器
 * @className MethodLogManager
 * @author jwSun
 * @date 2017年7月4日 下午5:59:36
 * @version 1.0.0
 */
@AutoRootApplicationConfig("defaultMethodLogManager")
public class MethodLogManager implements LogManager<MethodLog> {

    private Logger log = LoggerUtil.getLogger(getClass());

    private ExecutorService sendLogService = Executors.newCachedThreadPool(new DefaultDaemonThreadFactory());

    @Override
    public boolean sendLog(MethodLog methodLog) {
        sendLogService.execute(() -> {
            log.info("sendLog...: {}", methodLog);
        });
        return false;
    }

    static class DefaultDaemonThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultDaemonThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (!t.isDaemon())
                t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
