package com.mppl.banksampah.user.ui.home;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mppl.banksampah.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class JemputSampahFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private DatabaseReference ref;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private ImageButton imgBtnDatePicker;
    private TextView pickedDate;
    private Button btnOk;
    private Button btnBatal;
    private Button btnStatusJemput;
    private TextView tvPoinTransaksi;

    private Spinner spnrJenisSampah;
    private Spinner spnrSatuan;
    private EditText edtJumlahSampah;
    private EditText edtLokasiJemput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_form_jemput, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        pickedDate = root.findViewById(R.id.picked_date);
        btnBatal = root.findViewById(R.id.btn_batal_jemput);
        btnBatal.setOnClickListener(this);
        btnOk = root.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        imgBtnDatePicker = root.findViewById(R.id.date_picker_toggle_jemput);
        imgBtnDatePicker.setOnClickListener(this);
        btnStatusJemput = root.findViewById(R.id.btn_status_jemput);
        btnStatusJemput.setOnClickListener(this);

        spnrJenisSampah = root.findViewById(R.id.spinner_jenis_sampah);
        spnrSatuan = root.findViewById(R.id.spinner_satuan);
        edtJumlahSampah = root.findViewById(R.id.edtJumlahSampah);
        edtLokasiJemput = root.findViewById(R.id.edt_lokasi_jemput);

        tvPoinTransaksi = root.findViewById(R.id.tv_poin_transaksi);
        tvPoinTransaksi.setText("100");

        ref = FirebaseDatabase.getInstance().getReference().child("JemputSampah");

        return root;
    }

    private void requestJemput() {
        final String refKey = ref.push().getKey();
        if (validateForm()) {
            ref.addChildEventListener(new ChildEventListener() {
                String jenisSampah = spnrJenisSampah.getSelectedItem().toString();
                String satuanSampah = spnrSatuan.getSelectedItem().toString();
                String jumlahSampah = edtJumlahSampah.getText().toString();
                String tanggal = pickedDate.getText().toString();
                String lokasiJemput = edtLokasiJemput.getText().toString();
                String poin = tvPoinTransaksi.getText().toString();
                String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.','_');

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ref.child(currentuserId).child(refKey).child("JenisSampah").setValue(jenisSampah);
                    ref.child(currentuserId).child(refKey).child("Berat").setValue(jumlahSampah);
                    ref.child(currentuserId).child(refKey).child("Satuan").setValue(satuanSampah);
                    ref.child(currentuserId).child(refKey).child("Tanggal").setValue(tanggal);
                    ref.child(currentuserId).child(refKey).child("LokasiJemput").setValue(lokasiJemput);
                    ref.child(currentuserId).child(refKey).child("Poin").setValue(poin);
                    ref.child(currentuserId).child(refKey).child("Status").setValue("Sedang diproses");
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
                    .setMessage("Request kamu berhasil dibuat, Sampah Anda Akan di Jemput Sesuai Dengan Lokasi Anda")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
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
        } else if (TextUtils.isEmpty(edtLokasiJemput.getText().toString())) {
            edtLokasiJemput.setError("Harap masukkan lokasi penjemputan sampah");
            result = false;
        }
        return result;
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.date_picker_toggle_jemput) {
            showDateDialog();
        } else if (v.getId() == R.id.btn_batal_jemput) {
            HomeFragment fragment = new HomeFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, HomeFragment.class.getSimpleName())
                    .addToBackStack(null).commit();

        }

        else if (v.getId() == R.id.btn_status_jemput) {
            StatusJemputFragment fragment = new StatusJemputFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, StatusJemputFragment.class.getSimpleName())
                    .addToBackStack(null).commit();

        }

        else if (v.getId() == R.id.btn_ok){
            requestJemput();
        }

    }

    private void showDateDialog() {

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                pickedDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

}
