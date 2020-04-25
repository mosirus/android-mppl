package com.mppl.banksampah.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.RiwayatTukarSampahAdapter;
import com.mppl.banksampah.ui.home.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class RiwayatTukarSampahFragment extends Fragment implements View.OnClickListener{

    private DatabaseReference refAntar;
    private DatabaseReference refJemput;

    private SimpleDateFormat dateFormatter;

    //private TextView pickedDate;
    private TextView tvTanggal;
    private TextView tvEmail;
    private TextView tvPoin;
    private TextView tvStatus;



    private RecyclerView rvRiwayatTukarSampah;
    private ArrayList<RiwayatTukarSampahFragment> listRiwayatTukar;
    private RiwayatTukarSampahAdapter riwayatTukarSampahAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_riwayat_tukar_sampah, container,false);

        tvTanggal = root.findViewById(R.id.tanggal_riwayat_tukar);
        tvEmail = root.findViewById(R.id.email_riwayat_tukar);
        tvPoin = root.findViewById(R.id.tvpoin_riwayat_tukar);
        tvStatus = root.findViewById(R.id.status_riwayat_tukar);

        refAntar = FirebaseDatabase.getInstance().getReference().child("AntarSampah");
        refJemput = FirebaseDatabase.getInstance().getReference().child("JemputSampah");


        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_pager_event){
            RiwayatTukarSampahFragment fragment = new RiwayatTukarSampahFragment();

            FragmentManager fragmentManager = getFragmentManager();
        } else if (v.getId() == R.id.btn_ok_riwayat_tukar){
            makeRequest();
        }

    }

    private void makeRequest() {
        final String refKey = refAntar.push().getKey();
        final String refKey1 = refJemput.push().getKey();
        if (validateForm()){
            refAntar.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            RiwayatTukarSampahFragment riwayatTukarSampahFragment = dataSnapshot1.getValue(RiwayatTukarSampahFragment.class);

                            listRiwayatTukar.add(riwayatTukarSampahFragment);

                        }
                    }

                    riwayatTukarSampahAdapter = new RiwayatTukarSampahAdapter(getActivity(), listRiwayatTukar);
                    rvRiwayatTukarSampah.setAdapter(riwayatTukarSampahAdapter);
            }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }); {




            }
        }

        refJemput.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                        RiwayatTukarSampahFragment riwayatTukarSampahFragment = dataSnapshot1.getValue(RiwayatTukarSampahFragment.class);

                        listRiwayatTukar.add(riwayatTukarSampahFragment);
                    }
                }
                riwayatTukarSampahAdapter = new RiwayatTukarSampahAdapter(getActivity(), listRiwayatTukar);
                rvRiwayatTukarSampah.setAdapter(riwayatTukarSampahAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean validateForm() {

        return false;
    }
}

