package com.mppl.banksampah.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.RiwayatPoin;
import com.mppl.banksampah.adapter.DaftarPenggunaAdapter;
import com.mppl.banksampah.adapter.RiwayatPoinAdapter;

import java.util.ArrayList;

public class DaftarPenggunaFragment extends Fragment {

    private RecyclerView rvDaftarPengguna;
    private DaftarPenggunaAdapter adapter;

    private String[] email;
    private String[] poin;
    private ArrayList<DaftarPengguna> daftarPenggunaArrayList;

    public DaftarPenggunaFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepare();
        addItem();
    }

    private void prepare(){
        email = getResources().getStringArray(R.array.data_tanggal_masuk);
        poin = getResources().getStringArray(R.array.ket_poin_masuk);
    }

    private void addItem(){
        daftarPenggunaArrayList = new ArrayList<>();

        for(int i = 0; i < email.length; i++){
            DaftarPengguna daftarPengguna = new DaftarPengguna();
            daftarPengguna.setEmail(email[i]);
            daftarPengguna.setPoin(poin[i]);
            daftarPenggunaArrayList.add(daftarPengguna);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daftarpengguna_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DaftarPenggunaAdapter(getContext());
        adapter.setListPengguna(daftarPenggunaArrayList);
        rvDaftarPengguna = view.findViewById(R.id.rvDaftarPengguna);
        rvDaftarPengguna.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDaftarPengguna.setAdapter(adapter);
    }
}

