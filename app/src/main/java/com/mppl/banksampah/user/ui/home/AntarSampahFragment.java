package com.mppl.banksampah.user.ui.home;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.AntarSampahUser;
import com.mppl.banksampah.user.ui.tentang.TentangFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AntarSampahFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference ref;

    private SimpleDateFormat dateFormatter;

    private TextView pickedDate;
    private TextView tvPoinTransaksi;
    private Spinner spnrJenisSampah;
    private Spinner spnrSatuan;
    private EditText edtJumlahSampah;

    private ArrayList<String> listDataSampah = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form_antar, container, false);

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

        pickedDate = view.findViewById(R.id.picked_date);
        Button btnBatal = view.findViewById(R.id.btn_batal_antar);
        btnBatal.setOnClickListener(this);
        Button btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);

        spnrSatuan = view.findViewById(R.id.spinner_satuan);
        edtJumlahSampah = view.findViewById(R.id.edtJumlahSampah);

        ImageButton imgBtnDatePicker = view.findViewById(R.id.date_picker_toggle);
        imgBtnDatePicker.setOnClickListener(this);
        Button btnStatusAntar = view.findViewById(R.id.btn_status_antar);
        btnStatusAntar.setOnClickListener(this);
        TextView tvDaftarLokasi = view.findViewById(R.id.tv_daftar_lokasi);
        tvDaftarLokasi.setOnClickListener(this);
        tvPoinTransaksi = view.findViewById(R.id.tv_poin_transaksi);

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

    private void makeRequest() {
        ref = FirebaseDatabase.getInstance().getReference().child("AntarSampah");
        final String refKey = ref.push().getKey();
        if (validateForm()) {
            ref.addChildEventListener(new ChildEventListener() {
                String JenisSampah = spnrJenisSampah.getSelectedItem().toString();
                String Satuan = spnrSatuan.getSelectedItem().toString();
                String Berat = edtJumlahSampah.getText().toString();
                String Tanggal = pickedDate.getText().toString();
                String poin = tvPoinTransaksi.getText().toString();
                String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.', '_');
                String status = "Sedang diproses";

                AntarSampahUser antarSampahUser = new AntarSampahUser(JenisSampah, Satuan, Berat, Tanggal, poin, currentuserId, status);

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ref.child(currentuserId).child(refKey).setValue(antarSampahUser);
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
                    .setTitle("Request Berhasil")
                    .setMessage("Request kamu berhasil dibuat, silahkan antar sampah ke pos terdekat")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }

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
        } else if (TextUtils.isEmpty(pickedDate.getText().toString())) {
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setMessage("Harap pilih tanggal pengantaran")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
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
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.date_picker_toggle) {
            showDateDialog();
        } else if (v.getId() == R.id.btn_batal_antar) {
            HomeFragment fragment = new HomeFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, HomeFragment.class.getSimpleName())
                    .addToBackStack(null).commit();

        } else if (v.getId() == R.id.btn_status_antar) {
            StatusAntarFragment fragment = new StatusAntarFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, StatusAntarFragment.class.getSimpleName())
                    .addToBackStack(null).commit();

        } else if (v.getId() == R.id.tv_daftar_lokasi) {
            TentangFragment fragment = new TentangFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, TentangFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        } else if (v.getId() == R.id.btn_ok) {
            makeRequest();
        }
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                pickedDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

}
