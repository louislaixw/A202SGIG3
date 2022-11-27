package com.example.yogaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView forgotPassword;
    private TextView register;
    private Button login;
    private TextView etEmail, etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.registerNow);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.loginbtn);
        login.setOnClickListener(this);

        etEmail = (TextView) findViewById(R.id.temail);
        etPassword = (TextView) findViewById(R.id.tpassword);

        forgotPassword = (TextView) findViewById(R.id.resetPassword);
        forgotPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerNow:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.loginbtn:
                loginNow();
                break;

            case R.id.resetPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void loginNow(){

        String temail = etEmail.getText().toString().trim();
        String tpassword = etPassword.getText().toString().trim();

        if (temail.isEmpty()){
            etEmail.setError("Email is Required!");
            etEmail.requestFocus();
            return;
        }

        if (tpassword.isEmpty()){
            etPassword.setError("Password is Required");
            etPassword.requestFocus();
            return;
        }

        if (tpassword.length() < 6){
            etPassword.setError("Password is Required to be 6 or more");
            etPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(temail).matches()){
            etEmail.setError("Please provide a valid email!");
            etEmail.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(temail, tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to login! Please re-check credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
