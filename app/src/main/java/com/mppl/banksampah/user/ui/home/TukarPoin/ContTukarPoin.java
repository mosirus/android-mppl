// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.user.ui.home.TukarPoin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;
import com.mppl.banksampah.admin.reward.KuponFragment;

import java.util.Objects;

public class ContTukarPoin extends Fragment {
    private ViewPager vpTukarPoin;
    private ViewPagerAdapter vpaTukarPoin;
    private TabLayout tlTukarPoin;
    private TextView tvPoinUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cont_tukar_poin, container, false);
        tlTukarPoin = root.findViewById(R.id.tab_layout_tukarpoin);
        vpTukarPoin = root.findViewById(R.id.view_pager_tukarpoin);

        vpaTukarPoin = new ViewPagerAdapter(getChildFragmentManager());
        vpaTukarPoin.AddFragment(new TukarPoinFragment(),"LIST BARANG");
        vpaTukarPoin.AddFragment(new KuponTukarPoinFragment(),"LIST KUPON");
        vpaTukarPoin.AddFragment(new StatusTukarPoinFragment(),"STATUS");

        vpTukarPoin.setAdapter(vpaTukarPoin);
        tlTukarPoin.setupWithViewPager(vpTukarPoin);

        tvPoinUser = root.findViewById(R.id.tv_poin_user);

        loadUserPoint();

        return root;
    }

    private void loadUserPoint() {
        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.', '_');
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String poin = Objects.requireNonNull(dataSnapshot.child("point").getValue()).toString();
                tvPoinUser.setText(poin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
