package com.mppl.banksampah.admin;

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
import com.mppl.banksampah.adapter.DaftarRequestRewardUserAdapter;
import com.mppl.banksampah.user.model.RequestedReward;

import java.util.ArrayList;


public class PermintaanRewardFragment extends Fragment implements OnClickListener{
    private Button btnListReward;
    private Button btnRiwayatReward;

    private RecyclerView rvListRequestedReward;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getEmailUser;

    private ArrayList<RequestedReward> listRequestedReward;
    private DaftarRequestRewardUserAdapter requestRewardUserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permintaan_reward, container, false);

        btnListReward = root.findViewById(R.id.btnReward_listReward);
        btnListReward.setOnClickListener(this);

        btnRiwayatReward = root.findViewById(R.id.btnReward_riwayatReward);
        btnRiwayatReward.setOnClickListener(this);

        rvListRequestedReward = root.findViewById(R.id.rv_item_requestReward);
        rvListRequestedReward.setHasFixedSize(true);
        rvListRequestedReward.setLayoutManager(new LinearLayoutManager(getContext()));

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        getEmailUser = user.getEmail();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("RequestRewardUser");
        listRequestedReward = new ArrayList<RequestedReward>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        String statParam = "Sedang Diproses";
                        RequestedReward requestedReward = snapshot1.getValue(RequestedReward.class);
                        if(requestedReward.getStatusRequested().equals(statParam)){
                            listRequestedReward.add(requestedReward);
                        }
                    }
                    requestRewardUserAdapter = new DaftarRequestRewardUserAdapter(getActivity(),listRequestedReward);
                    rvListRequestedReward.setAdapter(requestRewardUserAdapter);
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
            fragmentManager.beginTransaction().replace(R.id.fragmentPermintaanReward, fragment, KuponFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_riwayatReward){
            RiwayatRewardFragment fragment = new RiwayatRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentPermintaanReward, fragment, RiwayatRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }


    }
}
