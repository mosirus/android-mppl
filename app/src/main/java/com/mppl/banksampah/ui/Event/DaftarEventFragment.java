package com.mppl.banksampah.ui.Event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.EventAdapter;
import com.mppl.banksampah.ui.EventAdmin;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;


public class DaftarEventFragment extends Fragment {
    private RecyclerView rvDaftarEvent;
    private EventAdapter adapter;


    private Button btntambahevent;
    private String[] ketEvent;
    private ArrayList<EventAdmin> eventAdminArrayList;
    private EventAdminViewModel eventadminViewModel;


    public DaftarEventFragment(){
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        prepare();
        AddItem();


    }

    private void AddItem() {

    }

    private void prepare() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState){
        eventadminViewModel = ViewModelProviders.of(this).get(EventAdminViewModel.class);

        View root = inflater.inflate(R.layout.fragment_daftar_event,container, false);

        btntambahevent = root.findViewById(R.id.btn_tambahevent);

        return root;
    }






}
