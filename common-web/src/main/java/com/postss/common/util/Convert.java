package com.postss.common.util;

public interface Convert<T> {

    public T warp(Object obj);

    public T getByByte(Byte bytes);

    public T getByShort(Short shorts);

    public T getByInteger(Integer integer);

    public T getByString(String string);

    public T getByLong(Long longV);

    public T getByDouble(Double doubles);

    public T getByChar(char chars);

    public T getByFloat(Float floats);

}
