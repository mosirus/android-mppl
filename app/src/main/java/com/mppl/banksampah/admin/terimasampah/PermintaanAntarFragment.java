package com.mppl.banksampah.admin.terimasampah;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.DaftarAntarSampahUserAdapter;
import com.mppl.banksampah.user.model.AntarSampahUser;

import java.util.ArrayList;


public class PermintaanAntarFragment extends Fragment {
    private RecyclerView rvListPermintaanAntar;

    private ArrayList<AntarSampahUser> listAntarSampahUser;
    private DaftarAntarSampahUserAdapter antarSampahUserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permintaan_antar, container, false);

        rvListPermintaanAntar = root.findViewById(R.id.pa_listPermintaan);
        rvListPermintaanAntar.setHasFixedSize(true);
        rvListPermintaanAntar.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("AntarSampah");
        listAntarSampahUser = new ArrayList<AntarSampahUser>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        AntarSampahUser antarSampahUser = dataSnapshot1.getValue(AntarSampahUser.class);
                        if (dataSnapshot1.child("status").getValue().toString().equals("Sedang diproses")) {
                            antarSampahUser.setPushKey(dataSnapshot1.getKey());
                            listAntarSampahUser.add(antarSampahUser);
                        }
                    }
                }
                antarSampahUserAdapter = new DaftarAntarSampahUserAdapter(getActivity(), listAntarSampahUser);
                rvListPermintaanAntar.setAdapter(antarSampahUserAdapter);

                antarSampahUserAdapter.setOnItemCallback(new DaftarAntarSampahUserAdapter.OnItemCallback() {
                    @Override
                    public void onItemclicked(AntarSampahUser data) {
                        String userEmail = data.getCurrentId();
                        String tanggalPermintaan = data.getTanggal();
                        String jumlahSampah = data.getBerat();
                        String satuanSampah = data.getSatuan();
                        String jenisSampah = data.getJenisSampah();
                        String poinTransaksi = data.getPoin();
                        String pushKey = data.getPushKey();

                        Bundle bundle = new Bundle();
                        bundle.putString("UserEmail", userEmail);
                        bundle.putString("Tanggal", tanggalPermintaan);
                        bundle.putString("JumlahSampah", jumlahSampah);
                        bundle.putString("Satuan", satuanSampah);
                        bundle.putString("JenisSampah", jenisSampah);
                        bundle.putString("PoinTransaksi", poinTransaksi);
                        bundle.putString("RequestChildKey", pushKey);

                        KonfirmasiPermintaanFragment fragment = new KonfirmasiPermintaanFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getChildFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.containerPermintaanAntar, fragment, KonfirmasiPermintaanFragment.class.getSimpleName())
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
