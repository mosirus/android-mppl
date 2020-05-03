package com.mppl.banksampah.user.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.DaftarBarangUserAdapter;
import com.mppl.banksampah.admin.model.Reward;

import java.util.ArrayList;


public class KuponTukarPoinFragment extends Fragment implements View.OnClickListener{
    private Button btnListBarang;
    private Button btnStTukarPoin;
    private TextView tvPoinUser;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    private String getEmail;

    private ArrayList<Reward> listKupon;
    private DaftarBarangUserAdapter barangUserAdapter;
    private RecyclerView rvListKupon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kupon_tukar_poin, container, false);

        btnListBarang = root.findViewById(R.id.ftpbtn_listbarang);
        btnListBarang.setOnClickListener(this);

        btnStTukarPoin = root.findViewById(R.id.ftpbtn_status);
        btnStTukarPoin.setOnClickListener(this);

        tvPoinUser = root.findViewById(R.id.textView_point);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        getEmail = user.getEmail().replace(".","_");

        rvListKupon = root.findViewById(R.id.rvtp_list_kupon);
        rvListKupon.setHasFixedSize(true);
        rvListKupon.setLayoutManager(new LinearLayoutManager(getContext()));

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Reward");
        listKupon = new ArrayList<Reward>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String jenisParam = "Kupon";
                    if(dataSnapshot1.child("jenisReward").getValue().toString().equals(jenisParam)){
                        Reward reward = dataSnapshot1.getValue(Reward.class);
                        listKupon.add(reward);
                    }
                }
                barangUserAdapter = new DaftarBarangUserAdapter(getActivity(), listKupon);
                rvListKupon.setAdapter(barangUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });

        reference2 = database.getReference().child("Users").child(getEmail);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvPoinUser.setText(dataSnapshot.child("point").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ftpbtn_listbarang){
            TukarPoinFragment fragment = new TukarPoinFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,fragment, TukarPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.ftpbtn_status){
            StatusTukarPoinFragment fragment = new StatusTukarPoinFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, StatusTukarPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }
}
