// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.admin.jemputsampah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.DaftarJemputSampahAdapter;
import com.mppl.banksampah.user.model.JemputSampahUser;

import java.util.ArrayList;

public class PermintaanJemputFragment extends Fragment {

    private RecyclerView rvListPermintaanJemput;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private ArrayList<JemputSampahUser> listJemputSampah;
    private DaftarJemputSampahAdapter jemputSampahAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_jemputsampah__daftar_admin, container, false);

        rvListPermintaanJemput = root.findViewById(R.id.pa_listPermintaanAdmin);
        rvListPermintaanJemput.setHasFixedSize(true);
        rvListPermintaanJemput.setLayoutManager(new LinearLayoutManager(getContext()));

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("JemputSampah");
        listJemputSampah = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userEmail = snapshot.getKey();
                    for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                        JemputSampahUser jemputSampahUser = dataSnapshot2.getValue(JemputSampahUser.class);
                        if (dataSnapshot2.child("Status").getValue().toString().equals("Sedang diproses")) {
                            jemputSampahUser.setPushKey(dataSnapshot2.getKey());
                            jemputSampahUser.setEmail(userEmail);
                            listJemputSampah.add(jemputSampahUser);
                        }
                    }
                }
                jemputSampahAdapter = new DaftarJemputSampahAdapter(getActivity(), listJemputSampah);
                rvListPermintaanJemput.setAdapter(jemputSampahAdapter);

                jemputSampahAdapter.setOnItemCallback(new DaftarJemputSampahAdapter.OnItemCallback() {
                    @Override
                    public void onItemclicked(JemputSampahUser data) {
                        String userEmail = data.getEmail();
                        String tanggalPermintaan = data.getTanggal();
                        String jumlahSampah = data.getBerat();
                        String satuanSampah = data.getSatuan();
                        String jenisSampah = data.getJenisSampah();
                        String poinTransaksi = data.getPoin();
                        String pushKey = data.getPushKey();
                        String lokasiPenjemputan = data.getLokasiJemput();

                        Bundle bundle = new Bundle();
                        bundle.putString("UserEmail", userEmail);
                        bundle.putString("Tanggal", tanggalPermintaan);
                        bundle.putString("JumlahSampah", jumlahSampah);
                        bundle.putString("Satuan", satuanSampah);
                        bundle.putString("JenisSampah", jenisSampah);
                        bundle.putString("PoinTransaksi", poinTransaksi);
                        bundle.putString("RequestChildKey", pushKey);
                        bundle.putString("LokasiJemput", lokasiPenjemputan);

                        KonfirmasiJemputFragment fragment = new KonfirmasiJemputFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getChildFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.containerPermintaanJemput, fragment, KonfirmasiJemputFragment.class.getSimpleName())
                                .addToBackStack(null).commit();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
