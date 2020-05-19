package com.mppl.banksampah.admin.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.EventAdapter;
import com.mppl.banksampah.admin.model.EventAdmin;

import java.util.ArrayList;

public class DaftarEventFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvDaftarEvent;
    private EventAdapter adapter;

    private Button btnEditEvent;
    private Button btnDeleteEvent;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String GetUserID;

    private ArrayList<EventAdmin> eventAdminArrayList;

    public DaftarEventFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_daftar_event, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        adapter = new EventAdapter(getContext());
//        adapter.setListEvent(eventAdminArrayList);

          rvDaftarEvent = view.findViewById(R.id.rvDaftarEvent);

          rvDaftarEvent.setHasFixedSize(true);
          rvDaftarEvent.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvDaftarEvent.setHasFixedSize(true);
//        rvDaftarEvent.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Event");
        eventAdminArrayList = new ArrayList<EventAdmin>();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    EventAdmin event = snapshot.getValue(EventAdmin.class);
                    eventAdminArrayList.add(event);
                }
                adapter = new EventAdapter(getActivity(), eventAdminArrayList);
                rvDaftarEvent.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.setOnItemCallback(new EventAdapter.OnItemCallback() {
                    @Override
                    public void onItemclicked(EventAdmin data) {
                        String nameEvent = data.getNamaEvent();
                        String timeEvent = data.getWaktuEvent();
                        String locEvent = data.getTempatEvent();
                        String descEvent = data.getDescEvent();
                        String URLEvent = data.getURLEvent();

                        Bundle editEventBundle = new Bundle();
                        editEventBundle.putString("namaEvent",nameEvent);
                        editEventBundle.putString("waktuEvent",timeEvent);
                        editEventBundle.putString("tempatEvent",locEvent);
                        editEventBundle.putString("descEvent", descEvent);
                        editEventBundle.putString("urlevent", URLEvent);

                        EditEventFragment fragment = new EditEventFragment();
                        fragment.setArguments(editEventBundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.tambahEvent_container, fragment, EditEventFragment.class.getSimpleName())
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

    }
}
