package com.postss.common.extend;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.DateUtil;
import com.postss.common.util.StringUtil;

public class DateConverter implements Converter<String, Date> {

    private Logger log = LoggerUtil.getLogger(getClass());

    @Override
    public Date convert(String source) {
        if (!StringUtil.notEmpty(source)) {
            return null;
        }
        if (StringUtil.getAllPatternMattcher(source, DateUtil.DATE.PATTERN_YYYY_MM_DD_HH_MM_SS, 0) != null) {
            return DateUtil.parse(source, DateUtil.DATE.YYYY_MM_DD_HH_MM_SS);
        }
        if (StringUtil.getAllPatternMattcher(source, DateUtil.DATE.PATTERN_YYYY_MM_DD_HH_MM, 0) != null) {
            return DateUtil.parse(source, DateUtil.DATE.YYYY_MM_DD_HH_MM);
        }
        if (StringUtil.getAllPatternMattcher(source, DateUtil.DATE.PATTERN_YYYY_MM_DD, 0) != null) {
            return DateUtil.parse(source, DateUtil.DATE.YYYY_MM_DD);
        }
        log.debug("cant convert :" + source);
        return null;
    }

}
