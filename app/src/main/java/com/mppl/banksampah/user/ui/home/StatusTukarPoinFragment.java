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
import com.mppl.banksampah.adapter.DaftarStRequestRewardUserAdapter;
import com.mppl.banksampah.user.model.RequestedReward;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class StatusTukarPoinFragment extends Fragment implements View.OnClickListener{

    private Button btnListBarang;
    private Button btnListKupon;
    private RecyclerView rv_ListStatus;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getEmailUser;

    private ArrayList<RequestedReward> listRequestedReward;
    private DaftarStRequestRewardUserAdapter stRequestRewardUserAdapter;

    private String[] tanggal_status = {"18 Agustus 2020","04 November 2020","19 Desember 2020"};

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_status_tukar_poin, container, false);

        btnListBarang = root.findViewById(R.id.ftpbtn_listbarang);
        btnListBarang.setOnClickListener(this);

        rv_ListStatus = root.findViewById(R.id.rv_list_sttp);
        rv_ListStatus.setHasFixedSize(true);
        rv_ListStatus.setLayoutManager(new LinearLayoutManager(getContext()));

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        getEmailUser = user.getEmail().replace(".", "_");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("RequestRewardUser");
        listRequestedReward = new ArrayList<RequestedReward>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            RequestedReward requestedReward = snapshot1.getValue(RequestedReward.class);
                            if(snapshot1.child("emailRequester").getValue().toString().equals(getEmailUser)){
                                listRequestedReward.add(requestedReward);
                            }
                        }
                        stRequestRewardUserAdapter = new DaftarStRequestRewardUserAdapter(getActivity(),listRequestedReward);
                        rv_ListStatus.setAdapter(stRequestRewardUserAdapter);
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
        if(v.getId() == R.id.ftpbtn_listbarang){
            TukarPoinFragment fragment = new TukarPoinFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, TukarPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }

}
