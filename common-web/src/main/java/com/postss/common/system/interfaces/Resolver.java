package com.postss.common.system.interfaces;

public interface Resolver<K, V> {

    public V resolve(K source);

}
