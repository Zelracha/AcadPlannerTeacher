package com.example.academicplanner;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final View parentView;
    public final TextView dayOfMonth;
    public final TextView holidayText;
    private final CalendarAdapter.OnItemListener onItemListener;
    private final ArrayList<LocalDate> days;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
        super(itemView);
        parentView = itemView;
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        holidayText = itemView.findViewById(R.id.cellHolidayText);
        this.onItemListener = onItemListener;
        this.days = days;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
