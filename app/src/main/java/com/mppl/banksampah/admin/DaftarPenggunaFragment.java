package com.mppl.banksampah.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.MainActivity;
import com.mppl.banksampah.R;
import com.mppl.banksampah.RiwayatPoin;
import com.mppl.banksampah.adapter.DaftarPenggunaAdapter;
import com.mppl.banksampah.adapter.RiwayatPoinAdapter;
import com.mppl.banksampah.ui.akun.EditProfileFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DaftarPenggunaFragment extends Fragment {

    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private RecyclerView rvDaftarPengguna;
    private DaftarPenggunaAdapter adapter;

    private ArrayList<DaftarPengguna> daftarPenggunaArrayList;


    public DaftarPenggunaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daftarpengguna_admin, container, false);
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

                adapter.setOnItemClickCallback(new DaftarPenggunaAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(DaftarPengguna data) {
                        DaftarPenggunaDetailFragment fragment = new DaftarPenggunaDetailFragment();
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

