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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView loginNow;
    private EditText rEmail, rPassword, rCfpassword;
    private Button registerbtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        loginNow = (TextView) findViewById (R.id.loginNow);
        loginNow.setOnClickListener(this);

        registerbtn = (Button) findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(this);

        rEmail = (EditText) findViewById(R.id.etemail);
        rPassword = (EditText) findViewById(R.id.etpassword);
        rCfpassword = (EditText) findViewById(R.id.cfpassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginNow:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registerbtn:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String etemail = rEmail.getText().toString().trim();
        String etpassword = rPassword.getText().toString().trim();
        String cfpassword = rCfpassword.getText().toString().trim();

        if (etemail.isEmpty()){
            rEmail.setError("Email is Required!");
            rEmail.requestFocus();
            return;
        }

        if (etpassword.isEmpty()){
            rPassword.setError("Password is Required");
            rPassword.requestFocus();
            return;
        }

        if (cfpassword.isEmpty()){
            rCfpassword.setError("Confirmatory Password is Required!");
            rCfpassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(etemail).matches()){
            rEmail.setError("Please provide a valid email!");
            rCfpassword.requestFocus();
            return;
        }

        if (cfpassword.length() < 6){
            rCfpassword.setError("Password should be more than 6 characters!");
            rCfpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(etemail,etpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User (etemail);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
