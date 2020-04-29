package com.mppl.banksampah.user.ui.riwayatpoin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.RiwayatPoinAdapter;
import com.mppl.banksampah.user.model.RiwayatPoin;

import java.util.ArrayList;

public class PoinKeluarFragment extends Fragment {

    private RecyclerView rvPoinKeluar;
    private RiwayatPoinAdapter adapter;

    private ArrayList<RiwayatPoin> riwayatPoinArrayKeluarList;

    public PoinKeluarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poin_keluar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
