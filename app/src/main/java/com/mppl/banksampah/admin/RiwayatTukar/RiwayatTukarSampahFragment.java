package com.mppl.banksampah.admin.RiwayatTukar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;
import com.mppl.banksampah.user.ui.home.JemputSampahFragment;

public class RiwayatTukarSampahFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_riwayat_tukar_sampah, container,false);

        TabLayout tabLayout = root.findViewById(R.id.tab_riwayat_tukar_sampah);
        ViewPager viewPager = root.findViewById(R.id.view_pager_riwayat_tukar);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.AddFragment(new RiwayatAntarSampahFragment(), "Riwayat Antar");
        adapter.AddFragment(new RiwayatTerimaSampahFragment(), "Riwayat Terima");
        adapter.AddFragment(new RiwayatJemputSampahFragment(), "Riwayat Jemput");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

}

