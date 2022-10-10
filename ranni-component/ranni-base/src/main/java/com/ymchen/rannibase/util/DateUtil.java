package com.ymchen.rannibase.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }

    public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant2 = date.toInstant();
        return LocalDateTime.ofInstant(instant2, zone);
    }

    public static Date format(String timeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(timeStr));
        return toDate(localDateTime);
    }

    public static String parse(Date date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.format(formatter);
    }
}
