// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.admin.daftarpengguna;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.mppl.banksampah.adapter.DaftarPenggunaAdapter;
import com.mppl.banksampah.admin.daftarpengguna.DaftarPenggunaDetailFragment;
import com.mppl.banksampah.admin.model.DaftarPengguna;

import java.util.ArrayList;

public class DaftarPenggunaFragment extends Fragment {

    private DatabaseReference databaseReference;

    private ProgressBar progressDialog;

    private RecyclerView rvDaftarPengguna;
    private DaftarPenggunaAdapter adapter;

    private ArrayList<DaftarPengguna> daftarPenggunaArrayList;


    public DaftarPenggunaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_daftarpengguna_admin, container, false);
        progressDialog = root.findViewById(R.id.loadingProgressPengguna);
        progressDialog.setVisibility(View.VISIBLE);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDaftarPengguna = view.findViewById(R.id.rvDaftarPengguna);
        rvDaftarPengguna.setHasFixedSize(true);
        rvDaftarPengguna.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        daftarPenggunaArrayList = new ArrayList<DaftarPengguna>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    DaftarPengguna daftarPengguna = dataSnapshot1.getValue(DaftarPengguna.class);
                    daftarPenggunaArrayList.add(daftarPengguna);
                }
                adapter = new DaftarPenggunaAdapter(getActivity(), daftarPenggunaArrayList);
                rvDaftarPengguna.setAdapter(adapter);
                progressDialog.setVisibility(View.GONE);

                adapter.setOnItemClickCallback(new DaftarPenggunaAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(DaftarPengguna data) {
                        String userEmail = data.email;
                        String userName = data.nama_lengkap;
                        String userAddress = data.alamat;
                        String userTelp = data.no_telp;
                        String userJob = data.pekerjaan;
                        String userIdentityNumber = data.no_identitas;
                        int userPoint = data.point;
                        String photo_url = data.profile_image_url;

                        Bundle bundle = new Bundle();
                        bundle.putString("Email", userEmail);
                        bundle.putString("Name", userName);
                        bundle.putString("Address", userAddress);
                        bundle.putString("Telp", userTelp);
                        bundle.putString("Job", userJob);
                        bundle.putString("IdentityNumber", userIdentityNumber);
                        bundle.putInt("Point", userPoint);
                        bundle.putString("PhotoURL", photo_url);

                        DaftarPenggunaDetailFragment fragment = new DaftarPenggunaDetailFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, DaftarPenggunaDetailFragment.class.getSimpleName())
                                .addToBackStack(null).commit();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

