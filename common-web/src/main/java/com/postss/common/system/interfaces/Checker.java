package com.postss.common.system.interfaces;

public interface Checker<K, V> {

    public V check(K source, Object... args);

}
