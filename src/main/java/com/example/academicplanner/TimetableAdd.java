package com.example.academicplanner;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TimetableAdd extends AppCompatActivity {

    private TextView fromTimeTextView;
    private TextView toTimeTextView;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private int previousFromHour = 9;
    private int previousFromMinute = 0;
    private int previousToHour = 10;
    private int previousToMinute = 0;
    private String amPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_add);

        fromTimeTextView = findViewById(R.id.from_time_picker);
        toTimeTextView = findViewById(R.id.to_time_picker);

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        updateTime(previousFromHour, previousFromMinute, true);
        updateTime(previousToHour, previousToMinute, false);

        fromTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(true);
            }
        });

        toTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(false);
            }
        });

        Button backButton = findViewById(R.id.timetable_back_button);
        backButton.setOnClickListener(v -> finish());

        Button saveButton = findViewById(R.id.timetable_save_button);
        saveButton.setOnClickListener(v -> finish());
    }

    private void showTimePicker(boolean isFromTime) {
        int hourToShow;
        int minuteToShow;
        if (isFromTime) {
            hourToShow = previousFromHour;
            minuteToShow = previousFromMinute;
        } else {
            hourToShow = previousToHour;
            minuteToShow = previousToMinute;
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(TimetableAdd.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (isFromTime) {
                            previousFromHour = hourOfDay;
                            previousFromMinute = minute;
                        } else {
                            previousToHour = hourOfDay;
                            previousToMinute = minute;
                        }
                        updateTime(hourOfDay, minute, isFromTime);
                    }
                }, hourToShow, minuteToShow, false);
        timePickerDialog.show();
    }

    private void updateTime(int hourOfDay, int minute, boolean isFromTime) {
        if (hourOfDay >= 12) {
            amPm = "PM";
        } else {
            amPm = "AM";
        }

        int hour = hourOfDay % 12;
        if (hour == 0) {
            hour = 12;
        }

        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);

        String time = formattedHour + ":" + formattedMinute + " " + amPm;

        if (isFromTime) {
            fromTimeTextView.setText(time);
        } else {
            toTimeTextView.setText(time);
        }
    }
}
