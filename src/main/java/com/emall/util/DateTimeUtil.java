package com.emall.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Harry Chou
 * @date 2019/6/15
 */
public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date str2Date(String dateTimeStr, String formatStr) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(formatStr);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, pattern);
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public static String date2Str(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(formatStr);
        LocalDateTime localDateTime = date.toInstant().atZone(zoneId).toLocalDateTime();
        return localDateTime.format(pattern);
    }

    public static Date str2Date(String dateTimeStr) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(STANDARD_FORMAT);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, pattern);
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public static String date2Str(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(STANDARD_FORMAT);
        LocalDateTime localDateTime = date.toInstant().atZone(zoneId).toLocalDateTime();
        return localDateTime.format(pattern);
    }

}
