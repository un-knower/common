package com.postss.common.es.entity;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;

import com.alibaba.fastjson.JSONObject;
import com.postss.common.es.search.EsSearch;

public class EsSearchBuilder {

    public static <E extends EsSearch> DefaultEsSearchBuilder<E> buildEsSearch(Class<E> clazz, String index,
            String type) {
        return new DefaultEsBuilder<E>(new EsIndexEntity(index, type), clazz);
    }

    private interface SearchBuilder<X extends SearchBuilder<X, A>, A> {
        public X putQuerySet(String val);

        public X must(String key, String val);

        public X must(String key, String val, Boolean cover);

        public X should(String key, String val);

        public X should(String key, String val, Boolean cover);

        public X setMinimumNumberShouldMatch(int num);

        public A build();
    }

    public interface DefaultEsSearchBuilder<A> extends SearchBuilder<DefaultEsSearchBuilder<A>, A> {
        public Object get();
    }

    private static class BaseSearchEntity {
        protected SearchType searchType = SearchType.QUERY_THEN_FETCH;//默认查询类型,查出来的size与用户指定相同
        protected Map<String, Set<String>> mustMap = new HashMap<>();//必要查询内容
        protected Map<String, Set<String>> shouldMap = new HashMap<>();//需要查询内容(非必要)
        protected Integer minimumNumberShouldMatch;//should至少匹配数量
        protected Operator operator = Operator.AND;
        protected Set<String> queryKey = new HashSet<>();
        protected EsIndexEntity esIndexEntity;
        protected Class<? extends EsSearch> search;
    }

    private static class DefaultEsBuilder<A> extends BaseSearchEntity implements DefaultEsSearchBuilder<A> {

        /*public DefaultEsBuilder(EsIndexEntity esIndexEntity) {
            this.EsIndexEntity = esIndexEntity;
        }*/

        public DefaultEsBuilder(EsIndexEntity esIndexEntity, Class<? extends EsSearch> clazz) {
            this.esIndexEntity = esIndexEntity;
            this.search = clazz;
        }

        @Override
        public DefaultEsBuilder<A> putQuerySet(String val) {
            this.queryKey.add(val);
            return this;
        }

        @Override
        public DefaultEsBuilder<A> setMinimumNumberShouldMatch(int minimumNumberShouldMatch) {
            this.minimumNumberShouldMatch = minimumNumberShouldMatch;
            return this;
        }

        @Override
        public DefaultEsBuilder<A> must(String key, String val) {
            return this.must(key, val, Boolean.FALSE);
        }

        /**
         * 添加must条件
         * @author jwSun
         * @date 2017年4月24日 下午3:11:52
         * @param key 属性名
         * @param val 属性值
         * @param cover 是否覆盖
         * @return
         */
        @Override
        public DefaultEsBuilder<A> must(String key, String val, Boolean cover) {
            if (this.mustMap.get(key) == null || cover) {
                Set<String> set = new HashSet<String>();
                set.add(val);
                this.mustMap.put(key, set);
            } else {
                this.mustMap.get(key).add(val);
            }
            return this;
        }

        @Override
        public DefaultEsBuilder<A> should(String key, String val) {
            return this.should(key, val, Boolean.FALSE);
        }

        @Override
        public DefaultEsBuilder<A> should(String key, String val, Boolean cover) {
            if (this.shouldMap.get(key) == null || cover) {
                Set<String> set = new HashSet<String>();
                set.add(val);
                this.shouldMap.put(key, set);
            } else {
                this.shouldMap.get(key).add(val);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public A build() {
            try {
                Constructor<? extends EsSearch> con = search.getConstructor(String.class, String.class);
                if (con != null) {
                    EsSearch search = con.newInstance(esIndexEntity.getIndex(), esIndexEntity.getType());
                    search.mustAll(this.mustMap);
                    search.shouldAll(this.shouldMap);
                    search.setMinimumNumberShouldMatch(minimumNumberShouldMatch);
                    search.setOperator(operator);
                    search.setSearchType(searchType);
                    return (A) search;
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Object get() {
            return JSONObject.toJSONString(mustMap);
        }
    }

}
