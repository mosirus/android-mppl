package com.mppl.banksampah.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.DaftarRewardAdapter;
import com.mppl.banksampah.admin.model.Reward;

import java.util.ArrayList;

public class KuponFragment extends Fragment implements OnClickListener{

    private Button btnTambahReward;
    private Button btnPermintaanReward;
    private Button btnRiwayatReward;

    private RecyclerView rvListReward;

    private String nama_reward[] = {"Reward1","Reward2","Reward3","Reward4"};

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String GetUserID;

    private ArrayList<Reward> listReward;
    private DaftarRewardAdapter rewardAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kupon, container, false);

        btnTambahReward = root.findViewById(R.id.btn_tambah_reward);
        btnTambahReward.setOnClickListener(this);

        btnPermintaanReward = root.findViewById(R.id.btnReward_permintaanReward);
        btnPermintaanReward.setOnClickListener(this);

        btnRiwayatReward = root.findViewById(R.id.btnReward_riwayatReward);
        btnRiwayatReward.setOnClickListener(this);

        rvListReward = root.findViewById(R.id.rv_list_reward);
        rvListReward.setHasFixedSize(true);
        rvListReward.setLayoutManager(new LinearLayoutManager(getContext()));
        //rvListReward.setAdapter(new SimpleRVAdapter(nama_reward));

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Reward");
        listReward = new ArrayList<Reward>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Reward reward = snapshot.getValue(Reward.class);
                    listReward.add(reward);
                }
                rewardAdapter = new DaftarRewardAdapter(getActivity(), listReward);
                rvListReward.setAdapter(rewardAdapter);
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
        if(v.getId() == R.id.btn_tambah_reward){
            TambahRewardFragment fragment = new TambahRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, TambahRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_permintaanReward){
            PermintaanRewardFragment fragment = new PermintaanRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, PermintaanRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_riwayatReward){
            RiwayatRewardFragment fragment = new RiwayatRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, RiwayatRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }
}

