package com.mppl.banksampah.user.ui.tentang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.EventAdapterUser;
import com.mppl.banksampah.user.model.EventUser;
import com.mppl.banksampah.user.ui.home.DetailEventFragment;

import java.util.ArrayList;

public class TentangFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private RecyclerView rvDaftarEvent;
    private EventAdapterUser adapter;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String GetUserID;
    private ArrayList<EventUser> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tentang, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDaftarEvent = view.findViewById(R.id.rvDaftarEvent);

        rvDaftarEvent.setHasFixedSize(true);
        rvDaftarEvent.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        database =  FirebaseDatabase.getInstance();
        reference = database.getReference().child("Event");
        list = new ArrayList<EventUser>();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    EventUser event = snapshot.getValue(EventUser.class);
                    list.add(event);
                }
                adapter = new EventAdapterUser(getActivity(), list);
                rvDaftarEvent.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.setOnItemCallback(new EventAdapterUser.OnItemCallback() {
                    @Override
                    public void onItemclicked(EventUser data) {
                        String nameEvent = data.getNamaEvent();
                        String timeEvent = data.getWaktuEvent();
                        String locEvent = data.getTempatEvent();
                        String descEvent = data.getDescEvent();
                        String URLEvent = data.getURLEvent();

                        Bundle detailEventBundle = new Bundle();
                        detailEventBundle.putString("namaEvent",nameEvent);
                        detailEventBundle.putString("waktuEvent",timeEvent);
                        detailEventBundle.putString("tempatEvent",locEvent);
                        detailEventBundle.putString("descEvent", descEvent);
                        detailEventBundle.putString("urlevent", URLEvent);

                        DetailEventFragment fragment = new DetailEventFragment();
                        fragment.setArguments(detailEventBundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, DetailEventFragment.class.getSimpleName())
                                .addToBackStack(null).commit();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}