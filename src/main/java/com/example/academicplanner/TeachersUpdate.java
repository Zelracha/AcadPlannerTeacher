package com.example.academicplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TeachersUpdate extends AppCompatActivity {
    EditText name_input, surname_input, phone_input, email_input;
    Button update_button, back_button2, delete_button;
    String id, name, surname, phone, email;
    ImageView pic_input, updatepic;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_update);

        name_input = findViewById(R.id.name_input2);
        surname_input = findViewById(R.id.surname_input2);
        phone_input = findViewById(R.id.phone_input2);
        email_input = findViewById(R.id.email_input2);
        pic_input = findViewById(R.id.updatepic);
        update_button = findViewById(R.id.update_button);
        back_button2 = findViewById(R.id.back_button2);
        delete_button = findViewById(R.id.delete_button);
        pic_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });


        getAndSetTeacherData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_input.getText().toString().trim().isEmpty()){
                    name_input.setError("Missing teacher name");
                    name_input.requestFocus();
                } else if (surname_input.getText().toString().trim().isEmpty()) {
                    surname_input.setError("Missing teacher surname");
                    surname_input.requestFocus();
                }
                DBHelper myDB = new DBHelper(TeachersUpdate.this);
                String phoneNumberString = phone_input.getText().toString().trim();
                Long phoneNumber = phoneNumberString.isEmpty() ? null : Long.valueOf(phoneNumberString);
                name = name_input.getText().toString().trim();
                surname = surname_input.getText().toString().trim();
                email = email_input.getText().toString().trim();

                myDB.updateTeacherData(id, name, surname, phoneNumber, email, selectedImageUri != null ? selectedImageUri.toString() : null);
                finish();
            }
        });

        back_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTeacherDialog();
            }
        });
    }
    void getAndSetTeacherData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("surname")
                && getIntent().hasExtra("phone") && getIntent().hasExtra("email")) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            surname = getIntent().getStringExtra("surname");
            phone = getIntent().getStringExtra("phone");
            email = getIntent().getStringExtra("email");


            name_input.setText(name);
            surname_input.setText(surname);
            phone_input.setText(phone);
            email_input.setText(email);
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmTeacherDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + surname + " ?");
        builder.setMessage("Are you sure you want to delete " + surname + " ?\nThis cannot be undone.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                DBHelper myDB = new DBHelper(TeachersUpdate.this);
                myDB.deleteTeacherRow(id);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            pic_input.setImageURI(selectedImageUri);
        }
    }

}
