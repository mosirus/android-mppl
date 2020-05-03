package com.mppl.banksampah.user.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.user.ui.riwayatpoin.RiwayatPoinFragment;

import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView tvTanggalPoinMasuk;
    TextView tvRincianPoinMasuk;
    TextView tvJumlahPoinMasuk;
    TextView tvJumlahPoin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTanggalPoinMasuk = view.findViewById(R.id.tanggal_info);
        tvRincianPoinMasuk = view.findViewById(R.id.txt_info);
        tvJumlahPoinMasuk = view.findViewById(R.id.txt_info_poin);
        tvJumlahPoin = view.findViewById(R.id.jumlah_poin);

        Button btnAntar = view.findViewById(R.id.btn_antar);
        btnAntar.setOnClickListener(this);
        Button btnJemput = view.findViewById(R.id.btn_jemput);
        btnJemput.setOnClickListener(this);
        Button btnTukarPoin = view.findViewById(R.id.btn_tukarpoin);
        btnTukarPoin.setOnClickListener(this);
        LinearLayoutCompat riwayatPoinContainer = view.findViewById(R.id.riwayatPoinNotification);
        riwayatPoinContainer.setOnClickListener(this);

        loadUserPoint();
        loadPointHistory();
    }

    private void loadUserPoint() {
        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.', '_');
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String poin = Objects.requireNonNull(dataSnapshot.child("point").getValue()).toString();
                tvJumlahPoin.setText(poin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadPointHistory() {
        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.', '_');
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("AntarSampah").child(currentuser);
        Query query = ref.limitToLast(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if ((dataSnapshot1.child("status").getValue()).toString().equals("Berhasil")) {
                        String tanggal = Objects.requireNonNull(dataSnapshot1.child("tanggal").getValue()).toString();
                        String poin = Objects.requireNonNull(dataSnapshot1.child("poin").getValue()).toString();
                        String satuan = Objects.requireNonNull(dataSnapshot1.child("satuan").getValue()).toString();
                        String jenisSampah = Objects.requireNonNull(dataSnapshot1.child("jenisSampah").getValue()).toString();
                        String berat = Objects.requireNonNull(dataSnapshot1.child("berat").getValue()).toString();
                        String rincian = "Penukaran sampah " + jenisSampah + ": " + berat + " " + satuan;

                        tvTanggalPoinMasuk.setText(tanggal);
                        tvRincianPoinMasuk.setText(rincian);
                        tvJumlahPoinMasuk.setVisibility(View.VISIBLE);
                        tvJumlahPoinMasuk.setText("+" + poin + " Poin");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_antar) {
            AntarSampahFragment fragment = new AntarSampahFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, AntarSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        } else if (v.getId() == R.id.btn_jemput) {
            JemputSampahFragment fragment = new JemputSampahFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, JemputSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        } else if (v.getId() == R.id.btn_tukarpoin) {
            TukarPoinFragment fragment = new TukarPoinFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, TukarPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        } else if (v.getId() == R.id.riwayatPoinNotification) {
            RiwayatPoinFragment fragment = new RiwayatPoinFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, RiwayatPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}