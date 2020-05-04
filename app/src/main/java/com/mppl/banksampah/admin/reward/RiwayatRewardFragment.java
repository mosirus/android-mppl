package com.mppl.banksampah.admin.reward;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.DaftarRiwayatRewardAdminAdapter;
import com.mppl.banksampah.user.model.RequestedReward;

import java.util.ArrayList;


public class RiwayatRewardFragment extends Fragment implements OnClickListener{

    private Button btnListReward;
    private Button btnPermintaanReward;

    private RecyclerView rvListRiwayatReward;
    private ArrayList<RequestedReward> listRequestedRewards;
    private DaftarRiwayatRewardAdminAdapter riwayatRewardAdminAdapter;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_riwayat_reward, container, false);

        btnListReward = root.findViewById(R.id.btnReward_listReward);
        btnListReward.setOnClickListener(this);

        btnPermintaanReward = root.findViewById(R.id.btnReward_permintaanReward);
        btnPermintaanReward.setOnClickListener(this);

        rvListRiwayatReward = root.findViewById(R.id.rv_list_riwayatreward);
        rvListRiwayatReward.setHasFixedSize(true);
        rvListRiwayatReward.setLayoutManager(new LinearLayoutManager(getContext()));

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        getEmail = user.getEmail().replace(".", "_");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("RequestRewardUser");
        listRequestedRewards = new ArrayList<RequestedReward>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        RequestedReward requestedReward = snapshot1.getValue(RequestedReward.class);
                        listRequestedRewards.add(requestedReward);
                    }
                    riwayatRewardAdminAdapter = new DaftarRiwayatRewardAdminAdapter(getActivity(),listRequestedRewards);
                    rvListRiwayatReward.setAdapter(riwayatRewardAdminAdapter);
                }
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
        if(v.getId() == R.id.btnReward_listReward){
            KuponFragment fragment = new KuponFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentRiwayatReward, fragment, KuponFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_permintaanReward){
            PermintaanRewardFragment fragment = new PermintaanRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentRiwayatReward, fragment, PermintaanRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }


    }
}
