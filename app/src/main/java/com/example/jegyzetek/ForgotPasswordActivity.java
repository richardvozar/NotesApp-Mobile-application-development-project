package com.example.jegyzetek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText forgotPasswordET;
    private Button recoverPasswordBTN, backToLoginBTN;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        forgotPasswordET = findViewById(R.id.emailForgotEditText);
        recoverPasswordBTN = findViewById(R.id.recoverPwButton);
        backToLoginBTN = findViewById(R.id.backToLoginButton2);

        firebaseAuth = FirebaseAuth.getInstance();


        backToLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } );

        recoverPasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgotPasswordET.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Először írd be az email címed!", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: send email with password recover

                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Email elküldve, megváltoztathatod a jelszavad a benne levő linkkel!", Toast.LENGTH_SHORT).show();
                                finish();

                                startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Nem létező email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}