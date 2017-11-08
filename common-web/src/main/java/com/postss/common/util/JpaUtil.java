package com.postss.common.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;

import com.postss.common.annotation.QueryByBetween;
import com.postss.common.annotation.QueryByEquals;
import com.postss.common.annotation.QueryByLike;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;

/**
 * spring data jpa工具类
 * @className JpaUtil
 * @author jwSun
 * @date 2017年6月28日 下午8:03:24
 * @version 1.0.0
 */
@SuppressWarnings("deprecation")
public class JpaUtil {

    public static EntityManagerFactory entityManagerFactory = null;
    public static final String dataSource = PropertiesUtil.getProperty("import.jdbc.util") + "."
            + PropertiesUtil.getProperty("import.dataSource");
    private static Logger log = LoggerUtil.getLogger(JpaUtil.class);
    public static DataSource dataSourceImpl = null;

    static {
        org.springframework.orm.jpa.JpaTransactionManager JpaTransactionManager = (org.springframework.orm.jpa.JpaTransactionManager) SpringBeanUtil
                .getBean("transactionManager");
        entityManagerFactory = JpaTransactionManager.getEntityManagerFactory();
        dataSourceImpl = ((EntityManagerFactoryInfo) SpringBeanUtil.getBean("entityManagerFactory")).getDataSource();
    }

    /**
     * 原生sql查询(结果为jdbc的格式，没有key，尽量不用)
     * @author jwSun
     * @date 2017年4月6日 下午3:56:50
     * @param sql 执行sql
     * @return
     */
    public static List<?> execute(String sql) {
        return execute(sql, entityManagerFactory);
    }

    public static List<?> execute(String sql, EntityManagerFactory entityManagerFactory) {
        List<?> objecArraytList = null;
        objecArraytList = executeByJdbc(sql);
        if (objecArraytList == null) {
            objecArraytList = executeByHibernate(sql, entityManagerFactory);
        }
        return objecArraytList;
    }

    /**
     * 使用hibernate entityManagerFactory获取数据
     * @param sql
     * @param entityManagerFactory
     * @return
     */
    public static List<?> executeByHibernate(String sql, EntityManagerFactory entityManagerFactory) {
        List<?> objecArraytList = null;
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            Query query = em.createNativeQuery(sql);
            objecArraytList = query.getResultList();
            em.close();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return objecArraytList;
    }

