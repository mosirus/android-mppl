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
import com.mppl.banksampah.adapter.RiwayatPoinAdapter;
import com.mppl.banksampah.user.model.RiwayatPoin;

import java.util.ArrayList;
import java.util.Objects;

public class PoinMasukFragment extends Fragment {

    private RecyclerView rvPoinMasuk;
    private RiwayatPoinAdapter adapter;

    private ArrayList<RiwayatPoin> riwayatPoinArrayList;

    private TextView tvBelumRequest;

    public PoinMasukFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poin_masuk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPoinMasuk = view.findViewById(R.id.rvPoinMasuk);
        rvPoinMasuk.setHasFixedSize(true);
        rvPoinMasuk.setLayoutManager(new LinearLayoutManager(getContext()));

        tvBelumRequest = view.findViewById(R.id.tvBelumRequest);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.', '_');
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AntarSampah").child(currentuserId);
        DatabaseReference databaseReferenceJemput = FirebaseDatabase.getInstance().getReference().child("JemputSampah").child(currentuserId);

        riwayatPoinArrayList = new ArrayList<RiwayatPoin>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RiwayatPoin riwayatPoin = dataSnapshot1.getValue(RiwayatPoin.class);
                    if (riwayatPoin.getStatus().equals("Berhasil")) {
                        riwayatPoinArrayList.add(riwayatPoin);
                    }
                }

                if (riwayatPoinArrayList.isEmpty()) {
                    tvBelumRequest.setVisibility(View.VISIBLE);
                } else {
                    adapter = new RiwayatPoinAdapter(getActivity(), riwayatPoinArrayList);
                    rvPoinMasuk.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
