package com.postss.common.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import com.postss.common.base.enums.QueryType;
import com.postss.common.base.page.BasePage;
import com.postss.common.base.page.QueryPage;
import com.postss.common.base.repository.BaseRepository;
import com.postss.common.base.repository.BaseRepositoryEntity;
import com.postss.common.base.service.BaseJpaService;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.JpaUtil;
import com.postss.common.util.ReflectUtil;

/**
 * 使用JPA Service公共基础类
 * @className BaseJpaService
 * @author jwSun
 * @date 2017年6月28日 下午8:10:54
 * @version 1.0.0
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class BaseJpaServiceImpl<T> implements BaseJpaService<T> {

    protected Logger log = LoggerUtil.getLogger(getClass());

    public abstract BaseRepositoryEntity getDao();

    @SuppressWarnings("rawtypes")
    public BaseRepository getDaoReal() {
        return (BaseRepository) getDao();
    }

    /**
     * 根据id修改单个属性
     * @date 2016年10月13日下午8:17:47
     * @param id
     *            修改资源id
     * @param key
     *            修改资源key
     * @param value
     *            修改资源value
     */
    public void updateById(Serializable id, Field key, Object value) {
        if (key == null) {
            throw new RuntimeException("NO FIELD!");
        }
        T t = (T) getDaoReal().findOne(id);
        if (t == null) {
            log.debug("method:updateById entity is undefined");
            return;
        }
        try {
            ReflectUtil.getWriteMethod(t.getClass(), key.getName()).invoke(t, value);
            getDaoReal().save(t);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 根据id查询单个数据
     * @param id 查询数据id
     * @return T 查询类
     * @date 2016年7月17日下午12:06:13
     */
    public T findById(Serializable id) {
        return (T) getDaoReal().findOne(id);
    }

    @Override
    public Page<T> findList(T t, QueryPage basePage, String... likeFields) {
        Specification<T> specification = JpaUtil.getSpecification(t, likeFields);
        if (basePage.getBasePage() == null) {
            basePage.setBasePage(new BasePage());
        }
        if (basePage.getBasePage().isAllResult()) {
            long count = getDaoReal().count(specification);
            if (count == 0L)
                return new PageImpl<T>(new ArrayList<T>(), basePage.getPageResolver(), count);
            basePage.getBasePage().setAllResultPage(Integer.parseInt(String.valueOf(count)));
        }
        return getDaoReal().findAll(specification, basePage.getPageResolver());
    }

    @Override
    public List<T> findByField(Class<T> clazz, String key, Object value) {
        Object obj;
        try {
            obj = clazz.newInstance();
            ReflectUtil.getWriteMethod(clazz, key).invoke(obj, value);
            Specification<T> specification = (Specification<T>) JpaUtil.getSpecification(obj);
            List<T> list = getDaoReal().findAll(specification);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findByField(Class<T> clazz, String key, Object value, QueryType queryType) {
        Object obj;
        try {
            obj = clazz.newInstance();
            ReflectUtil.getWriteMethod(clazz, key).invoke(obj, value);
            Specification<T> specification = null;
            if (queryType == QueryType.LIKE) {
                specification = (Specification<T>) JpaUtil.getSpecification(obj, new String[] { key });
            } else {
                specification = (Specification<T>) JpaUtil.getSpecification(obj);
            }
            List<T> list = getDaoReal().findAll(specification);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Serializable... ids) {
        for (Serializable id : ids) {
            getDaoReal().delete(id);
        }
    }

    @Override
    public T save(T t) {
        return (T) getDaoReal().save(t);
    }

    @Override
    public T update(T t) {
        return (T) getDaoReal().save(t);
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
