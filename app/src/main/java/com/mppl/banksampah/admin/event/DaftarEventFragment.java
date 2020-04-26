package com.mppl.banksampah.admin.event;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.EventAdapter;
import com.mppl.banksampah.admin.model.EventAdmin;

import java.util.ArrayList;

public class DaftarEventFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvDaftarEvent;
    private EventAdapter adapter;

    private Button btnTambahEvent;
//    private ImageButton btnEditEvent;
//    private ImageButton btnHapusEvent;

    private TypedArray eventPhoto;
    private String[] nameEvent;

    private ArrayList<EventAdmin> eventAdminArrayList;

    public DaftarEventFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepare();
        addItem();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_daftar_event, container, false);

//        btnEditEvent = root.findViewById(R.id.editevent);
//        btnEditEvent.setOnClickListener(this);
//        btnHapusEvent = root.findViewById(R.id.buangevent);
//        btnHapusEvent.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTambahEvent = view.findViewById(R.id.btn_tambah_event);
        btnTambahEvent.setOnClickListener(this);

        adapter = new EventAdapter(getContext());
        adapter.setListEvent(eventAdminArrayList);
        rvDaftarEvent = view.findViewById(R.id.rvDaftarEvent);
        rvDaftarEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDaftarEvent.setAdapter(adapter);
    }

    private void prepare(){
        eventPhoto = getResources().obtainTypedArray(R.array.event_photo);
        nameEvent = getResources().getStringArray(R.array.event_name);
    }

    private void addItem(){
        eventAdminArrayList = new ArrayList<>();

        for(int i = 0; i < nameEvent.length; i++){
            EventAdmin eventAdmin = new EventAdmin();
            eventAdmin.setPhotoEvent(eventPhoto.getResourceId(i,-1));
            eventAdmin.setNamaEvent(nameEvent[i]);
            eventAdminArrayList.add(eventAdmin);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tambah_event){
            TambahEventFragment tambahEventFragment = new TambahEventFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, tambahEventFragment, TambahEventFragment.class.getSimpleName())
                        .addToBackStack(null).commit();

        }

        if (v.getId() == R.id.editevent){
            EditEventFragment editEventFragment = new EditEventFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, editEventFragment, EditEventFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }else if (v.getId() == R.id.buangevent){

        }
    }
}
