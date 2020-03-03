package com.mppl.banksampah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
