package com.postss.common.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import com.postss.common.base.enums.QueryType;
import com.postss.common.base.page.BasePage;
import com.postss.common.base.page.QueryPage;
import com.postss.common.base.repository.BaseRepository;
import com.postss.common.base.service.BaseJpaService;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.exception.BeanNotFindException;
import com.postss.common.system.exception.SystemException;
import com.postss.common.util.ConvertUtil;
import com.postss.common.util.JpaUtil;
import com.postss.common.util.ReflectUtil;
import com.postss.common.util.SpringBeanUtil;
import com.postss.common.util.XmlWebApplicationContextUtil;

/**
 * 使用JPA Service公共基础类
 * @className BaseJpaService
 * @author jwSun
 * @date 2017年6月28日 下午8:10:54
 * @version 1.0.0
 * @param <T>
 */
public abstract class BaseJpaServiceImpl<T> implements BaseJpaService<T>, ApplicationListener<ContextRefreshedEvent> {

    protected Logger log = LoggerUtil.getLogger(getClass());

    protected BaseRepository<T, Serializable> repository;

    public BaseJpaServiceImpl(BaseRepository<T, Serializable> repository) {
        super();
        this.repository = repository;
    }

    public BaseJpaServiceImpl() {
        super();
    }

    protected BaseRepository<T, Serializable> getRepository() {
        return this.repository;
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
        T t = getRepository().findOne(id);
        if (t == null) {
            log.debug("method:updateById entity is undefined");
            return;
        }
        try {
            ReflectUtil.getWriteMethod(t.getClass(), key.getName()).invoke(t, value);
            getRepository().save(t);
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
        return getRepository().findOne(id);
    }

    @Override
    public Page<T> findList(T t, QueryPage basePage, String... likeFields) {
        Specification<T> specification = JpaUtil.getSpecification(t, likeFields);
        if (basePage.getBasePage() == null) {
            basePage.setBasePage(new BasePage());
        }
        if (basePage.getBasePage().isAllResult()) {
            long count = getRepository().count(specification);
            if (count == 0L)
                return new PageImpl<T>(new ArrayList<T>(), basePage.getPageResolver(), count);
            basePage.getBasePage().setAllResultPage(Integer.parseInt(String.valueOf(count)));
        }
        return getRepository().findAll(specification, basePage.getPageResolver());
    }

    @Override
    public List<T> findByField(String key, Object value) {
        T obj;
        try {
            Class<T> clazz = getThisClass();
            obj = clazz.newInstance();
            ReflectUtil.getWriteMethod(clazz, key).invoke(obj, value);
            Specification<T> specification = JpaUtil.getSpecification(obj);
            List<T> list = getRepository().findAll(specification);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findByField(String key, Object value, QueryType queryType) {
        try {
            Class<T> clazz = getThisClass();
            T obj = clazz.newInstance();
            ReflectUtil.getWriteMethod(clazz, key).invoke(obj, value);
            Specification<T> specification = null;
            if (queryType == QueryType.LIKE) {
                specification = JpaUtil.getSpecification(obj, new String[] { key });
            } else {
                specification = JpaUtil.getSpecification(obj);
            }
            List<T> list = getRepository().findAll(specification);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Serializable... ids) {
        for (Serializable id : ids) {
            getRepository().delete(id);
        }
    }

    @Override
    public T save(T t) {
        return getRepository().save(t);
    }

    @Override
    public T update(Serializable id, T t) {
        T find = getRepository().findOne(id);
        if (find == null) {
            return find;
        }
        Map<String, Object> fieldMap = ConvertUtil.beanConvertToMap(t);
        fieldMap.forEach((fieldName, value) -> {
            Method writeMethod = ReflectUtil.getWriteMethod(t.getClass(), fieldName);
            if (writeMethod != null) {
                try {
                    writeMethod.invoke(find, value);
                } catch (Exception e) {
                    log.warn("write field : " + fieldName + " error,cause by : " + e);
                }
            }
        });
        return getRepository().save(find);
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

    @SuppressWarnings("unchecked")
    protected Class<T> getThisClass() {
        return (Class<T>) ReflectUtil.getGenericInterfaces(getClass(), 0);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext().getParent();
        if (context != null) {
            if (XmlWebApplicationContextUtil.getApplicationContext() == null) {
                throw new RuntimeException("XmlWebApplicationContextUtil ApplicationContext 不存在，请检查SpringBeanUtil是否配置");
            }
            if (this.getClass().getSimpleName().indexOf("ServiceImpl") == -1) {
                throw new SystemException("服务层类名必须以ServiceImpl结尾");
            }
            String className = this.getClass().getSimpleName().substring(0,
                    this.getClass().getSimpleName().indexOf("ServiceImpl"));
            className = Character.toLowerCase(className.charAt(0)) + className.substring(1, className.length());
            String defaultBeanName = className + "Repository";
            Object repository = SpringBeanUtil.getBean(defaultBeanName);
            if (repository == null) {
                log.warn("cant find bean:" + defaultBeanName + ",please check");
                return;
                // throw new BeanNotFindException(defaultBeanName);
            }
            if (repository instanceof BaseRepository) {
                @SuppressWarnings("unchecked")
                BaseRepository<T, Serializable> findRepository = (BaseRepository<T, Serializable>) repository;
                log.info("auto config class: " + findRepository + " to service:" + this.getClass().getName());
                this.repository = findRepository;
            } else {
                throw new BeanNotFindException(defaultBeanName, BaseRepository.class);
            }
        }
    }
}
