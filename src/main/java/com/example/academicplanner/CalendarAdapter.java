package com.example.academicplanner;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private int selectedPosition = -1;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() / 6.0);
        return new CalendarViewHolder(view, onItemListener, days);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = days.get(position);
        holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

        String holidayName = HolidayCalendar.getHolidayName(date);
        if (holidayName != null) {
            holder.holidayText.setText(holidayName);
            holder.holidayText.setVisibility(View.VISIBLE);
            holder.holidayText.setBackgroundColor(Color.YELLOW);
        } else {
            holder.holidayText.setVisibility(View.GONE);
        }

        if (date.equals(LocalDate.now())) {
            holder.dayOfMonth.setBackgroundResource(R.drawable.circle_background);
            holder.dayOfMonth.setTextColor(Color.WHITE);
        } else {
            holder.dayOfMonth.setBackgroundResource(0);
            if (date.getMonth().equals(CalendarUtils.selectedDate.getMonth())) {
                holder.dayOfMonth.setTextColor(Color.BLACK);
            } else {
                holder.dayOfMonth.setTextColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }
}
