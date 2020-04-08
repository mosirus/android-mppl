package com.mppl.banksampah.user.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
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
import com.mppl.banksampah.user.ui.tentang.StatusAntar;

import java.util.ArrayList;
import java.util.Objects;

public class StatusJemputFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private DatabaseReference databaseReference;

    private RecyclerView rvStatusJemput;
    private StatusAntarAdapter statusJemputAdapter;

    private ArrayList<StatusAntar> daftarStatusJemput;

    private Button btnIsiForm;

    private Button btnStatusJemput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_status_jemput, container, false);

        btnIsiForm = root.findViewById(R.id.btn_isi_form);
        btnIsiForm.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvStatusJemput = view.findViewById(R.id.rvStatusJemput);
        rvStatusJemput.setHasFixedSize(true);
        rvStatusJemput.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentuserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("JemputSampah").child(currentuserId);
        daftarStatusJemput = new ArrayList<StatusAntar>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    StatusAntar statusAntar = dataSnapshot1.getValue(StatusAntar.class);
                    daftarStatusJemput.add(statusAntar);
                }
                statusJemputAdapter = new StatusAntarAdapter(getActivity(), daftarStatusJemput);
                rvStatusJemput.setAdapter(statusJemputAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_isi_form){
            JemputSampahFragment fragment = new JemputSampahFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, JemputSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }
}
