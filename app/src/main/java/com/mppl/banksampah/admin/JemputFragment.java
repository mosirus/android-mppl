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
import com.mppl.banksampah.admin.model.JemputModel;

import java.util.ArrayList;
import java.util.List;

public class JemputFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jemputsampah__daftar_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewJemputList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TableViewAdapter adapter = new TableViewAdapter(getJemputList());
        recyclerView.setAdapter(adapter);
    }

    private List<JemputModel> getJemputList() {
        List<JemputModel> jemputList = new ArrayList<JemputModel>();

        jemputList.add(new JemputModel(20200403, "laxojun1@gmail.com", "EH"));
        jemputList.add(new JemputModel(20200415, "laxojun@gmail.com", "Kantin Lama"));
        jemputList.add(new JemputModel(20200415, "laxoju@gmail.com", "EH"));
        jemputList.add(new JemputModel(20200415, "laxoj@gmail.com", "Audit"));
        jemputList.add(new JemputModel(20200415, "laxo@gmail.com", "EH"));
        jemputList.add(new JemputModel(20200415, "lax@gmail.com", "Audit"));
        jemputList.add(new JemputModel(20200415, "la@gmail.com", "EH"));
        jemputList.add(new JemputModel(20200415, "l@gmail.com", "Audit"));
        jemputList.add(new JemputModel(20200415, "@gmail.com", "EH"));
        jemputList.add(new JemputModel(20200415, "2@gmail.com", "EH"));

        return  jemputList;
    }
}

