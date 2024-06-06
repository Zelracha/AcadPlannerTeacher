package com.example.academicplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TeachersAdd extends AppCompatActivity {
    private static final String TAG = "TeachersAdd";
    private static final int PICK_IMAGE = 1;

    EditText name_input, surname_input, phone_input, email_input;
    Button add_teacher, back_button;
    public ImageView pic_input;
    public Uri selectedImageUri;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_add);

        Log.d(TAG, "onCreate: started");

        name_input = findViewById(R.id.name_input);
        surname_input = findViewById(R.id.surname_input);
        phone_input = findViewById(R.id.phone_input);
        email_input = findViewById(R.id.email_input);
        add_teacher = findViewById(R.id.save_button);
        back_button = findViewById(R.id.back_button);
        pic_input = findViewById(R.id.pic_input);

        add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "add_teacher: clicked");
                if (name_input.getText().toString().trim().isEmpty()) {
                    name_input.setError("Missing teacher name");
                    name_input.requestFocus();
                    Log.d(TAG, "add_teacher: missing teacher name");
                } else if (surname_input.getText().toString().trim().isEmpty()) {
                    surname_input.setError("Missing teacher surname");
                    surname_input.requestFocus();
                    Log.d(TAG, "add_teacher: missing teacher surname");
                } else {
                    try {
                        DBHelper myDB = new DBHelper(TeachersAdd.this);
                        String phoneNumberString = phone_input.getText().toString().trim();
                        Long phoneNumber = phoneNumberString.isEmpty() ? null : Long.valueOf(phoneNumberString);

                        myDB.addTeacher(
                                name_input.getText().toString().trim(),
                                surname_input.getText().toString().trim(),
                                phoneNumber,
                                email_input.getText().toString().trim(),
                                selectedImageUri != null ? selectedImageUri.toString() : null
                        );
                        Log.d(TAG, "add_teacher: added teacher to database");
                        finish();
                    } catch (Exception e) {
                        Log.e(TAG, "add_teacher: exception occurred", e);
                    }
                }
            }
        });

        pic_input.setOnClickListener(v -> {
            Log.d(TAG, "pic_input: clicked to select image");
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE);
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "back_button: clicked");
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            pic_input.setImageURI(selectedImageUri);
            Log.d(TAG, "onActivityResult: image selected, URI=" + selectedImageUri);
        } else {
            Log.d(TAG, "onActivityResult: image selection failed or canceled");
        }
    }
}

