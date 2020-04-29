package com.mppl.banksampah.user.ui.riwayatpoin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;

import java.util.Objects;

public class RiwayatPoinFragment extends Fragment{

    private TextView tvJumlahPoin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_riwayatpoin, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tab_riwayat);
        ViewPager viewPager = root.findViewById(R.id.view_pager_riwayat);
        ViewPagerAdapter adapter = new ViewPagerAdapter( getChildFragmentManager());

        tvJumlahPoin = root.findViewById(R.id.textView_point);

        adapter.AddFragment( new PoinMasukFragment(), getResources().getString(R.string.poinmasuk_tab));
        adapter.AddFragment( new PoinKeluarFragment(), getResources().getString(R.string.poinkeluar_tab));

        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager(viewPager);

        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.', '_');
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String poin = Objects.requireNonNull(dataSnapshot.child("point").getValue()).toString();
                tvJumlahPoin.setText(poin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }


}