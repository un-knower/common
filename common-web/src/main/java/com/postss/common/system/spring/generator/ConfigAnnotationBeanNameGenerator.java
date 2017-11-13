package com.postss.common.system.spring.generator;

import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import com.postss.common.base.annotation.AutoConfig;

@AutoConfig
public class ConfigAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    private static final String COMPONENT_ANNOTATION_PACKAGENAME = "com.postss.common.base.annotation";
    private static final String COMPONENT_ANNOTATION_CLASSNAME_AUTO = COMPONENT_ANNOTATION_PACKAGENAME + ".AutoConfig";
    private static final String COMPONENT_ANNOTATION_CLASSNAME_ROOT = COMPONENT_ANNOTATION_PACKAGENAME
            + ".AutoRootApplicationConfig";
    private static final String COMPONENT_ANNOTATION_CLASSNAME_WEB = COMPONENT_ANNOTATION_PACKAGENAME
            + ".AutoWebApplicationConfig";
    private static final String COMPONENT_ANNOTATION_CLASSNAME_PROXY = COMPONENT_ANNOTATION_PACKAGENAME
            + "com.postss.common.extend.org.springframework.context.config.AutoProxyApplicationConfig";

    protected boolean isStereotypeWithNameValue(String annotationType, Set<String> metaAnnotationTypes,
            Map<String, Object> attributes) {

        boolean isStereotype = annotationType.equals(COMPONENT_ANNOTATION_CLASSNAME_ROOT)
                || annotationType.equals(COMPONENT_ANNOTATION_CLASSNAME_WEB)
                || annotationType.equals(COMPONENT_ANNOTATION_CLASSNAME_PROXY)
                || annotationType.equals(COMPONENT_ANNOTATION_CLASSNAME_AUTO)
                || (metaAnnotationTypes != null && metaAnnotationTypes.contains(COMPONENT_ANNOTATION_CLASSNAME_AUTO));

        boolean result = (isStereotype && attributes != null && attributes.containsKey("value"));
        if (!result) {
            return super.isStereotypeWithNameValue(annotationType, metaAnnotationTypes, attributes);
        } else {
            return result;
        }

    }

}
