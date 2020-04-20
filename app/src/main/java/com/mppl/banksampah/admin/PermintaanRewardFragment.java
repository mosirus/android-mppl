package com.mppl.banksampah.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.mppl.banksampah.R;


public class PermintaanRewardFragment extends Fragment implements OnClickListener{
    private Button btnListReward;
    private Button btnRiwayatReward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permintaan_reward, container, false);

        btnListReward = root.findViewById(R.id.btnReward_listReward);
        btnListReward.setOnClickListener(this);

        btnRiwayatReward = root.findViewById(R.id.btnReward_riwayatReward);
        btnRiwayatReward.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnReward_listReward){
            KuponFragment fragment = new KuponFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, KuponFragment.class.getSimpleName())
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
