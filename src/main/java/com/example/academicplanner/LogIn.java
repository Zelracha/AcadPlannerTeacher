package com.example.academicplanner;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LogIn extends AppCompatActivity {

    EditText txtusername2;
    TextInputLayout txtpassword2;
    Button btnlogin2;
    TextView signupredirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        txtusername2 = (EditText) findViewById(R.id.txtusername2);
        txtpassword2 = (TextInputLayout) findViewById(R.id.txtpassword2);
        signupredirect = (TextView) findViewById(R.id.signupredirect);
        btnlogin2 = (Button) findViewById(R.id.btnlogin2);

        btnlogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()){
                }
                else{
                    checkUser();
                }
            }
        });

        signupredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this, SignUp.class);
                startActivity(i);
            }
        });
    }
    public boolean validateUsername(){
        String valuser = txtusername2.getText().toString();
        if(valuser.isEmpty()){
            txtusername2.setError("Missing Fields");
            return false;
        }
        else {
            txtusername2.setError(null);
            return true;
        }
    }

    public boolean validatePassword(){
        String valpass = txtpassword2.getEditText().toString();
        if(valpass.isEmpty()){
            txtpassword2.setError("Missing Fields");
            return false;
        }
        else {
            txtpassword2.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = txtusername2.getText().toString().trim();
        String userPassword = txtpassword2.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    txtusername2.setError(null);
                    String passFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if(Objects.equals(passFromDB, userPassword)){
                        txtusername2.setError(null);
                        Intent i = new Intent (LogIn.this, NavigationDrawer.class);
                        startActivity(i);
                    }
                    else {
                        txtpassword2.setError("Incorrect password");
                        txtpassword2.getEditText().requestFocus();
                    }
                }
                else{
                    txtusername2.setError("User does not exist");
                    txtpassword2.getEditText().requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}