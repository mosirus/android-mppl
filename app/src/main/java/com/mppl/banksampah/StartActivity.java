// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mppl.banksampah.admin.AdminMainActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            if (user.getUid().equals("Ydb0Zv17xzZ0X6VifzYNUJhkF8J2")) {
                startActivity(new Intent(StartActivity.this, AdminMainActivity.class));
                finish();
            } else {
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogin = findViewById(R.id.btn_masuk);
        btnLogin.setOnClickListener(this);

        btnRegister = findViewById(R.id.btn_daftar);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //Intent ke Halaman Login
        if (v.getId() == R.id.btn_masuk) {
            Intent IntentLogin = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(IntentLogin);
        }

        if (v.getId() == R.id.btn_daftar) {
            Intent IntentDaftar = new Intent(StartActivity.this, RegisterActivity.class);
            startActivity(IntentDaftar);
        }
    }

    //Intent Ke Halaman Register
}
