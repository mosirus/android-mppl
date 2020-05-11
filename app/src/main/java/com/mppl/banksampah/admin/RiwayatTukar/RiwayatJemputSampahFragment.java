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
import com.mppl.banksampah.user.model.JemputSampahUser;

import java.util.ArrayList;

public class RiwayatJemputSampahFragment extends Fragment {
    private RecyclerView rvAntarSampah;
    private RiwayatTukarSampahJemputAdapter adapter;

    private ArrayList<JemputSampahUser> RiwayatTukarSampahArrayList;

    private TextView tvBelumRequest;

    public RiwayatJemputSampahFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_riwayat_jemput_sampah, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAntarSampah = view.findViewById(R.id.rvAntar);
        rvAntarSampah.setHasFixedSize(true);
        rvAntarSampah.setLayoutManager(new LinearLayoutManager(getContext()));

        tvBelumRequest = view.findViewById(R.id.tvBelumRequest);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference databaseReferenceJemput = FirebaseDatabase.getInstance().getReference().child("JemputSampah");

        RiwayatTukarSampahArrayList = new ArrayList<>();

        databaseReferenceJemput.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String userEmail = dataSnapshot1.getKey();
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        JemputSampahUser riwayatPoin = dataSnapshot2.getValue(JemputSampahUser.class);
                        if (riwayatPoin.getStatus().equals("Berhasil") || riwayatPoin.getStatus().equals("Tidak berhasil")) {
                            riwayatPoin.setEmail(userEmail);
                            RiwayatTukarSampahArrayList.add(riwayatPoin);
                        }
                    }
                }
                adapter = new RiwayatTukarSampahJemputAdapter(getContext(), RiwayatTukarSampahArrayList);
                rvAntarSampah.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
