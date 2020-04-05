package com.mppl.banksampah.ui.home;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
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

    private Spinner spnrJenisSampah;
    private Spinner spnrNamaSampah;
    private EditText edtJumlahSampah;
    private EditText edtLokasiJemput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_form_jemput, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//
//        });

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
        spnrNamaSampah = root.findViewById(R.id.spinner_detail_sampah);
        edtJumlahSampah = root.findViewById(R.id.edtJumlahSampah);
        edtLokasiJemput = root.findViewById(R.id.edt_lokasi_jemput);

        ref = FirebaseDatabase.getInstance().getReference().child("JemputSampah");

        return root;
    }

    private void RequestJemput() {
        String jenisSampah = spnrJenisSampah.getSelectedItem().toString();
        String DetailSampah = spnrNamaSampah.getSelectedItem().toString();
        String jumlahSampah = edtJumlahSampah.getText().toString();
        String tanggal = pickedDate.getText().toString();
        String alamatJemput = edtLokasiJemput.getText().toString();
        String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        if (validateForm()) {
            ref.child("IdUser").setValue(currentuserId);
            ref.child("JenisSampah").setValue(jenisSampah);
            ref.child("DetailSampah").setValue(DetailSampah);
            ref.child("Berat").setValue(jumlahSampah);
            ref.child("Tanggal").setValue(tanggal);
            ref.child("AlamatJemput").setValue(alamatJemput);
            ref.child("Status").setValue("Sedang diproses");

            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setTitle("Request Berhasil")
                    .setMessage("Request kamu berhasil dibuat, Sampah Anda Akan Di Jemput Sesuai Dengan Lokasi Anda")
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
            RequestJemput();
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
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                pickedDate.setText("Tanggal dipilih : " + dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

}
