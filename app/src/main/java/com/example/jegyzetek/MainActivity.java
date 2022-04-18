package com.example.jegyzetek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginBTN, registerBTN, forgotPwBTN;
    private ProgressBar loginPGB;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.emailLoginEditText);
        passwordET = findViewById(R.id.passwordLoginEditText);
        loginBTN = findViewById(R.id.loginButton);
        registerBTN = findViewById(R.id.registerButton);
        forgotPwBTN = findViewById(R.id.forgotPasswordButton);

        loginPGB = findViewById(R.id.progressBarLogin);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if (firebaseUser != null) {
            finish();
            startActivity(new Intent(MainActivity.this, NotesActivity.class));
        }



        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        forgotPwBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Minden mezőt tölts ki!", Toast.LENGTH_SHORT).show();
                } else {
                    // login the user

                    loginPGB.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                checkMailVerification();
                            } else {
                                Toast.makeText(MainActivity.this, "Fiók nem létezik, vagy rossz jelszó!", Toast.LENGTH_SHORT).show();
                                loginPGB.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });






    }

    private void checkMailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified() == true) {
            Toast.makeText(MainActivity.this, "Sikeres bejelentkezés", Toast.LENGTH_SHORT).show();
            finish();

            startActivity(new Intent(MainActivity.this, NotesActivity.class));
        } else {
            loginPGB.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "Erősítsd meg a fiókodat az emailben küldött linkkel!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }



}