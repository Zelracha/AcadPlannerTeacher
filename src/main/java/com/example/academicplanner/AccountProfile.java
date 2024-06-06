package com.example.academicplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountProfile extends AppCompatActivity {

    TextView profileUsername, profileName, profileEmail;
    Button profileEdit_btn, profileback_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);

        profileUsername = findViewById(R.id.profileUsername);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);

        profileEdit_btn = findViewById(R.id.profileEdit_btn);
        profileback_btn = findViewById(R.id.profileback_btn);

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "Username");
        String name = sharedPreferences.getString("Name", "Name");
        String email = sharedPreferences.getString("Email", "Email");

        profileUsername.setText(username);
        profileName.setText(name);
        profileEmail.setText(email);

        profileEdit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountProfile.this, UpdateProfile.class);
                startActivity(i);
            }
        });

        profileback_btn.setOnClickListener(new View.OnClickListener() {
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

        profileUsername.setText(username);
        profileName.setText(name);
        profileEmail.setText(email);
    }
}
