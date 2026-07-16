package com.example.MoneyManagement.Util;

import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DISPLAY_DATE_PATTERN = "dd/MM/yyyy";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern(TIME_PATTERN);

    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DISPLAY_DATE_PATTERN);

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    public static String format(LocalDate date) {

        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }
    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }
    public static String format(LocalTime time) {
        if (time == null) {
            return null;
        }
        return time.format(TIME_FORMATTER);
    }
    public static String formatForDisplay(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DISPLAY_DATE_FORMATTER);
    }
    public static LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, DATE_FORMATTER);
    }
    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null || dateTime.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
    public static LocalTime parseTime(String time) {
        if (time == null || time.isBlank()) {
            return null;
        }
        return LocalTime.parse(time, TIME_FORMATTER);
    }
    public static LocalDateTime startOfDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atStartOfDay();
    }
    public static LocalDateTime endOfDay(LocalDate date) {

        if (date == null) {
            return null;
        }
        return date.atTime(LocalTime.MAX);
    }
    public static LocalDate addDays(LocalDate date, long days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }
    public static LocalDate subtractDays(LocalDate date, long days) {
        if (date == null) {
            return null;
        }
        return date.minusDays(days);
    }
    public static LocalDate addMonths(LocalDate date, long months) {

        if (date == null) {
            return null;
        }
        return date.plusMonths(months);
    }

    public static LocalDate subtractMonths(LocalDate date, long months) {
        if (date == null) {
            return null;
        }
        return date.minusMonths(months);
    }
    public static LocalDate addYears(LocalDate date, long years) {
        if (date == null) {
            return null;
        }
        return date.plusYears(years);
    }
    public static LocalDate subtractYears(LocalDate date, long years) {
        if (date == null) {
            return null;
        }
        return date.minusYears(years);
    }
    public static long daysBetween(LocalDate startDate,
                                   LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    public static long monthsBetween(LocalDate startDate,
                                     LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.MONTHS.between(
                YearMonth.from(startDate),
                YearMonth.from(endDate)
        );
    }
    public static long yearsBetween(LocalDate startDate,
                                    LocalDate endDate) {

        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.YEARS.between(startDate, endDate);
    }
    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }
    public static boolean isFuture(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
    public static boolean isLeapYear(int year) {
        return LocalDate.of(year, 1, 1).isLeapYear();
    }
    public static boolean isWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY ||
                day == DayOfWeek.SUNDAY;
    }
    public static int getAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
    public static String today() {
        return format(getCurrentDate());
    }
    public static String now() {
        return format(getCurrentDateTime());
    }
}