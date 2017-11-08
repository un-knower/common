package com.postss.common.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Transactional
@Repository
public class CommonDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	/**
	 * * 查询数据集合
	 * @param sql 查询sql sql中的参数用:name格式 
	 * @param params 查询参数map格式，key对应参数中的:name
	 * @param clazz 实体类型为空则直接转换为map格式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> queryListEntity(String sql,Map<String, Object> params, Class<?> clazz){
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result =  query.list();
		if (clazz != null) {
			List<Object>  entityList = convert(clazz, result);
			return entityList;
		}
		return result;
	}

	private List<Object> convert(Class<?> clazz, List<Map<String, Object>> list) {
		List<Object> result;
		if (CollectionUtils.isEmpty(list)) {
		    return null;
		}
		result = new ArrayList<Object>();
		try {
			 PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			 for (Map<String, Object> map : list) {
		         Object obj = clazz.newInstance();
		         for (String key:map.keySet()) {
		             String attrName = key.toLowerCase();
		             for (PropertyDescriptor prop : props) {
		            	 attrName = removeUnderLine(attrName);
		                 if (!attrName.equals(prop.getName())) {
		                     continue;
		                 }
		                 Method method = prop.getWriteMethod();
		                 Object value = map.get(key);
		                 if (value != null) {
		                     value = ConvertUtils.convert(value,prop.getPropertyType());
		                 }
		                 method.invoke(obj,value);
		             }
		         }
		         result.add(obj);
		     }
		} catch (Exception e) {
			throw new RuntimeException("数据转换错误");
		}
		return result;
	}

	private String removeUnderLine(String attrName) {
		//去掉数据库字段的下划线
		 if(attrName.contains("_")) {
			String[] names = attrName.split("_");
			String firstPart = names[0];
			String otherPart = "";
			for (int i = 1; i < names.length; i++) {
				String word = names[i].replaceFirst(names[i].substring(0, 1), names[i].substring(0, 1).toUpperCase());
				otherPart += word;
			}
			attrName = firstPart + otherPart;
		 }
		return attrName;
	}
	
	/**
	 * 获取记录条数
	 * @param sql
	 * @param params
	 * @return
	 */
	public Integer getCountBy(String sql,Map<String, Object> params){
		Query query =  entityManager.createNativeQuery(sql);
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		BigInteger bigInteger  = (BigInteger) query.getSingleResult();
		return bigInteger.intValue();
	}
	
	/**
	 * 新增或者删除
	 * @param sql
	 * @param params
	 * @return
	 */
	public Integer deleteOrUpDate(String sql,Map<String, Object> params){
		Query query =  entityManager.createNativeQuery(sql);
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.executeUpdate();
	}
}