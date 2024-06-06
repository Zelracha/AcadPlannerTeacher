package com.example.academicplanner;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import android.content.SharedPreferences;

public class SignUp extends AppCompatActivity {

    EditText txtname, txtemail, txtusername, txtpassword;
    TextView signinredirect;
    Button btnsignup2;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtname = (EditText) findViewById(R.id.txtname);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtusername = (EditText) findViewById(R.id.txtusername);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        signinredirect = (TextView) findViewById(R.id.signinredirect);
        btnsignup2 = (Button) findViewById(R.id.btnsignup2);

        btnsignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = txtname.getText().toString();
                String email = txtemail.getText().toString();
                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();

                reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(name.isEmpty()||email.isEmpty()||username.isEmpty()||password.isEmpty()) {
                            Toast.makeText(SignUp.this,"Missing Fields",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            HelperClass helperClass = new HelperClass(name, email, username, password);
                            reference.child(username).setValue(helperClass);

                            if (dataSnapshot.exists()) {
                                Toast.makeText(SignUp.this, "The username already exists", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Username", username);
                                editor.putString("Name", name);
                                editor.putString("Email", email);
                                editor.putString("Password", password);
                                editor.apply();

                                Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignUp.this,MainActivity.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        signinredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, LogIn.class);
                startActivity(i);
            }
        });

    }
}