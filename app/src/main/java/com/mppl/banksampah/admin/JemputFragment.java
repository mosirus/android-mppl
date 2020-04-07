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
import com.mppl.banksampah.adapter.TableViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class JemputFragment extends Fragment {
    private RecyclerView recyclerView;
    private TableViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jemputsampah__daftar_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewJemputList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TableViewAdapter(getJemputList());
        recyclerView.setAdapter(adapter);
    }

    private List getJemputList() {
        List jemputList = new ArrayList<>();

        jemputList.add(new JemputModel("04-06-2020", "laxojun1@gmail.com", "Audit"));
        jemputList.add(new JemputModel("03-04-2020", "laxo@gmail.com", "EH"));

        return  jemputList;
    }
}

