package com.mppl.banksampah.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mppl.banksampah.R;

public class KuponFragment extends Fragment implements OnClickListener{

    private Button btnTambahReward;
    private Button btnPermintaanReward;
    private Button btnRiwayatReward;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kupon, container, false);

        btnTambahReward = root.findViewById(R.id.btn_tambah_reward);
        btnTambahReward.setOnClickListener(this);

        btnPermintaanReward = root.findViewById(R.id.btnReward_permintaanReward);
        btnPermintaanReward.setOnClickListener(this);

        btnRiwayatReward = root.findViewById(R.id.btnReward_riwayatReward);
        btnRiwayatReward.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_tambah_reward){
            TambahRewardFragment fragment = new TambahRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, TambahRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_permintaanReward){
            PermintaanRewardFragment fragment = new PermintaanRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, PermintaanRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_riwayatReward){
            RiwayatRewardFragment fragment = new RiwayatRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, RiwayatRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }
}

