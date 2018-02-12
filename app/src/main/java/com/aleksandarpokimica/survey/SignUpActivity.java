package com.aleksandarpokimica.survey;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText etEmail, etPassword, etConfirmPassword;
    String email, password, confirmpassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        Button bSignUp = (Button) findViewById(R.id.bSignUp);
        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                confirmpassword = etConfirmPassword.getText().toString();
                if(!email.isEmpty() || !password.isEmpty() || !confirmpassword.isEmpty()) {
                    if (password.equals(confirmpassword)) {

                        signUp(email, password);

                        etEmail.setText("");
                        etPassword.setText("");
                        etConfirmPassword.setText("");
                        Toast.makeText(SignUpActivity.this, "You've successfully created an account", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(i);

                    } else {

                        Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();

                    }
                }else{

                    Toast.makeText(SignUpActivity.this, "Please fill in the fields", Toast.LENGTH_LONG).show();

                }

            }
        });

        Button b2 = (Button)findViewById(R.id.bSignIn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });

    }

    private void signUp(String email, String password) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
