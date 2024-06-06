package com.example.academicplanner.ui.timetable;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.example.academicplanner.R;
import com.example.academicplanner.TimetableAdd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class TimetableFragment extends Fragment {

    private TextView daySunday, dayMonday, dayTuesday, dayWednesday, dayThursday, dayFriday, daySaturday;
    private TextView textSunday, textMonday, textTuesday, textWednesday, textThursday, textFriday, textSaturday;
    private ImageView previousWeek, nextWeek;
    private FloatingActionButton addTimetableButton;
    private Calendar calendar;
    private Calendar today;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

        initViews(view);

        calendar = Calendar.getInstance();
        today = Calendar.getInstance();

        setCurrentMonth(view);
        setDayOfWeek();
        setOnClickListeners();

        return view;
    }

    private void initViews(View view) {
        daySunday = view.findViewById(R.id.daySunday);
        dayMonday = view.findViewById(R.id.dayMonday);
        dayTuesday = view.findViewById(R.id.dayTuesday);
        dayWednesday = view.findViewById(R.id.dayWednesday);
        dayThursday = view.findViewById(R.id.dayThursday);
        dayFriday = view.findViewById(R.id.dayFriday);
        daySaturday = view.findViewById(R.id.daySaturday);

        textSunday = view.findViewById(R.id.textSunday);
        textMonday = view.findViewById(R.id.textMonday);
        textTuesday = view.findViewById(R.id.textTuesday);
        textWednesday = view.findViewById(R.id.textWednesday);
        textThursday = view.findViewById(R.id.textThursday);
        textFriday = view.findViewById(R.id.textFriday);
        textSaturday = view.findViewById(R.id.textSaturday);

        previousWeek = view.findViewById(R.id.previousWeek);
        nextWeek = view.findViewById(R.id.nextWeek);
        addTimetableButton = view.findViewById(R.id.add_timetable_button);
    }

    private void setCurrentMonth(View view) {
        TextView currentMonthTextView = view.findViewById(R.id.currentMonth);
        int currentMonth = calendar.get(Calendar.MONTH);
        String[] months = new DateFormatSymbols().getMonths();
        String currentMonthName = months[currentMonth];
        currentMonthTextView.setText(currentMonthName);
    }

    private void setDayOfWeek() {
        int currentDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        int currentMonth = today.get(Calendar.MONTH);
        int currentDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
        int currentYear = today.get(Calendar.YEAR);

        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, i);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            TextView dayTextView;
            TextView textTextView;
            switch (i) {
                case Calendar.SUNDAY:
                    dayTextView = daySunday;
                    textTextView = textSunday;
                    break;
                case Calendar.MONDAY:
                    dayTextView = dayMonday;
                    textTextView = textMonday;
                    break;
                case Calendar.TUESDAY:
                    dayTextView = dayTuesday;
                    textTextView = textTuesday;
                    break;
                case Calendar.WEDNESDAY:
                    dayTextView = dayWednesday;
                    textTextView = textWednesday;
                    break;
                case Calendar.THURSDAY:
                    dayTextView = dayThursday;
                    textTextView = textThursday;
                    break;
                case Calendar.FRIDAY:
                    dayTextView = dayFriday;
                    textTextView = textFriday;
                    break;
                case Calendar.SATURDAY:
                    dayTextView = daySaturday;
                    textTextView = textSaturday;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }

            dayTextView.setText(String.valueOf(dayOfMonth));

            if (year == currentYear && month == currentMonth && dayOfMonth == currentDayOfMonth && i == currentDayOfWeek) {
                Drawable circle = ContextCompat.getDrawable(requireContext(), R.drawable.circle_current_day);
                dayTextView.setBackground(circle);
                dayTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
                textTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
            } else {
                dayTextView.setBackground(null);
                dayTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
                textTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
            }
        }

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    }

    private void setOnClickListeners() {
        previousWeek.setOnClickListener(v -> {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            setCurrentMonth(getView());
            setDayOfWeek();
        });

        nextWeek.setOnClickListener(v -> {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            setCurrentMonth(getView());
            setDayOfWeek();
        });

        addTimetableButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), TimetableAdd.class);
            startActivity(intent);
        });
    }
}
