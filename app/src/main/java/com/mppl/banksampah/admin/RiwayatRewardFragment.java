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


public class RiwayatRewardFragment extends Fragment implements OnClickListener{

    private Button btnListReward;
    private Button btnPermintaanReward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_riwayat_reward, container, false);

        btnListReward = root.findViewById(R.id.btnReward_listReward);
        btnListReward.setOnClickListener(this);

        btnPermintaanReward = root.findViewById(R.id.btnReward_permintaanReward);
        btnPermintaanReward.setOnClickListener(this);

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
        if(v.getId() == R.id.btnReward_permintaanReward){
            PermintaanRewardFragment fragment = new PermintaanRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, PermintaanRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }


    }
}
