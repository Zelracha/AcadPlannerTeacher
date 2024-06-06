package com.example.academicplanner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubjectsAdd extends AppCompatActivity implements ColorPicker.OnColorSelectedListener, TeachersPicker.TeachersSelectedListener, TermsPicker.TermsSelectedListener {

    private EditText nameInput, noteInput;
    private Button add_subject, backButton;
    private TextView pickColorTextView, addTeacherTextView, pickTermTextView;

    private int selectedColor;
    private List<String> selectedTeachers = new ArrayList<>();
    private List<String> selectedTerms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_add);

        nameInput = findViewById(R.id.name_input);
        noteInput = findViewById(R.id.note_input);
        add_subject = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);
        pickColorTextView = findViewById(R.id.color_picker);
        addTeacherTextView = findViewById(R.id.teacher_picker);
        pickTermTextView = findViewById(R.id.terms_picker);

        addTeacherTextView.setOnClickListener(v -> openTeacherPickerBottomSheet());
        pickTermTextView.setOnClickListener(v -> openTermPickerBottomSheet());

        add_subject.setOnClickListener(v -> {
            String subjectName = nameInput.getText().toString().trim();
            if (subjectName.isEmpty()) {
                nameInput.setError("Missing subject name");
                nameInput.requestFocus();
                return;
            }

            DBHelper myDB = new DBHelper(SubjectsAdd.this);
            myDB.addSubject(subjectName, noteInput.getText().toString(), String.valueOf(selectedColor),
                    selectedTeachers.toString(), selectedTerms.toString());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("subjectName", subjectName);
            resultIntent.putExtra("subjectNote", noteInput.getText().toString());
            resultIntent.putExtra("subjectColor", selectedColor);
            resultIntent.putExtra("selectedTeachers", (Serializable) selectedTeachers);
            resultIntent.putExtra("selectedTerms", (Serializable) selectedTerms);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        backButton.setOnClickListener(v -> finish());

        pickColorTextView.setOnClickListener(v -> {
            ColorPicker colorPicker = new ColorPicker();
            colorPicker.setOnColorSelectedListener(this);
            colorPicker.show(getSupportFragmentManager(), "colorPicker");
        });
    }

    private void openTeacherPickerBottomSheet() {
        TeachersPicker bottomSheetFragment = TeachersPicker.newInstance(selectedTeachers);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void openTermPickerBottomSheet() {
        TermsPicker bottomSheetFragment = TermsPicker.newInstance(selectedTerms);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onColorSelected(int color) {
        selectedColor = color;
        Drawable drawable = pickColorTextView.getCompoundDrawablesRelative()[0];
        if (drawable != null) {
            Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(wrappedDrawable, color);
            pickColorTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(wrappedDrawable, null, null, null);
        }
    }

    @Override
    public void onTeachersSelected(List<String> selectedTeachers) {
        this.selectedTeachers = selectedTeachers;
        updateTeacherTextView();
    }

    @Override
    public void onTermsSelected(List<String> selectedTerms) {
        this.selectedTerms = selectedTerms;
        updateTermTextView();
    }

    private void updateTeacherTextView() {
        if (selectedTeachers.isEmpty()) {
            addTeacherTextView.setText("Add a teacher");
        } else {
            StringBuilder teachersStringBuilder = new StringBuilder();
            for (int i = 0; i < selectedTeachers.size(); i++) {
                teachersStringBuilder.append(selectedTeachers.get(i));
                if (i != selectedTeachers.size() - 1) {
                    teachersStringBuilder.append(", ");
                }
            }
            addTeacherTextView.setText(teachersStringBuilder.toString());
        }
    }

    private void updateTermTextView() {
        if (selectedTerms.isEmpty()) {
            pickTermTextView.setText("Choose terms (optional)");
        } else {
            StringBuilder termStringBuilder = new StringBuilder();
            for (int i = 0; i < selectedTerms.size(); i++) {
                termStringBuilder.append(selectedTerms.get(i));
                if (i != selectedTerms.size() - 1) {
                    termStringBuilder.append(", ");
                }
            }
            pickTermTextView.setText(termStringBuilder.toString());
        }
    }
}