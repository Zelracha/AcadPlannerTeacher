package com.example.academicplanner.ui.teachers;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academicplanner.TeacherAdapter;
import com.example.academicplanner.DBHelper;
import com.example.academicplanner.R;
import com.example.academicplanner.TeachersAdd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TeachersFragment extends Fragment {
    private static final String TAG = "TeachersFragment";

    RecyclerView teacher_recyclerView;
    FloatingActionButton add_button;
    ImageView empty_teacherview;
    TextView no_teacher_data, no_teacher_data_details;

    DBHelper myDB;
    ArrayList<String> teacher_id, teacher_name, teacher_surname, teacher_phone, teacher_email, teacher_pic_blob;
    TeacherAdapter teacherAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        View view = inflater.inflate(R.layout.fragment_teachers, container, false);

        try {
            teacher_recyclerView = view.findViewById(R.id.teacher_recyclerView);
            empty_teacherview = view.findViewById(R.id.empty_teacherview);
            no_teacher_data_details = view.findViewById(R.id.no_teacher_data_details);
            no_teacher_data = view.findViewById(R.id.no_teacher_data);
            add_button = view.findViewById(R.id.add_teacher_button);

            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: add_button clicked");
                    Intent intent = new Intent(getActivity(), TeachersAdd.class);
                    startActivity(intent);
                }
            });

            myDB = new DBHelper(getActivity());
            teacher_id = new ArrayList<>();
            teacher_name = new ArrayList<>();
            teacher_surname = new ArrayList<>();
            teacher_phone = new ArrayList<>();
            teacher_email = new ArrayList<>();
            teacher_pic_blob = new ArrayList<>();

            teacherAdapter = new TeacherAdapter(getActivity(), getContext(), teacher_id, teacher_name,
                    teacher_surname, teacher_phone, teacher_email, teacher_pic_blob);
            teacher_recyclerView.setAdapter(teacherAdapter);
            teacher_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            storeTeacherData();
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: Exception occurred", e);
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d(TAG, "onActivityResult: requestCode == 1, recreating activity");
            getActivity().recreate();
        }
    }

    void storeTeacherData() {
        Log.d(TAG, "storeTeacherData: fetching data from database");
        Cursor cursor = null;
        try {
            cursor = myDB.readTeacherData();
            if (cursor == null) {
                Log.e(TAG, "storeTeacherData: cursor is null");
                return;
            }
            if (cursor.getCount() == 0) {
                Log.d(TAG, "storeTeacherData: no data found");
                empty_teacherview.setVisibility(View.VISIBLE);
                no_teacher_data.setVisibility(View.VISIBLE);
                no_teacher_data_details.setVisibility(View.VISIBLE);
            } else {
                while (cursor.moveToNext()) {
                    teacher_id.add(cursor.getString(0));
                    teacher_name.add(cursor.getString(1));
                    teacher_surname.add(cursor.getString(2));
                    teacher_phone.add(cursor.getString(3));
                    teacher_email.add(cursor.getString(4));
                    teacher_pic_blob.add(cursor.getString(5));
                }
                empty_teacherview.setVisibility(View.GONE);
                no_teacher_data.setVisibility(View.GONE);
                no_teacher_data_details.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "storeTeacherData: error reading from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: clearing and re-fetching data");
        try {
            teacher_id.clear();
            teacher_name.clear();
            teacher_surname.clear();
            teacher_phone.clear();
            teacher_email.clear();
            teacher_pic_blob.clear();
            storeTeacherData();
            teacherAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "onResume: Exception occurred", e);
        }
    }
}