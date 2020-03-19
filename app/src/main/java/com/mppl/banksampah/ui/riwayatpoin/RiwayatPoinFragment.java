package com.mppl.banksampah.ui.riwayatpoin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;

public class RiwayatPoinFragment extends Fragment{

    private RiwayatPoinViewModel riwayatViewModel;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        riwayatViewModel = ViewModelProviders.of(this).get(RiwayatPoinViewModel.class);
        View root = inflater.inflate(R.layout.fragment_riwayatpoin, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tab_riwayat);
        viewPager = root.findViewById(R.id.view_pager_riwayat);
        ViewPagerAdapter adapter = new ViewPagerAdapter( getChildFragmentManager());

        adapter.AddFragment( new PoinMasukFragment(), getResources().getString(R.string.poinmasuk_tab));
        adapter.AddFragment( new PoinKeluarFragment(), getResources().getString(R.string.poinkeluar_tab));

        viewPager.setAdapter( adapter );
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }


}