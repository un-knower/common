package com.postss.common.util;

import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.pojo.javassist.SerializableProxy;

public class HibernateUtil {

    /**
     * 懒加载是否已经加载完毕
     * @author jwSun
     * @date 2017年5月8日 下午4:18:46
     * @param obj
     * @return
     */
    public static boolean wasInitialized(Object obj) {
        //如果为PersistentSet类型,则判断是否已加载内容，没有则转化
        if (obj instanceof org.hibernate.collection.internal.PersistentSet) {
            PersistentSet persistentSet = (PersistentSet) obj;
            return persistentSet.wasInitialized();
        }
        //判断是否为HibernateProxy代理，如果是则没有加载，不转化
        if (obj instanceof HibernateProxy) {
            HibernateProxy proxy = (HibernateProxy) obj;
            //懒加载处理代理类为JavassistLazyInitializer, proxy.writeReplace()方法获得原始对象,相当于代理类中的target
            //获得的对象为SerializableProxy则表示还未从数据库获取,为所需对象则表示已从数据库获取过,可以进行转化
            if (proxy.writeReplace() instanceof SerializableProxy) {
                return false;
            }
        }
        return true;
    }

    public static void loadLazy(Object obj) {
        //如果为PersistentSet类型
        if (obj instanceof org.hibernate.collection.internal.PersistentSet) {
            obj.toString();
        }
        //判断是否为HibernateProxy代理
        if (obj instanceof HibernateProxy) {
            obj.toString();
        }
    }

}
