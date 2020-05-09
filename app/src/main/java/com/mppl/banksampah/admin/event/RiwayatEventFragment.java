package com.mppl.banksampah.admin.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.mppl.banksampah.adapter.RiwayatEventAdapter;
import com.mppl.banksampah.admin.model.EventAdmin;

import java.util.ArrayList;

public class RiwayatEventFragment extends Fragment {

    private RecyclerView rvRiwayatEvent;
    private RiwayatEventAdapter adapter;
    private ArrayList<EventAdmin> list;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String GetUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_riwayat_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRiwayatEvent = view.findViewById(R.id.rvRiwayatEvent);
        rvRiwayatEvent.setHasFixedSize(true);
        rvRiwayatEvent.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Event");
        list = new ArrayList<EventAdmin>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    EventAdmin event = snapshot.getValue(EventAdmin.class);
                    list.add(event);
                }
                adapter = new RiwayatEventAdapter(getActivity(), list);
                rvRiwayatEvent.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
