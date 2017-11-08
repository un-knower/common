package com.postss.common.annotation.parameter;

import java.lang.annotation.Annotation;

import com.postss.common.util.Checker;
import com.postss.common.util.StringUtil;

public class NotEmptyChecker implements ParameterConfigChecker {

    @Override
    public Boolean check(Object source, Object... argName) {
        if (source instanceof String) {
            Checker.hasText(source, argName[0].toString());
        } else {
            Checker.notNull(source, argName[0].toString());
        }
        return true;
    }

    @Override
    public Boolean check(Object source, Annotation annotation, Object... args) {
        NotEmpty notempty = (NotEmpty) annotation;
        if (StringUtil.notEmpty(notempty.value())) {
            check(source, notempty.value());
        } else {
            check(source, args);
        }
        return true;
    }

}
