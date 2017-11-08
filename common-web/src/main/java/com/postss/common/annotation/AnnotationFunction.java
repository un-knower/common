package com.postss.common.annotation;

public interface AnnotationFunction<T, K> {

    public K resolveAnnotation(T t);

}
