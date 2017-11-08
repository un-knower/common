package com.postss.common.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Dao公共基础类
 * ClassName: BaseDao 
 * @author jwSun
 * @date 2016年7月17日
 */
@NoRepositoryBean
public interface BaseJpaDao<T> extends JpaRepository<T, String> {

    /**
     * 增加
     * @author jwSun
     * @date 2017年2月5日 下午2:50:51
     */
    /*
    
    public void save(T t);
    
    *//**
      * 修改
      * @param @param t
      * @param @return   
      * @return T  
      * @author jwSun
      * @date 2016年7月17日下午12:06:07
      */
    /*
    public void update(T t);
    
    *//**
      * 根据id查询
      * @param @param id
      * @param @return   
      * @return T  
      * @author jwSun
      * @date 2016年7月17日下午12:06:13
      */
    /*
    public T findById(Object id);
    
    *//**
      * 根据调传入条件查询List数量
      * @param @param t
      * @param @return   
      * @return T  
      * @author jwSun
      * @date 2016年7月17日下午12:06:26
      */
    /*
    public Integer findCount(Page basePage);
    
    *//**
      * 根据调传入条件查询List
      * @param @param t
      * @param @return
      * @return T
      * @author jwSun
      * @date 2016年7月17日下午12:06:26
      */
    /*
    public List<T> findList(Page basePage);
    
    *//**
      * 根据key-value查询
      * @param t
      * @return List<T> 
      * @author jwSun
      * @date 2016年7月17日下午12:06:26
      */
    /*
    public List<T> findByMap(Map<String, Object> map);
    
    *//**
      * 删除
      * @param @param t   
      * @return void  
      * @author jwSun
      * @date 2016年7月17日下午12:04:59
      */
    /*
    public void delete(Integer id);*/

}
