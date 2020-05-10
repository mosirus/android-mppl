package com.mppl.banksampah.admin.terimasampah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;


public class TerimaSampahFragmentContainer extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_terimasampah, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tab_terima_sampah);
        ViewPager viewPager = root.findViewById(R.id.view_pager_terima_sampah);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.AddFragment(new TerimaSampahFragment(), getResources().getString(R.string.isi_form));
        adapter.AddFragment(new PermintaanAntarFragment(), getResources().getString(R.string.permintaan_antar));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }


}
