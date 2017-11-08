package com.postss.common.extend.data.jpa.repository.support;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * 如果有排序但不是主键排序，强制增加末尾主键排序
 * @author jwSun
 * @date 2017年6月9日 下午2:55:55
 */
public class DefaultJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> {

    public DefaultJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    public DefaultJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    protected TypedQuery<T> getQuery(Specification<T> spec, Pageable pageable) {
        Sort sort = pageable == null ? null : pageable.getSort();
        if (sort != null) {
            Field[] fields = getDomainClass().getDeclaredFields();
            for (Field field : fields) {
                Id id = field.getDeclaredAnnotation(Id.class);
                if (id != null) {
                    Order order = sort.getOrderFor(field.getName());
                    if (order == null) {
                        sort = sort.and(new Sort(field.getName()));
                    }
                    break;
                }
            }
        }

        return getQuery(spec, sort);
    }

}
