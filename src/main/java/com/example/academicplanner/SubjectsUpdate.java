package com.example.academicplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.List;

public class SubjectsUpdate extends AppCompatActivity implements ColorPicker.OnColorSelectedListener, TeachersPicker.TeachersSelectedListener, TermsPicker.TermsSelectedListener{

    private EditText nameInput, noteInput;
    private Button updateButton, backButton, deleteButton;
    private TextView pickColorTextView, addTeacherTextView, pickTermTextView;

    private int selectedColor2;
    private List<String> selectedTeachers = new ArrayList<>();
    private List<String> selectedTerms = new ArrayList<>();
    String SubId, SubName, SubNote, SubColor, SubTeacher, SubTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_update);

        nameInput = findViewById(R.id.name_input2);
        noteInput = findViewById(R.id.note_input2);
        updateButton = findViewById(R.id.update_button);
        backButton = findViewById(R.id.back_button);
        pickColorTextView = findViewById(R.id.color_picker2);
        addTeacherTextView = findViewById(R.id.teacher_picker2);
        pickTermTextView = findViewById(R.id.terms_picker2);

        addTeacherTextView.setOnClickListener(v -> openTeacherPickerBottomSheet());
        pickTermTextView.setOnClickListener(v -> openTermPickerBottomSheet());

        getAndSetSubjectData();

        updateButton.setOnClickListener(v -> {
            if (nameInput.getText().toString().trim().isEmpty()) {
                nameInput.setError("Missing subject name");
                nameInput.requestFocus();
            }
            DBHelper myDB = new DBHelper(SubjectsUpdate.this);
            SubName = nameInput.getText().toString().trim();
            SubNote = noteInput.getText().toString().trim();
            String subcolor = String.valueOf(selectedColor2);
            String subTeachers = TextUtils.join(", ", selectedTeachers);
            String subTerms = TextUtils.join(", ", selectedTerms);
            myDB.updateSubjectData(SubId, SubName, SubNote, subcolor, subTeachers, subTerms);
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
        selectedColor2 = color;
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

    void getAndSetSubjectData(){
        if(getIntent().hasExtra("subjectId") && getIntent().hasExtra("subjectName") && getIntent().hasExtra("subjectNote")
                && getIntent().hasExtra("subjectColor") && getIntent().hasExtra("selectedTeachers")  && getIntent().hasExtra("selectedTerms")) {
            SubId = getIntent().getStringExtra("subjectId");
            SubName = getIntent().getStringExtra("subjectName");
            SubNote = getIntent().getStringExtra("subjectNote");
            SubColor = getIntent().getStringExtra("subjectColor");
            SubTeacher = getIntent().getStringExtra("selectedTeachers");
            SubTerm = getIntent().getStringExtra("selectedTerms");

            nameInput.setText(SubName);
            noteInput.setText(SubNote);
            pickColorTextView.setText(SubColor);
            addTeacherTextView.setText(SubTeacher);
            pickTermTextView.setText(SubTerm);
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmSubjectDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + SubName + " ?");
        builder.setMessage("Are you sure you want to delete " + SubName + " ?/nThis cannot be undone.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                DBHelper myDB = new DBHelper(SubjectsUpdate.this);
                myDB.deleteSubjectRow(SubId);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }
}