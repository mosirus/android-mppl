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

public class TerimaSampahFragment extends Fragment implements OnClickListener{
    private Button btnPermintaanAntar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_isiformterimasampah_admin, container, false);

        btnPermintaanAntar = root.findViewById(R.id.btn_status_antar);
        btnPermintaanAntar.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_status_antar){
            PermintaanAntarFragment fragment = new PermintaanAntarFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, PermintaanAntarFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}

