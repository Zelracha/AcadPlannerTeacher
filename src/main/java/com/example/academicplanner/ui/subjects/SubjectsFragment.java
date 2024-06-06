package com.example.academicplanner.ui.subjects;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.academicplanner.R;
import com.example.academicplanner.SubjectsAdapter;
import com.example.academicplanner.SubjectsAdd;
import com.example.academicplanner.DBHelper;
import java.util.ArrayList;
import java.util.List;

public class SubjectsFragment extends Fragment {

    private RecyclerView subject_recyclerView;
    private SubjectsAdapter adapter;
    private List<Subject> subjectsList;
    private View view;

    public SubjectsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subjects, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subject_recyclerView = view.findViewById(R.id.subject_recyclerView);
        view.findViewById(R.id.add_subject_button).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SubjectsAdd.class);
            startActivityForResult(intent, 1);
        });

        subjectsList = new ArrayList<>();
        adapter = new SubjectsAdapter(subjectsList);
        subject_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subject_recyclerView.setAdapter(adapter);

        storeSubjectData();
        updateUI();
    }

    private void updateUI() {
        if (subjectsList.isEmpty()) {
            subject_recyclerView.setVisibility(View.GONE);
            view.findViewById(R.id.empty_subjectview).setVisibility(View.VISIBLE);
            view.findViewById(R.id.no_subject_data).setVisibility(View.VISIBLE);
            view.findViewById(R.id.no_subject_data_details).setVisibility(View.VISIBLE);
        } else {
            subject_recyclerView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.empty_subjectview).setVisibility(View.GONE);
            view.findViewById(R.id.no_subject_data).setVisibility(View.GONE);
            view.findViewById(R.id.no_subject_data_details).setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            int subjectId = data.getIntExtra("subjectId", 0);
            String subjectName = data.getStringExtra("subjectName");
            String subjectNote = data.getStringExtra("subjectNote");
            int subjectColor = data.getIntExtra("subjectColor", 0);
            String subjectTeachers = data.getStringExtra("selectedTeachers");
            String subjectTerms = data.getStringExtra("selectedTerms");
            addSubject(subjectId,subjectName, subjectNote, subjectColor, subjectTeachers, subjectTerms);
            storeSubjectData();
        }
    }

    void addSubject(int subjectId, String subjectName, String subjectNote, int subjectColor, String subjectTeachers, String subjectTerms) {
        Subject newSubject = new Subject(subjectId, subjectName, subjectNote, subjectColor, subjectTeachers, subjectTerms);
        subjectsList.add(newSubject);
        adapter.notifyItemInserted(subjectsList.size() - 1);
        updateUI();
    }

    void storeSubjectData() {
        DBHelper dbHelper = new DBHelper(getContext());
        Cursor cursor = dbHelper.readSubjectData();

        subjectsList.clear();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int subjectId = cursor.getInt(cursor.getColumnIndex(DBHelper.SUBJECT_ID));
                @SuppressLint("Range") String subjectName = cursor.getString(cursor.getColumnIndex(DBHelper.SUBJECT_NAME));
                @SuppressLint("Range") String subjectNote = cursor.getString(cursor.getColumnIndex(DBHelper.SUBJECT_NOTE));
                @SuppressLint("Range") int subjectColor = cursor.getInt(cursor.getColumnIndex(DBHelper.SUBJECT_COLOR));
                @SuppressLint("Range") String subjectTeachers = cursor.getString(cursor.getColumnIndex(DBHelper.SELECTED_TEACHERS));
                @SuppressLint("Range") String subjectTerms = cursor.getString(cursor.getColumnIndex(DBHelper.SELECTED_TERMS));
                subjectsList.add(new SubjectsFragment.Subject(subjectId, subjectName, subjectNote, subjectColor, subjectTeachers, subjectTerms));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        adapter.notifyDataSetChanged();
        updateUI();
    }

    public static class Subject {
        private int id;
        private String name;
        private String note;
        private int color;
        private String teachers;
        private String terms;

        public Subject(int id, String name, String note, int color, String teachers, String terms) {
            this.id = id;
            this.name = name;
            this.note = note;
            this.color = color;
            this.teachers = teachers;
            this.terms = terms;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id= id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getTeachers() {
            return teachers;
        }

        public void setTeachers(String teachers) {
            this.teachers = teachers;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }
    }

}