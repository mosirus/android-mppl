package com.mppl.banksampah.admin.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.mppl.banksampah.R;
import com.mppl.banksampah.adapter.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class EventFragment extends Fragment implements View.OnClickListener {

    private Button tambahEvent;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tambahEvent = view.findViewById(R.id.btn_tambah_event);
        tambahEvent.setOnClickListener(this);
        tabLayout = view.findViewById(R.id.tab_event);
        viewPager = view.findViewById(R.id.view_pager_event);

        Bundle event = new Bundle();

        if (event != null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());

            adapter.AddFragment(new DaftarEventFragment(), getResources().getString(R.string.daftar_event));
            adapter.AddFragment(new RiwayatEventFragment(), getResources().getString(R.string.riwayat_event));
            adapter.notifyDataSetChanged();

            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tambah_event){

            TambahEventFragment fragment = new TambahEventFragment();

            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, TambahEventFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();

//            TambahRewardFragment fragment = new TambahRewardFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, TambahRewardFragment.class.getSimpleName())
//                    .addToBackStack(null).commit();
        }
    }

//    private void returnToList() {
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new TambahEventFragment()).addToBackStack(null).commit();
//    }
}

