// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mppl.banksampah.admin.AdminMainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private DatabaseReference database;
    private FirebaseAuth auth;
    private EditText edtEmail;
    private EditText edtPass;
    private TextView tvRegister;
    private Button btnLogin;
    private Button btnCancel;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.tv_email);
        edtPass = findViewById(R.id.tv_pass);
        tvRegister = findViewById(R.id.daftar_sekarang);
        btnLogin = findViewById(R.id.btn_masuk);
        btnCancel = findViewById(R.id.btn_batal);
        progressBar = findViewById(R.id.loadingProgress);

        btnLogin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    //fungsi signin untuk mengkonfirmasi data pengguna yang sudah mendaftar sebelumnya
    private void signIn() {
        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            progressBar.setVisibility(View.GONE);
            return;
        }
        //showProgressDialog();

        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(LoginActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        // Go to MainActivity

        if (user.getUid().equals("Ydb0Zv17xzZ0X6VifzYNUJhkF8J2")){
            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }


    //fungsi untuk memvalidasi EditText email dan password agar tak kosong dan sesuai format
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Masukkan email");
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPass.getText().toString())) {
            edtPass.setError("Masukkan password");
            result = false;
        } else {
            edtPass.setError(null);
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_masuk) {

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputManager.isAcceptingText()) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            signIn();
        }
        else if (i == R.id.btn_batal) {
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
        }
        else if (i == R.id.daftar_sekarang) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

    }

}