package com.mppl.banksampah.admin.RiwayatTukar;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.RiwayatTukarSampahAdapter;
import com.mppl.banksampah.user.model.AntarSampahUser;

import java.util.ArrayList;

public class RiwayatTerimaSampahFragment extends Fragment {

    private RecyclerView rvAntarSampah;
    private RiwayatTukarSampahAdapter adapter;

    private ArrayList<AntarSampahUser> RiwayatTukarSampahArrayList;

    public RiwayatTerimaSampahFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tukar_sampah_antar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAntarSampah = view.findViewById(R.id.rvAntar);
        rvAntarSampah.setHasFixedSize(true);
        rvAntarSampah.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AntarSampah");

        RiwayatTukarSampahArrayList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String userEmail = dataSnapshot1.getKey();
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        AntarSampahUser riwayatPoin = dataSnapshot2.getValue(AntarSampahUser.class);
                        if (riwayatPoin.getStatus().equals("Berhasil") || riwayatPoin.getStatus().equals("Tidak berhasil")) {
                            if (riwayatPoin.getCurrentId().equals("")){
                                riwayatPoin.setCurrentId(userEmail);
                                RiwayatTukarSampahArrayList.add(riwayatPoin);
                            }
                        }
                    }
                }
                adapter = new RiwayatTukarSampahAdapter(getContext(), RiwayatTukarSampahArrayList);
                rvAntarSampah.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });

//      DatabaseReference databaseReferenceJemput = FirebaseDatabase.getInstance().getReference().child("JemputSampah").child(currentuserId);
//        databaseReferenceJemput.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    RiwayatPoin riwayatAntar = dataSnapshot1.getValue(RiwayatPoin.class);
//                    if (riwayatAntar.getStatus().equals("Berhasil")) {
//                        RiwayatTukarSampahArrayList.add(riwayatAntar);
//                    }
//                }
//
//                if (RiwayatTukarSampahArrayList.isEmpty()) {
//                    tvBelumRequest.setVisibility(View.VISIBLE);
//                } else {
//                    adapter = new RiwayatTukarSampahAdapter(getActivity(), RiwayatTukarSampahArrayList);
//                    rvAntarSampah.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    }
}

