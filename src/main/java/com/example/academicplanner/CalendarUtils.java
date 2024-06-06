package com.example.academicplanner;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarUtils {
    public static LocalDate selectedDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String monthDayFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate selectedDate) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        dayOfWeek = dayOfWeek == 7 ? 0 : dayOfWeek; // Ensure Sunday is 0

        LocalDate prevMonth = selectedDate.minusMonths(1);
        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();

        for (int i = dayOfWeek; i > 0; i--) {
            daysInMonthArray.add(prevMonth.withDayOfMonth(prevDaysInMonth - i + 1));
        }

        for (int i = 1; i <= daysInMonth; i++) {
            daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i));
        }

        int nextMonthDays = 42 - daysInMonthArray.size();
        LocalDate nextMonth = selectedDate.plusMonths(1);
        for (int i = 1; i <= nextMonthDays; i++) {
            daysInMonthArray.add(nextMonth.withDayOfMonth(i));
        }

        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isHoliday(LocalDate date) {
        for (HolidayCalendar holiday : HolidayCalendar.getHolidays()) {
            if (holiday.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }
}
