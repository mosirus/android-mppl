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
import com.mppl.banksampah.adapter.DaftarAntarSampahUserAdapter;
import com.mppl.banksampah.user.model.AntarSampahUser;
import com.mppl.banksampah.user.ui.home.AntarSampahFragment;

import java.util.ArrayList;
import java.util.Objects;


public class PermintaanAntarFragment extends Fragment implements OnClickListener{
    private Button btnIsiForm;
    private RecyclerView rvListPermintaanAntar;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String GetUserID;

    private ArrayList<AntarSampahUser> listAntarSampahUser;
    private DaftarAntarSampahUserAdapter antarSampahUserAdapter;


    private String namaPeminta[] = {"Pangondion K Naibaho","Zephyr Sensei","Lae Monang","Sulastri Tambunan","Angel Napitupulu"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permintaan_antar, container, false);

        rvListPermintaanAntar = root.findViewById(R.id.pa_listPermintaan);
        rvListPermintaanAntar.setHasFixedSize(true);
        rvListPermintaanAntar.setLayoutManager(new LinearLayoutManager(getContext()));
        //rvListPermintaanAntar.setAdapter(new SimpleRVAdapter(namaPeminta));

        btnIsiForm = root.findViewById(R.id.btn_isi_form);
        btnIsiForm.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.','_');
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("AntarSampah");
        listAntarSampahUser = new ArrayList<AntarSampahUser>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    AntarSampahUser antarSampahUser = snapshot.getValue(AntarSampahUser.class);
                    listAntarSampahUser.add(antarSampahUser);
                }
                antarSampahUserAdapter = new DaftarAntarSampahUserAdapter(getActivity(),listAntarSampahUser);
                rvListPermintaanAntar.setAdapter(antarSampahUserAdapter);
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
        if(v.getId() == R.id.btn_isi_form){
            TerimaSampahFragment fragment = new TerimaSampahFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, TerimaSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }


}
