package com.postss.common.system.exception;

public class BeanNotFindException extends SystemException {

    private static final long serialVersionUID = 1L;

    public BeanNotFindException(String beanName) {
        super("cant find bean:" + beanName + ",please check");
    }

    public BeanNotFindException(String beanName, Class<?> clazz) {
        super("find bean:" + beanName + " but cant cast to " + clazz);
    }

}
