package com.mppl.banksampah.admin.datasampah;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mppl.banksampah.R;

public class TambahSampahFragment extends Fragment implements View.OnClickListener {
    private EditText edtNamaSampah;
    private EditText edtPoinSampah;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tambah_sampah, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnTambahSampah = view.findViewById(R.id.btnTambah);
        btnTambahSampah.setOnClickListener(this);
        Button btnBatal = view.findViewById(R.id.btnBatalTambah);
        btnBatal.setOnClickListener(this);

        edtNamaSampah = view.findViewById(R.id.edt_namasampah);
        edtPoinSampah = view.findViewById(R.id.edt_poinsampah);
    }

    private void tambahSampah() {
        if (validateForm()) {
            String namaSampah = edtNamaSampah.getText().toString();
            String poinSampah = edtPoinSampah.getText().toString();
            String dataSampah = poinSampah + " Poin - " + namaSampah;

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Jenis_Sampah");
            final String refKey = reference.push().getKey();
            reference.child(refKey).child("JenisSampah").setValue(dataSampah);
            reference.child(refKey).child("Satuan").setValue("Kg");

            Toast.makeText(getContext(), "Data sampah berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNamaSampah.getText().toString())) {
            edtNamaSampah.setError("Masukkan nama sampah");
            result = false;
        } else if (TextUtils.isEmpty(edtPoinSampah.getText().toString())) {
            edtPoinSampah.setError("Masukkan poin sampah");
            result = false;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTambah) {
            validateForm();
            tambahSampah();
        } else if (v.getId() == R.id.btnBatalTambah) {
            getFragmentManager().popBackStack();
        }
    }
}
