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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public User user;

    private Button btnDaftar;

    //Komponen User
    private EditText namaLengkap;
    private EditText editTextEmail;
    private EditText notelp;
    private EditText username;
    private EditText editTextPassword;
    private double point = 0;

    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

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

        database = FirebaseDatabase.getInstance().getReference();

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

    private void registerUser(User user){
        /*
        Kode untuk mengirimkan data ke Firebase Realtime Database
        */
        String acNamaLengkap = namaLengkap.getText().toString().trim();
        String acEmail = editTextEmail.getText().toString().trim();
        String acNoTelp = notelp.getText().toString().trim();
        String acUsername = username.getText().toString().trim();
        String acPassword = editTextPassword.getText().toString().trim();
        int firstPoint = 0;

        user = new User(acNamaLengkap,acEmail,acUsername,acNoTelp,acPassword,firstPoint);

        database.child("Users").push().setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                namaLengkap.setText("");
                editTextEmail.setText("");
                notelp.setText("");
                username.setText("");
                editTextPassword.setText("");
                Snackbar.make(findViewById(R.id.btn_daftar),"Data berhasil didaftarkan",Snackbar.LENGTH_LONG).show();
                Toast.makeText(RegisterActivity.this, "Register succesfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == btnDaftar) {
            registerUser(user);
        }

        if(view == textViewSignin) {
            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(login);
        }
    }
}
