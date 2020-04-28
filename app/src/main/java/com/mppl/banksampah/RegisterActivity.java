package com.mppl.banksampah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mppl.banksampah.user.model.User;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "REGISTER";
    public User user;

    private Button btnDaftar;
    private Button btnBatal;

    //Komponen User
    private EditText namaLengkap;
    private EditText editTextEmail;
    private EditText notelp;
    private EditText username;
    private EditText editTextPassword;
    private double point = 0;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        btnDaftar = findViewById(R.id.btn_register);
        btnBatal = findViewById(R.id.btn_batal);
        namaLengkap = findViewById(R.id.tv_nama);
        editTextEmail = findViewById(R.id.tv_email);
        notelp = findViewById(R.id.tv_notelp);
        username = findViewById(R.id.tv_pass_conf);
        editTextPassword = findViewById(R.id.tv_pass_reg);

        database = FirebaseDatabase.getInstance().getReference();

        btnBatal.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);
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
            if (editTextPassword.getText().toString().equals(username.getText().toString())) {
                username.setError(null);
            } else {
                username.setError("The password confirmation does not match");
                result = false;
            }
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Required");
            result = false;
        } else {
            editTextPassword.setError(null);
        }

        return result;
    }


    private void registerUser() {
        /*
        Kode untuk mengirimkan data ke Firebase Realtime Database
        */

        String acEmail = editTextEmail.getText().toString().trim();
        String acPassword = editTextPassword.getText().toString().trim();

        if (!validateForm()) {
            return;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Mendaftarkan Akun...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(acEmail, acPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                progressDialog.dismiss();
                                Log.d(TAG, "createUserWithEmail:success");
                                onAuthSuccess(task.getResult().getUser());
                            } else {
                                // If sign in fails, display a message to the user.
                                progressDialog.dismiss();
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Pendaftaran gagal, email mungkin sudah digunakan",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        String acNamaLengkap = namaLengkap.getText().toString().trim();
        String acEmail = editTextEmail.getText().toString().trim();
        String acNoTelp = notelp.getText().toString().trim();
        String acPassword = editTextPassword.getText().toString().trim();
        int firstPoint = 0;
        String acPekerjaan = "";
        String acNoId = "";
        String acAlamat = "";

        writeNewUser(user.getEmail().replace('.', '_'), acNamaLengkap, acEmail, acNoTelp, acPassword, acNoId, acPekerjaan, acAlamat, firstPoint);

        // Go to MainActivity
        new AlertDialog.Builder(this)
                .setTitle("Pendaftaran Berhasil")
                .setMessage("Tekan OK untuk menuju ke halaman utama")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                }).show();
    }

    private void writeNewUser(String userId, String nama, String email, String noTelp, String password,
                              String id, String pekerjaan, String alamat, int point) {

        String acNamaLengkap = namaLengkap.getText().toString().trim();
        String acEmail = editTextEmail.getText().toString().trim();
        String acNoTelp = notelp.getText().toString().trim();
        String acUsername = username.getText().toString().trim();
        String acPassword = editTextPassword.getText().toString().trim();
        int firstPoint = 0;
        String acPekerjaan = "";
        String acNoId = "";
        String acAlamat = "";

        User customer = new User(acNamaLengkap, acEmail, acNoTelp, acPassword, acPekerjaan, acNoId, acAlamat, firstPoint);
        database.child("Users").child(userId).setValue(customer);
    }

    @Override
    public void onClick(View view) {
        if (view == btnDaftar) {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputManager.isAcceptingText()) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            validateForm();
            registerUser();

        } else if (view == btnBatal) {
            Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
            startActivity(intent);
        }
    }
}