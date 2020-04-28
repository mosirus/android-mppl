package com.mppl.banksampah.admin.terimasampah;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.AntarSampahUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TerimaSampahFragment extends Fragment implements OnClickListener {
    private DatabaseReference ref;

    private SimpleDateFormat dateFormatter;

    private TextView pickedDate;
    private EditText edtEmailPengguna;
    private TextView tvPoinTransaksi;
    private Spinner spnrJenisSampah;
    private Spinner spnrSatuan;
    private EditText edtJumlahSampah;
    private String userEmail;

    private ArrayList<String> listDataSampah = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_isiformterimasampah_admin, container, false);

        spnrJenisSampah = root.findViewById(R.id.spinner_jenis_sampah);

        loadDataSampah();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, listDataSampah);
        spinnerArrayAdapter.add("Jenis Sampah");
        spinnerArrayAdapter.notifyDataSetChanged();

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnrJenisSampah.setAdapter(spinnerArrayAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Button btnOK = view.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(this);

        pickedDate = view.findViewById(R.id.picked_date);

        Date currentTime = Calendar.getInstance().getTime();
        pickedDate.setText(dateFormatter.format(currentTime));

        spnrJenisSampah = view.findViewById(R.id.spinner_jenis_sampah);
        spnrSatuan = view.findViewById(R.id.spinner_satuan);
        edtJumlahSampah = view.findViewById(R.id.edtJumlahSampah);
        edtEmailPengguna = view.findViewById(R.id.edit_email_pengguna);
        tvPoinTransaksi = view.findViewById(R.id.tv_poin_transaksi_terima);

        ImageButton imgBtnDatePicker = view.findViewById(R.id.date_picker_toggle);
        imgBtnDatePicker.setOnClickListener(this);

        userEmail = edtEmailPengguna.getText().toString().replace('.', '_');

        edtJumlahSampah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePoint();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculatePoint();
            }
        });

        spnrJenisSampah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                calculatePoint();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    private void terimaAntarSampah() {

        ref = FirebaseDatabase.getInstance().getReference().child("AntarSampah");
        final String refKey = ref.push().getKey();

        if (validateForm()) {
            final String userRefEmail = edtEmailPengguna.getText().toString().replace('.', '_');
            final DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference().child("Users");
            userDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(userRefEmail)) {
                        ref.addChildEventListener(new ChildEventListener() {
                            String jenisSampah = spnrJenisSampah.getSelectedItem().toString();
                            String satuanSampah = spnrSatuan.getSelectedItem().toString();
                            String jumlahSampah = edtJumlahSampah.getText().toString();
                            String tanggal = pickedDate.getText().toString();
                            String poin = tvPoinTransaksi.getText().toString();
                            String status = "Berhasil";

                            AntarSampahUser antarSampahUser = new AntarSampahUser(jenisSampah, satuanSampah, jumlahSampah, tanggal, poin, userEmail, status);

                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                assert refKey != null;
                                ref.child(edtEmailPengguna.getText().toString().replace('.', '_')).child(refKey).setValue(antarSampahUser);
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                                .setTitle("Berhasil")
                                .setMessage("Proses terima sampah berhasil, poin akan ditambahkan ke akun pengguna")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                        addUserPoint();
                    } else {
                        Toast.makeText(getActivity(), "Email user tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
    }

    private void addUserPoint() {
        // Fungsi menambah point user

        String userRefEmail = edtEmailPengguna.getText().toString().replace('.', '_');
        final int poin = Integer.parseInt(tvPoinTransaksi.getText().toString());

        final DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userRefEmail);
        userDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userCurrentPoint = Objects.requireNonNull(dataSnapshot.child("point").getValue()).toString();

                String finalPoint = String.valueOf(Integer.parseInt(userCurrentPoint) + poin);
                userDataRef.child("point").setValue(finalPoint);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void loadDataSampah() {
        ref = FirebaseDatabase.getInstance().getReference().child("Jenis_Sampah");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dataSampah = snapshot.child("JenisSampah").getValue().toString();
                    listDataSampah.add(dataSampah);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculatePoint() {
        int jumlahSampah;
        String[] strPoinSampah;
        int poinSampah;

        if (spnrJenisSampah.getSelectedItem().toString().equals("Jenis Sampah") ||
                edtJumlahSampah.getText().toString().isEmpty()) {
            tvPoinTransaksi.setText("Poin");
        } else {
            jumlahSampah = Integer.parseInt(edtJumlahSampah.getText().toString());
            strPoinSampah = spnrJenisSampah.getSelectedItem().toString().split(" ");
            poinSampah = Integer.parseInt(strPoinSampah[0]);

            String jumlahPoin = String.valueOf(jumlahSampah * poinSampah);
            tvPoinTransaksi.setText(jumlahPoin);
        }

    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtJumlahSampah.getText().toString())) {
            edtJumlahSampah.setError("Harap masukkan jumlah sampah");
            result = false;
        } else if (TextUtils.isEmpty(edtEmailPengguna.getText().toString())) {
            edtEmailPengguna.setError("Harap masukkan email pengguna");
            result = false;
        } else if (spnrJenisSampah.getSelectedItem().toString().equals("Jenis Sampah")) {
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setMessage("Harap masukkan jenis sampah")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            result = false;

        } else if (spnrSatuan.getSelectedItem().toString().equals("Satuan")) {
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setMessage("Harap masukkan satuan sampah")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            result = false;

        } else if (TextUtils.isEmpty(pickedDate.getText().toString())) {
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setMessage("Harap pilih tanggal pengantaran")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            result = false;
        }
        return result;
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                pickedDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.date_picker_toggle) {
            showDateDialog();
        } else if (v.getId() == R.id.btn_ok) {
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setMessage("Apakah anda yakin data yang dimasukkan telah benar ?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            terimaAntarSampah();
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }

}

