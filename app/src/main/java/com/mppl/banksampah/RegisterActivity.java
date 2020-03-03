package com.mppl.banksampah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDaftar;
    private EditText namaLengkap;
    private EditText editTextEmail;
    private EditText notelp;
    private EditText username;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        btnDaftar =  findViewById(R.id.btn_register);
        namaLengkap = findViewById(R.id.tv_nama);
        editTextEmail = findViewById(R.id.tv_email);
        notelp = findViewById(R.id.tv_notelp);
        username = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.tv_pass);
        textViewSignin = findViewById(R.id.text_signup);

        btnDaftar.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(namaLengkap.getText().toString())) {
            namaLengkap.setError("Required");
            result = false;
        } else {
            namaLengkap.setError(null);
        }

        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Required");
            result = false;
        } else {
            editTextEmail.setError(null);
        }

        if (TextUtils.isEmpty(notelp.getText().toString())) {
            notelp.setError("Required");
            result = false;
        } else {
            notelp.setError(null);
        }

        if (TextUtils.isEmpty(username.getText().toString())) {
            username.setError("Required");
            result = false;
        } else {
            username.setError(null);
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Required");
            result = false;
        } else {
            editTextPassword.setError(null);
        }

        return result;
    }

    private void registerUSer() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (!validateForm()) {
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Could not register. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnDaftar) {
            registerUSer();
        }

        if(view == textViewSignin) {
            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(login);
        }
    }
}
