package com.postss.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;

import com.postss.common.log.util.LoggerUtil;

/**
 * 日期工具类
 * <pre>
 * simpleDateFormat线程不安全,不能使用static创建调用
 * </pre>
 * @author jwSun
 * @date 2017年2月17日 下午7:22:46
 */
public class DateUtil {

    private static Logger log = LoggerUtil.getLogger(DateUtil.class);

    public static interface DATE {
        public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
        public static final String YYYY_MM_DD = "yyyy-MM-dd";
        public static final String PATTERN_YYYY_MM_DD = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[13579][26])00))-02-29)";
        public static final String PATTERN_YYYY_MM_DD_HH_MM = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d$";
        public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
    }

    /**
     * 获得当前日期YYYY_MM_DD
     * @author jwSun
     * @date 2017年2月27日 上午11:15:09
     * @return
     */
    public static String getDateNow() {
        return LocalDateTime.now().format(getDateTimeFormatter(DATE.YYYY_MM_DD));
    }

    /**
     * 获得当前日期YYYY_MM_DD_HH_MM_SS
     * @author jwSun
     * @date 2017年2月27日 上午11:15:09
     * @return
     */
    public static String getTimestampNow() {
        return LocalDateTime.now().format(getDateTimeFormatter(DATE.YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 获得当前日期YYYY_MM_DD
     * @author jwSun
     * @date 2017年2月27日 上午11:15:09
     * @return
     */
    public static String format(Date date) {
        return getDateFormat(DATE.YYYY_MM_DD_HH_MM_SS).format(date);
    }

    /**
     * 获得当前日期YYYY_MM_DD
     * @author jwSun
     * @date 2017年2月27日 上午11:15:09
     * @return
     */
    public static String format(Date date, String pattern) {
        if (!StringUtil.notEmpty(pattern)) {
            throw new RuntimeException("pattern cant be null");
        }
        return getDateFormat(pattern).format(date);
    }

    public static Date parse(String date) throws ParseException {
        return getDateFormat(null).parse(date);
    }

    public static Date parse(String date, String pattern) {
        try {
            return getDateFormat(pattern).parse(date);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static DateFormat getDateFormat(String pattern) {
        if (pattern == null || pattern == "" || pattern.equals(DATE.YYYY_MM_DD_HH_MM_SS)) {
            return new SimpleDateFormat(DATE.YYYY_MM_DD_HH_MM_SS);
        } else if (pattern.equals(DATE.YYYY_MM_DD)) {
            return new SimpleDateFormat(DATE.YYYY_MM_DD);
        } else {
            return new SimpleDateFormat(pattern);
        }
    }

    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        if (pattern == null || pattern == "" || pattern.equals(DATE.YYYY_MM_DD_HH_MM_SS)) {
            return new DateTimeFormatterFactory(DATE.YYYY_MM_DD_HH_MM_SS).createDateTimeFormatter();
        } else if (pattern.equals(DATE.YYYY_MM_DD)) {
            return new DateTimeFormatterFactory(DATE.YYYY_MM_DD).createDateTimeFormatter();
        } else {
            return new DateTimeFormatterFactory(pattern).createDateTimeFormatter();
        }
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

}