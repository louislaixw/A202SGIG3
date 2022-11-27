package com.example.yogaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity ;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private TextView creset;
    private Button resetPassbtn;
    private EditText emailET;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        creset = (TextView) findViewById(R.id.cancelReset);
        creset.setOnClickListener(this);

        resetPassbtn = (Button) findViewById(R.id.resetPass);
        emailET = (EditText) findViewById(R.id.resetEmail);
        mAuth = FirebaseAuth.getInstance();

        resetPassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelReset:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void resetPassword(){
        String email = emailET.getText().toString().trim();

        if(email.isEmpty()){
            emailET.setError("Email is required!");
            emailET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setError("Invalid email!");
            emailET.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email address for the reset email!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotPassword.this, "Reset Attempt was failed! Returning to login!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                }
            }
        });
    }
}
