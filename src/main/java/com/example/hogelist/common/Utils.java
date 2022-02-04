package com.example.hogelist.common;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Utils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // String → SQL.Date
    public static Date strDate(String s) {
        long ms = 0;
        try {
            ms = sdf.parse(s).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(ms);
    }

    // LocalDate → int
    public static int birthday(LocalDate bornDay) {
        LocalDate birthday = bornDay;
        LocalDate now = LocalDate.now();
        long age = ChronoUnit.YEARS.between(birthday, now);
        return (int) age;
    }

    // 半角スペース、タブならtrue
    public static boolean isBlank(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ' && s.charAt(i) != '\t') {
                return false;
            }
        }
        return true;
    }

    // 全角スペースならtrue
    public static boolean isDoubleSpace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '　' && s.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    // String → LocalDateにparseできればtrue
    public static boolean isValidDateFormat(String s) {
            try {
                LocalDate.parse(s);
            } catch (DateTimeException e) {
                return false;
            }
        return true;
    }

    // 今日以降ならtrue
    public static boolean isTodayOrFurtureDate(String s) {
        try {
            LocalDate now = LocalDate.now();
            LocalDate ld = LocalDate.parse(s);
            if (ld.isAfter(now)) {
                return true;
            } else {
                return false;
            }
        } catch (DateTimeException e) {
            return false;
        }
    }
}