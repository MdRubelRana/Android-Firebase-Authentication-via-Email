package com.dirtybytes.firebaseauthentication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText conPassword;
    private EditText name;
    private TextView linkSignin;
    private Button btnSignup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        firebaseAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        conPassword = findViewById(R.id.conpassword_reg);
        name = findViewById(R.id.name);
        linkSignin = findViewById(R.id.link_signin);
        btnSignup = findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regEmail = email.getText().toString().trim();
                String regPass = password.getText().toString().trim();
                String regName = name.getText().toString().trim();
                String regConPass = conPassword.getText().toString().trim();

                if (TextUtils.isEmpty(regName)) {
                    name.setError("Required field.");
                    return;
                }
                if (TextUtils.isEmpty(regEmail)) {
                    email.setError("Required field.");
                    return;
                }
                if (TextUtils.isEmpty(regPass)) {
                    password.setError("Required field.");
                    return;
                }
                if (TextUtils.isEmpty(regConPass)) {
                    conPassword.setError("required field.");
                    return;
                }


                mDialog.setMessage("Creating new account. Please wait a while...");
                mDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(regEmail, regPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            Toast.makeText(getApplicationContext(), "Account create successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        linkSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(RegistrationActivity.this)
                .setTitle("Warning")
                .setMessage("Do you really want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(RegistrationActivity.this);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show();
    }
}
