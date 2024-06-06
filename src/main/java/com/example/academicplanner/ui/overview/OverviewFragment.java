package com.example.academicplanner.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.academicplanner.R;
import com.example.academicplanner.ui.agenda.AgendaFragment;
import com.example.academicplanner.ui.calendar.CalendarFragment;
import com.example.academicplanner.ui.subjects.SubjectsFragment;
import com.example.academicplanner.ui.teachers.TeachersFragment;
import com.example.academicplanner.ui.timetable.TimetableFragment;

public class OverviewFragment extends Fragment implements View.OnClickListener {

    private Button btnAgenda;
    private Button btnCalendar;
    private Button btnTimetable;
    private Button btnSubjects;
    private Button btnTeachers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        btnAgenda = view.findViewById(R.id.btnAgenda);
        btnCalendar = view.findViewById(R.id.btnCalendar);
        btnTimetable = view.findViewById(R.id.btnTimetable);
        btnSubjects = view.findViewById(R.id.btnSubjects);
        btnTeachers = view.findViewById(R.id.btnTeachers);

        btnAgenda.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);
        btnTimetable.setOnClickListener(this);
        btnSubjects.setOnClickListener(this);
        btnTeachers.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        String title = "";

        if (v.getId() == R.id.btnAgenda) {
            fragment = new AgendaFragment();
            title = "Agenda";
        } else if (v.getId() == R.id.btnCalendar) {
            fragment = new CalendarFragment();
            title = "Calendar";
        } else if (v.getId() == R.id.btnTimetable) {
            fragment = new TimetableFragment();
            title = "Timetable";
        } else if (v.getId() == R.id.btnSubjects) {
            fragment = new SubjectsFragment();
            title = "Subjects";
        } else if (v.getId() == R.id.btnTeachers) {
            fragment = new TeachersFragment();
            title = "Teachers";
        }

        if (fragment != null) {
            replaceFragment(fragment);
            updateToolbarTitle(title);

            btnAgenda.setVisibility(View.GONE);
            btnCalendar.setVisibility(View.GONE);
            btnTimetable.setVisibility(View.GONE);
            btnSubjects.setVisibility(View.GONE);
            btnTeachers.setVisibility(View.GONE);
        }
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.overview_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void updateToolbarTitle(String title) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle(title);
            }
        }
    }

    public void showButtons() {
        btnAgenda.setVisibility(View.VISIBLE);
        btnCalendar.setVisibility(View.VISIBLE);
        btnTimetable.setVisibility(View.VISIBLE);
        btnSubjects.setVisibility(View.VISIBLE);
        btnTeachers.setVisibility(View.VISIBLE);
    }
}