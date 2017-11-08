package com.postss.common.base.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.postss.common.base.dao.BaseDao;
import com.postss.common.base.page.QueryPage;
import com.postss.common.base.service.BaseService;

/**
 * Service公共实现基础类
 * @className BaseServiceImpl
 * @author jwSun
 * @date 2017年6月28日 下午8:10:40
 * @version 1.0.0
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public abstract BaseDao<T> getDao();

    /**
     * 增加
     * 
     * @param t
     * @author jwSun
     * @date 2016年7月17日下午12:03:47
     */
    public void add(T t) {
        getDao().save(t);
    }

    /**
     * 修改
     * 
     * @author jwSun
     * @date 2016年10月6日下午5:36:25
     * @param t
     */
    public T update(T t) {
        getDao().update(t);
        return t;
    }

    /**
     * 根据id修改单个属性
     * 
     * @author jwSun
     * @date 2016年10月13日下午8:17:47
     * @param id
     *            修改资源id
     * @param key
     *            修改资源key
     * @param value
     *            修改资源value
     */
    public void updateById(Integer id, Field key, Object value) {
        if (key == null) {
            throw new RuntimeException("NO FIELD!");
        }
        // getDao().updateById(id, key.getName(), value);
    }

    /**
     * 根据id查询
     * 
     * @param id
     * @return T
     * @author jwSun
     * @date 2016年7月17日下午12:06:13
     */
    public T findById(Integer id) {
        T findT = getDao().findById(id);
        return findT;
    }

    /**
     * 根据调传入条件查询List
     * 
     * @return List<T>
     * @author jwSun
     * @date 2016年7月17日下午12:06:27
     */
    public List<T> findList(QueryPage basePage) {
        Integer count = getDao().findCount(basePage);
        if (basePage.getBasePage() != null) {
            basePage.getBasePage().setTotal(count);
        }
        if (!count.equals(0)) {
            List<T> findList = getDao().findList(basePage);
            return findList;
        }
        return new ArrayList<T>();
    }

    /**
     * 根据key-value查询
     * 
     * @param t
     * @return List<T>
     * @author jwSun
     * @date 2016年7月17日下午12:06:26
     */
    public List<T> findByMap(String key, Object value) {
        Map<String, Object> map = getMap(key, value);
        return getDao().findByMap(map);
    }

    /**
     * 删除
     * 
     * @param ids
     * @return void
     * @author jwSun
     * @date 2016年7月17日下午12:04:59
     */
    public void softDelete(Integer[] ids) {
        for (Integer id : ids) {
            getDao().softDelete(id);
        }
    }

    // ***************************自定义方法********************************//
    /**
     * 获得一个新的HashMap
     * 
     * @author jwSun
     * @date 2016年9月24日上午9:39:58
     */
    public Map<String, Object> getMap() {
        return new HashMap<String, Object>();
    }

    /**
     * 获得一个新的带有数据的HashMap
     * 
     * @author jwSun
     * @date 2016年9月24日上午9:39:58
     */
    public Map<String, Object> getMap(String key, Object value) {
        Map<String, Object> map = getMap();
        map.put(key, value);
        return map;
    }

}
