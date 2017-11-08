package com.postss.common.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 守护线程
 * @className ExecutorsDaemon
 * @author jwSun
 * @date 2017年8月6日 下午4:30:02
 * @version 1.0.0
 */
public class ExecutorsDaemon {

    /**
     * 获得生产守护线程的线程工厂
     * @return
     */
    public static ThreadFactory getDaemonThreadFactory() {
        return new DefaultDaemonThreadFactory();
    }

    /**
     * 生产守护线程的线程池，Executors默认使用主线程会导致tomcat无法停止
     */
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
