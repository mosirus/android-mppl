package com.mppl.banksampah.ui.Event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class EventAdminFragment extends Fragment {

    private EventAdminViewModel eventAdminViewModel;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        eventAdminViewModel = ViewModelProviders.of(this).get(EventAdminViewModel.class);
        View root= inflater.inflate(R.layout.fragment_event_admin, container, false);

        TabLayout tabLayout = root.findViewById(R.id.tab_event);
        viewPager = root.findViewById(R.id.view_pager_event);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.AddFragment(new DaftarEventFragment(), "Daftar");
        adapter.AddFragment(new RiwayatEventFragment(), "Riwayat");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }
}

