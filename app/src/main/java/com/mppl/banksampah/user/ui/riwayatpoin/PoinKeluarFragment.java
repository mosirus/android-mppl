package com.mppl.banksampah.user.ui.riwayatpoin;


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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.RiwayatPoinKeluarAdapter;
import com.mppl.banksampah.user.model.RequestedReward;

import java.util.ArrayList;
import java.util.Objects;

public class PoinKeluarFragment extends Fragment {

    private RecyclerView rvPoinKeluar;
    private RiwayatPoinKeluarAdapter adapter;

    private ArrayList<RequestedReward> riwayatPoinKeluarArrayList;

    private TextView tvBelumRequest;

    public PoinKeluarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poin_keluar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPoinKeluar = view.findViewById(R.id.rvPoinKeluar);
        rvPoinKeluar.setHasFixedSize(true);
        rvPoinKeluar.setLayoutManager(new LinearLayoutManager(getContext()));

        tvBelumRequest = view.findViewById(R.id.tvBelumRequest);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.', '_');
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("RequestRewardUser").child(currentuserId);

        riwayatPoinKeluarArrayList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RequestedReward requestedReward = dataSnapshot1.getValue(RequestedReward.class);
                    if (requestedReward.getStatusRequested().equals("Berhasil") || requestedReward.getStatusRequested().equals("Tidak Berhasil")) {
                        riwayatPoinKeluarArrayList.add(requestedReward);
                    }
                }
                if (riwayatPoinKeluarArrayList.isEmpty()) {
                    tvBelumRequest.setVisibility(View.VISIBLE);
                } else {
                    adapter = new RiwayatPoinKeluarAdapter(getActivity(), riwayatPoinKeluarArrayList);
                    rvPoinKeluar.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
