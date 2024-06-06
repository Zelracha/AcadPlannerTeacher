package com.example.academicplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.academicplanner.ui.settings.SettingsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    EditText profileUsername_txt, profileName_txt, profileEmail_txt,  profilePassword_txt;
    Button profileback_btn2, profileUpdate_btn;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        profileUsername_txt = findViewById(R.id.profileUsername_txt );
        profileName_txt  = findViewById(R.id.profileName_txt );
        profileEmail_txt  = findViewById(R.id.profileEmail_txt );
        profilePassword_txt = findViewById(R.id. profilePassword_txt );
        profileUpdate_btn = findViewById(R.id.profileUpdate_btn);
        profileback_btn2= findViewById(R.id.profileback_btn2);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        profileUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmProfileDialog();
                String oldUsername = profileUsername_txt.getText().toString(); // Retrieve the old username
                String newUsername = profileUsername_txt.getText().toString();
                String newName = profileName_txt.getText().toString();
                String newEmail = profileEmail_txt.getText().toString();
                String newPassword = profilePassword_txt.getText().toString();

                // Step 1: Retrieve the current user's data
                reference.child(oldUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Step 2: Delete the old entry
                        dataSnapshot.getRef().removeValue();

                        // Step 3: Create a new entry with the new username
                        DatabaseReference newUserRef = reference.child(newUsername);
                        newUserRef.child("name").setValue(newName);
                        newUserRef.child("email").setValue(newEmail);
                        newUserRef.child("username").setValue(newUsername);
                        newUserRef.child("password").setValue(newPassword);

                        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Username", newUsername);
                        editor.putString("Name", newName);
                        editor.putString("Email", newEmail);
                        editor.putString("Password", newPassword);
                        editor.apply();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        profileback_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "Username");
        String name = sharedPreferences.getString("Name", "Name");
        String email = sharedPreferences.getString("Email", "Email");
        String password = sharedPreferences.getString("Password", "Password");

        profileUsername_txt.setText(username);
        profileName_txt.setText(name);
        profileEmail_txt.setText(email);
        profilePassword_txt .setText(password);
    }
    void confirmProfileDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update profile ?");
        builder.setMessage("Are you sure you want to update this profile ? \n This cannot be undone.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                finish();
                Toast.makeText(UpdateProfile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
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
