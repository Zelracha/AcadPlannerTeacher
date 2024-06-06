package com.example.academicplanner.ui.calendar;

import static com.example.academicplanner.CalendarUtils.daysInMonthArray;
import static com.example.academicplanner.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.academicplanner.CalendarAdapter;
import com.example.academicplanner.CalendarUtils;
import com.example.academicplanner.R;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        initWidgets(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = LocalDate.now();
        }
        setMonthView();

        Button previousMonthButton = view.findViewById(R.id.previousMonthButton);
        Button nextMonthButton = view.findViewById(R.id.nextMonthButton);
        Button newEventButton = view.findViewById(R.id.newEventButton);

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                previousMonthAction();
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                nextMonthAction();
            }
        });

        return view;
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        ((CalendarAdapter) calendarRecyclerView.getAdapter()).setSelectedPosition(-1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        ((CalendarAdapter) calendarRecyclerView.getAdapter()).setSelectedPosition(-1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setMonthView();
    }
}
