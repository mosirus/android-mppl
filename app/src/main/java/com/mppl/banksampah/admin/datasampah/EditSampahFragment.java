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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;

public class EditSampahFragment extends Fragment implements View.OnClickListener {
    public static final String EXTRA = "extra";
    private EditText edtNamaSampah;
    private EditText edtPoinSampah;
    private String dataSampah;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            dataSampah = bundle.getString("Data");
        }
        return inflater.inflate(R.layout.fragment_edit_sampah, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnTambahSampah = view.findViewById(R.id.btnTambah);
        btnTambahSampah.setOnClickListener(this);
        Button btnBatal = view.findViewById(R.id.btnBatalTambah);
        btnBatal.setOnClickListener(this);

        String[] dataSampahArray = dataSampah.split(" - ");
        String[] poinSampahData = dataSampahArray[0].split(" ");

        edtNamaSampah = view.findViewById(R.id.edt_namasampah);
        edtNamaSampah.setText(dataSampahArray[1]);
        edtPoinSampah = view.findViewById(R.id.edt_poinsampah);
        edtPoinSampah.setText(poinSampahData[0]);
    }

    private void editSampah() {
        if (validateForm()) {
            String namaSampah = edtNamaSampah.getText().toString();
            String poinSampah = edtPoinSampah.getText().toString();
            final String dataSampahBaru = poinSampah + " Poin - " + namaSampah;

            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Jenis_Sampah");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        if (snapshot1.child("JenisSampah").getValue().toString().equals(dataSampah)) {
                            String refKey = snapshot1.getKey();
                            reference.child(refKey).child("JenisSampah").setValue(dataSampahBaru).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), "Data sampah telah disimpan", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
            editSampah();
        } else if (v.getId() == R.id.btnBatalTambah) {
            getFragmentManager().popBackStack();
        }
    }
}

