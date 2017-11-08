package com.postss.common.base.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.postss.common.base.enums.QueryType;
import com.postss.common.base.page.QueryPage;

/**
 * 使用JPA Service公共基础类
 * @className BaseJpaService
 * @author jwSun
 * @date 2017年6月28日 下午8:10:54
 * @version 1.0.0
 * @param <T>
 */
public interface BaseJpaService<T> extends BaseService<T> {

    /**
     * 根据id修改单个属性
     * @author jwSun
     * @date 2016年10月13日下午8:17:47
     * @param id 修改资源id
     * @param key 修改资源key
     * @param value 修改资源value
     */
    public void updateById(Serializable id, Field key, Object value);

    /**
     * 根据id查询单个数据
     * @param id 查询数据id
     * @return T 查询类
     * @date 2016年7月17日下午12:06:13
     */
    public T findById(Serializable id);

    /**
     * 根据调传入条件查询List
     * @param t 带查询条件的查询类
     * @param basePage 分页参数
     * @param likeFields 使用like条件查询的属性值
     * @return List<T> 查询结果列表
     */
    public Page<T> findList(T t, QueryPage basePage, String... likeFields);

    /**
     * 根据key-value查询
     * @param clazz 查询的类型
     * @param key 查询属性名
     * @param value 查询值
     * @return
     */
    public List<T> findByField(Class<T> clazz, String key, Object value);

    /**
     * 根据key-value查询
     * @param clazz 查询的类型
     * @param key  查询属性名
     * @param value 查询值
     * @param queryType 查询类型(like equal)
     * @return
     */
    public List<T> findByField(Class<T> clazz, String key, Object value, QueryType queryType);

    //**************************自定义功能*************************************//

    /**
     * 获得一个新的HashMap<String,Object>
     * @param t
     * @return Map<String, Object> 
     * @author jwSun
     * @date 2016年7月17日下午12:06:26
     */
    public Map<String, Object> getMap();

    /**
     * 获得一个新的带有数据的HashMap<String,Object>
     * @param t
     * @return Map<String, Object>
     * @author jwSun
     * @date 2016年7月17日下午12:06:26
     */
    public Map<String, Object> getMap(String key, Object value);
}
