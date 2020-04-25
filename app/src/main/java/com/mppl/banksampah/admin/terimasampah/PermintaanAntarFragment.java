package com.mppl.banksampah.admin.terimasampah;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private ArrayList<AntarSampahUser> listAntarSampahUser;
    private DaftarAntarSampahUserAdapter antarSampahUserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permintaan_antar, container, false);

        rvListPermintaanAntar = root.findViewById(R.id.pa_listPermintaan);
        rvListPermintaanAntar.setHasFixedSize(true);
        rvListPermintaanAntar.setLayoutManager(new LinearLayoutManager(getContext()));

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("AntarSampah");
        listAntarSampahUser = new ArrayList<AntarSampahUser>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        AntarSampahUser antarSampahUser = dataSnapshot1.getValue(AntarSampahUser.class);
                        if (dataSnapshot1.child("status").getValue().toString().equals("Sedang diproses")) {
                            listAntarSampahUser.add(antarSampahUser);
                        }
                    }
                }
                antarSampahUserAdapter = new DaftarAntarSampahUserAdapter(getActivity(), listAntarSampahUser);
                rvListPermintaanAntar.setAdapter(antarSampahUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }


}
