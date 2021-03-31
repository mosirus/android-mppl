// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.admin.datasampah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.DaftarSampahAdapter;

import java.util.ArrayList;

public class DataSampahFragment extends Fragment implements OnClickListener {
    private DatabaseReference ref;
    private RecyclerView rvDaftarSampah;
    private DaftarSampahAdapter daftarSampahAdapter;

    private ArrayList<String> listDataSampah = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_sampah, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnTambahSampah = view.findViewById(R.id.btnTambahSampah);
        btnTambahSampah.setOnClickListener(this);

        rvDaftarSampah = view.findViewById(R.id.rv_daftar_sampah);
        rvDaftarSampah.setHasFixedSize(true);
        rvDaftarSampah.setLayoutManager(new LinearLayoutManager(getContext()));

        loadDataSampah();
    }

    private void loadDataSampah() {
        ref = FirebaseDatabase.getInstance().getReference().child("Jenis_Sampah");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dataSampah = snapshot.child("JenisSampah").getValue().toString();
                    String pushKey = dataSnapshot.getKey();
                    listDataSampah.add(dataSampah);
                }
                daftarSampahAdapter = new DaftarSampahAdapter(getContext(), listDataSampah);
                rvDaftarSampah.setAdapter(daftarSampahAdapter);

                daftarSampahAdapter.setOnItemCallback(new DaftarSampahAdapter.OnItemCallback() {
                    @Override
                    public void onItemclicked(String data) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Data", data);

                        EditSampahFragment fragment = new EditSampahFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getChildFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.dataSampah, fragment, EditSampahFragment.class.getSimpleName())
                                .addToBackStack(null).commit();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTambahSampah) {
            TambahSampahFragment fragment = new TambahSampahFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.dataSampah, fragment, TambahSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }

}

