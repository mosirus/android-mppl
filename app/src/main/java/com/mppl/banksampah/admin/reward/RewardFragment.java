// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.admin.reward;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;

public class RewardFragment extends Fragment implements View.OnClickListener{

    private Button tambahReward;
    private ViewPager rewardViewPager;
    private ViewPagerAdapter vpaReward;
    private TabLayout tlReward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reward, container, false);

        tambahReward = root.findViewById(R.id.btn_tambah_reward);
        tambahReward.setOnClickListener(this);
        tlReward = root.findViewById(R.id.tab_layout_reward);
        rewardViewPager = root.findViewById(R.id.view_pager_reward);

        vpaReward = new ViewPagerAdapter(getChildFragmentManager());
        vpaReward.AddFragment(new KuponFragment(),"DAFTAR");
        vpaReward.AddFragment(new PermintaanRewardFragment(),"PERMINTAAN");
        vpaReward.AddFragment(new RiwayatRewardFragment(),"RIWAYAT");

        rewardViewPager.setAdapter(vpaReward);
        tlReward.setupWithViewPager(rewardViewPager);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_tambah_reward){
            TambahRewardFragment fragment = new TambahRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, TambahRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }
}
