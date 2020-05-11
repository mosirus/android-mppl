package com.mppl.banksampah.admin.RiwayatTukar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.RiwayatTukarSampahJemputAdapter;
import com.mppl.banksampah.admin.JemputFragment;
import com.mppl.banksampah.user.model.RiwayatPoin;

import java.util.ArrayList;

public class RiwayatJemputSampahFragment extends Fragment {

    private RecyclerView rvJemput;
    private RiwayatTukarSampahJemputAdapter adapter;

    private ArrayList<JemputFragment> RiwayatTukarSampahArrayList;
    private String currentuserId;

    private TextView tvBelumRequest;

    public RiwayatJemputSampahFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tukar_sampah_antar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvJemput = view.findViewById(R.id.rvJemput);
        rvJemput.setHasFixedSize(true);
        rvJemput.setLayoutManager(new LinearLayoutManager(getContext()));

        tvBelumRequest = view.findViewById(R.id.tvBelumRequest);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("JemputSampah");

        RiwayatTukarSampahArrayList = new ArrayList<>();

        DatabaseReference databaseReferenceJemput = FirebaseDatabase.getInstance().getReference().child("JemputSampah").child(currentuserId);
        databaseReferenceJemput.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RiwayatPoin riwayatAntar = dataSnapshot1.getValue(RiwayatPoin.class);
                    if (riwayatAntar.getStatus().equals("Berhasil")) {
                        RiwayatTukarSampahArrayList.add(riwayatAntar);
                    }
                }

                if (RiwayatTukarSampahArrayList.isEmpty()) {
                    tvBelumRequest.setVisibility(View.VISIBLE);
                } else {
                    adapter = new RiwayatTukarSampahJemputAdapter(getActivity(), RiwayatTukarSampahArrayList);
                    rvJemput.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
        }
    }