    /**
     * 使用原生jdbc+连接池获取数据
     * @param sql
     * @return
     */
    public static List<?> executeByJdbc(String sql) {
        List<?> objecArraytList = null;
        Connection connection = null;
        try {
            connection = dataSourceImpl.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            objecArraytList = new ArrayList<>();
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) objecArraytList;
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] obj = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    obj[i - 1] = rs.getObject(i);
                }
                list.add(obj);
            }
            rs.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return objecArraytList;
    }

    /**
     * 获得Specification条件查询
     * @param obj 查询类
     * @return Specification
     */
    public static <T> Specification<T> getSpecification(T obj) {
        return (Specification<T>) getSpecification(obj, new String[] {});
    }

    /**
     * 获得Specification条件查询
     * @param obj 查询类
     * @param likeField 使用like查询的属性
     * @return Specification
     */
    public static <T> Specification<T> getSpecification(T obj, String[] likeField) {
        if (likeField == null)
            throw new RuntimeException("likeField IS NULL");

        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            Class<?> objclazz = obj.getClass();
            Field[] fields = objclazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }
                try {
                    Object val = ReflectUtil.getReadMethod(objclazz, field.getName()).invoke(obj);
                    if (val != null) {
                        if (ArrayUtils.contains(likeField, field.getName())) {
                            predicateList.add(cb.like(root.get(field.getName()), "%" + val + "%"));
                        } else {
                            predicateList.add(cb.equal(root.get(field.getName()), val));
                        }
                    }
                } catch (Exception e) {
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    /**
     * 获得Specification条件查询
     * @param obj 查询类
     * @param likeField 使用like查询的属性
     * @return Specification
     */
    public static <T> Specification<T> getSpecificationExcludeFields(T obj, String[] likeField, String[] ignoreFields) {
        if (likeField == null)
            throw new RuntimeException("likeField IS NULL");

        final Set<String> ignoreFieldsSet = new HashSet<>();
        if (ignoreFields != null && ignoreFields.length > 0) {
            Collections.addAll(ignoreFieldsSet, ignoreFields);
        }
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            Class<?> objclazz = obj.getClass();
            Field[] fields = objclazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID") || ignoreFieldsSet.contains(field.getName())) {
                    continue;
                }
                try {
                    Object val = ReflectUtil.getReadMethod(objclazz, field.getName()).invoke(obj);
                    if (val != null) {
                        if (ArrayUtils.contains(likeField, field.getName())) {
                            predicateList.add(cb.like(root.get(field.getName()), "%" + val + "%"));
                        } else {
                            predicateList.add(cb.equal(root.get(field.getName()), val));
                        }
                    }
                } catch (Exception e) {
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    /**
     * 根据注解类型添加条件
     * @author jwSun
     * @param <T>
     * @date 2017年4月5日 下午2:32:55
     */
    @SuppressWarnings("unused")
    @Deprecated
    private static <T> void setPredicateList(T obj, Field[] fields, T obj2, List<Predicate> predicateList, Field field,
            Object val, CriteriaBuilder cb, Root<T> root) {
        if (field.getDeclaredAnnotation(QueryByEquals.class) != null) {
            predicateList.add(cb.equal(root.get(field.getName()), val));
            return;
        } else if (field.getDeclaredAnnotation(QueryByLike.class) != null) {
            predicateList.add(cb.like(root.get(field.getName()), "%" + val + "%"));
            return;
        } else if (field.getDeclaredAnnotation(QueryByBetween.class) != null) {
            /*QueryByBetween queryByBetween = field.getDeclaredAnnotation(QueryByBetween.class);
            try {
                if (StringUtil.notEmpty(queryByBetween.lowFieldName())
                        && StringUtil.notEmpty(queryByBetween.highFieldName())) {
                    Field lowField = obj.getClass().getDeclaredField(queryByBetween.lowFieldName());
                    Object lowVal = ReflectUtil.getReadMethod(obj.getClass(), lowField.getName()).invoke(obj);
                    Object highVal = ReflectUtil.getReadMethod(obj.getClass(), lowField.getName()).invoke(obj);
                    predicateList.add(cb.between(root.get(field.getName()), lowVal, highVal));
                }
            } catch (Exception e) {
                return;
            }*/
        }

        //默认使用相等
        predicateList.add(cb.equal(root.get(field.getName()), val));
    }

    /**
     * 获得Specification条件查询
     * @author LuoJian
     * @param <T>
     * @date 2017年4月11日15:48:06
     *  gt， ge，lt， le
     *  大于、大于等于、小于、小于等于
     *  本方法默认为 like  后面提供 大于等于 、小于等于的字段
     *  不需要大于 小于的参数用null 代替
     */
    public static <T> Specification<T> getSpecification(T obj, String[] geField, String[] leField) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            Class<?> objclazz = obj.getClass();
            Field[] fields = objclazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("serialVersionUID")) {
                    continue;
                }

                try {
                    Object val = ReflectUtil.getReadMethod(objclazz, field.getName()).invoke(obj);
                    if (val != null) {
                        //setPredicateList(obj, fields, obj, predicateList, field, val, cb, root);
                        if (field.getType().isAssignableFrom(Number.class)) {//判断是不是数字类型
                            if (geField != null && ArrayUtils.contains(geField, field.getName())) {
                                predicateList.add(cb.ge(root.get(field.getName()), (Number) val));
                            }
                            if (leField != null && ArrayUtils.contains(leField, field.getName())) {
                                predicateList.add(cb.le(root.get(field.getName()), (Number) val));
                            }
                            if (geField == null && leField == null) {
                                predicateList.add(cb.equal(root.get(field.getName()), "%" + val + "%"));
                            }
                        } else if (field.getType() == Date.class) {
                            if (geField != null && ArrayUtils.contains(geField, field.getName())) {
                                predicateList.add(
                                        cb.greaterThanOrEqualTo(root.get(field.getName()).as(Date.class), (Date) val));
                            }
                            if (leField != null && ArrayUtils.contains(leField, field.getName())) {
                                predicateList.add(
                                        cb.lessThanOrEqualTo(root.get(field.getName()).as(Date.class), (Date) val));
                            }
                            if (geField == null && leField == null) {
                                predicateList.add(cb.equal(root.get(field.getName()), "%" + val + "%"));
                            }
                        } else {
                            predicateList.add(cb.like(root.get(field.getName()), "%" + val + "%"));
                        }
                    }
                } catch (Exception e) {

                }
            }

            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
