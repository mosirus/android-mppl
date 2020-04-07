package com.mppl.banksampah.ui.riwayatpoin;


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
import com.mppl.banksampah.adapter.RiwayatPoinAdapter;

public class PoinKeluarFragment extends Fragment {

    private RecyclerView rvPoinKeluar;
    private RiwayatPoinAdapter adapter;

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

        adapter = new RiwayatPoinAdapter(getContext());

        rvPoinKeluar = view.findViewById(R.id.rvPoinKeluar);
        rvPoinKeluar.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPoinKeluar.setAdapter(adapter);

    }
}
