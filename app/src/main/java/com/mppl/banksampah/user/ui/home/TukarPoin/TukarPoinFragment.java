package com.mppl.banksampah.user.ui.home.TukarPoin;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class TukarPoinFragment extends Fragment implements View.OnClickListener {

    private Button btnstatus;
    private Button btnListKupon;
    private TextView tvPoinUser;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    private String getEmailUser;

    private ArrayList<Reward> listBarang;
    private DaftarBarangUserAdapter barangUserAdapter;
    private RecyclerView rvListBarang;

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_tukar_poin, container, false);

        rvListBarang = root.findViewById(R.id.rvtp_list_barang);
        rvListBarang.setHasFixedSize(true);
        rvListBarang.setLayoutManager(new LinearLayoutManager(getContext()));

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        getEmailUser = user.getEmail().replace(".","_");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Reward");
        listBarang = new ArrayList<Reward>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String jenisParam = "Barang";
                    if(dataSnapshot1.child("jenisReward").getValue().toString().equals(jenisParam)){
                        Reward reward = dataSnapshot1.getValue(Reward.class);
                        listBarang.add(reward);
                    }
                }
                barangUserAdapter = new DaftarBarangUserAdapter(getActivity(), listBarang);
                rvListBarang.setAdapter(barangUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {


    }

}
