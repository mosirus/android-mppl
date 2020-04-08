package com.mppl.banksampah.ui.Event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.EventAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RiwayatEventFragment extends Fragment {

    private EventAdminViewModel eventAdminViewModel;

    private EventAdapter adapter;
    private RecyclerView rvRiwayatEvent;


    public RiwayatEventFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_riwayat_event, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new EventAdapter(getContext());

        rvRiwayatEvent = view.findViewById(R.id.tab_riwayat);
        rvRiwayatEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRiwayatEvent.setAdapter(adapter);

    }
}
