package com.mppl.banksampah.user.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.StatusAntarAdapter;
import com.mppl.banksampah.user.model.StatusAntar;

import java.util.ArrayList;
import java.util.Objects;

public class StatusAntarFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvStatusAntar;
    private StatusAntarAdapter statusAntarAdapter;

    private ArrayList<StatusAntar> daftarStatusAntar;

    private TextView tvBelumRequest;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_status_antar, container, false);
        Button btnIsiForm = root.findViewById(R.id.btn_isi_form);
        btnIsiForm.setOnClickListener(this);
        tvBelumRequest = root.findViewById(R.id.tvBelumRequest);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvStatusAntar = view.findViewById(R.id.rvStatusAntar);
        rvStatusAntar.setHasFixedSize(true);
        rvStatusAntar.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.','_');
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AntarSampah").child(currentuserId);
        daftarStatusAntar = new ArrayList<StatusAntar>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    StatusAntar statusAntar = dataSnapshot1.getValue(StatusAntar.class);
                    daftarStatusAntar.add(statusAntar);
                }
                if (daftarStatusAntar.isEmpty()){
                    tvBelumRequest.setVisibility(View.VISIBLE);
                } else {
                    statusAntarAdapter = new StatusAntarAdapter(getActivity(), daftarStatusAntar);
                    rvStatusAntar.setAdapter(statusAntarAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_isi_form) {
            AntarSampahFragment fragment = new AntarSampahFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, AntarSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }
}
