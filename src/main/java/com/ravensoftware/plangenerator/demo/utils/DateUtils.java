package com.ravensoftware.plangenerator.demo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by bilga on 31-05-2021
 */
public class DateUtils {

    public static String PATTERN_DD_MM_YYYY = "dd.MM.yyyy";


    public static LocalDate convertStringToLocalDate(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }

    public static String convertLocalDateToString(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    public static String getNextMonthToString(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = convertStringToLocalDate(date, pattern);
        return localDate.plusMonths(1).format(formatter);
    }

}
