package com.example.academicplanner;

import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HolidayCalendar {
    private LocalDate date;
    private String name;

    public HolidayCalendar(LocalDate date, String name) {
        this.date = date;
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public static List<HolidayCalendar> getHolidays() {
        List<HolidayCalendar> holidays = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holidays.add(new HolidayCalendar(LocalDate.of(2025, 1, 1), "New Year"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 3, 28), "Maundy Thursday"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 3, 29), "Good Friday"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 4, 9), "Araw ng Kagitingan"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 5, 1), "Labor Day"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 6, 12), "Independence Day"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 8, 26), "National Heroes Day"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 11, 30), "Bonifacio Day"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 12, 25), "Christmas"));
            holidays.add(new HolidayCalendar(LocalDate.of(2024, 12, 30), "Rizal Day"));
        }

        return holidays;
    }

    public static String getHolidayName(LocalDate date) {
        for (HolidayCalendar holiday : getHolidays()) {
            if (holiday.getDate().equals(date)) {
                return holiday.getName();
            }
        }
        return null;
    }
}
