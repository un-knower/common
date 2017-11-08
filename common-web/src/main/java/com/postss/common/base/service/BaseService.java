package com.postss.common.base.service;

import java.io.Serializable;

/**
 * Service公共基础类
 * ClassName: BaseService 
 * @author jwSun
 * @date 2016年7月17日
 */
public interface BaseService<T> {

    //*************************基础查询功能*************************************//

    /**
     * 新增
     * @author jwSun
     * @date 2017年4月7日 上午11:04:51
     * @param t
     * @return
     */
    public T save(T t);

    /**
     * 更新
     * @author jwSun
     * @date 2017年4月7日 上午11:04:57
     * @param t
     * @return
     */
    public T update(T t);

    /**
     * 删除
     * @author jwSun
     * @date 2016年7月17日下午12:04:59
     */
    public void delete(Serializable... id);
}
