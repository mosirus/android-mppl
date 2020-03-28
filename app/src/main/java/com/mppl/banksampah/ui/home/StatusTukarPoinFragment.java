package com.mppl.banksampah.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mppl.banksampah.R;

import org.jetbrains.annotations.NotNull;


public class StatusTukarPoinFragment extends Fragment implements View.OnClickListener{

    private Button btnListBarang;
    private Button btnListKupon;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_status_tukar_poin, container, false);

        btnListBarang = root.findViewById(R.id.ftpbtn_listbarang);
        btnListBarang.setOnClickListener(this);

        btnListKupon = root.findViewById(R.id.ftpbtn_listkupon);
        btnListKupon.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ftpbtn_listbarang){
            TukarPoinFragment fragment = new TukarPoinFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, TukarPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        else if(v.getId() == R.id.ftpbtn_listkupon){
            ListKuponFragment fragment = new ListKuponFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ListKuponFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}
