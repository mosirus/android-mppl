package com.mppl.banksampah.user.ui.riwayatpoin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.RiwayatPoin;
import com.mppl.banksampah.adapter.RiwayatPoinAdapter;

import java.util.ArrayList;

public class PoinMasukFragment extends Fragment {


    private RecyclerView rvPoinMasuk;
    private RiwayatPoinAdapter adapter;

    private String[] tanggalMasuk;
    private String[] ketMasuk;
    private ArrayList<RiwayatPoin> riwayatPoinArrayList;

    public PoinMasukFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepare();
        addItem();
    }

    private void prepare(){
        tanggalMasuk = getResources().getStringArray(R.array.data_tanggal_masuk);
        ketMasuk = getResources().getStringArray(R.array.ket_poin_masuk);
    }

    private void addItem(){
        riwayatPoinArrayList = new ArrayList<>();

        for(int i = 0; i < tanggalMasuk.length; i++){
            RiwayatPoin riwayatPoin = new RiwayatPoin();
            riwayatPoin.setTanggal(tanggalMasuk[i]);
            riwayatPoin.setKeterangan(ketMasuk[i]);
            riwayatPoinArrayList.add(riwayatPoin);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poin_masuk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RiwayatPoinAdapter(getContext());
        adapter.setListRiwayat(riwayatPoinArrayList);
        rvPoinMasuk = view.findViewById(R.id.rvPoinMasuk);
        rvPoinMasuk.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPoinMasuk.setAdapter(adapter);
    }
}
