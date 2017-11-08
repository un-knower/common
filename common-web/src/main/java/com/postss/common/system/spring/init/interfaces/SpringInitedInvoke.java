package com.postss.common.system.spring.init.interfaces;

/**
 * 调用指定spring bean方法
 * @ClassName SpringInitedInvoke
 * @author jwSun
 * @Date 2017年6月24日 上午10:19:01
 * @version 1.0.0
 */
public interface SpringInitedInvoke {

    /**
     * applicationContext初始化完成后执行此方法
     * @param obj
     */
    public void apply(Object obj);

}
