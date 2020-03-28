package com.mppl.banksampah.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.mppl.banksampah.R;

import org.jetbrains.annotations.NotNull;


public class TukarPoinFragment extends Fragment implements View.OnClickListener {

    private Button btnListKupon;
    private Button btnstatus;
    private RecyclerView rv_listBarang;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_tukar_poin, container, false);

        btnListKupon = root.findViewById(R.id.ftpbtn_listkupon);
        btnListKupon.setOnClickListener(this);

        btnstatus = root.findViewById(R.id.ftpbtn_status);
        btnstatus.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ftpbtn_listkupon){
            ListKuponFragment fragment = new ListKuponFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ListKuponFragment.class.getSimpleName())
                            .addToBackStack(null).commit();
        }
        else if(v.getId() == R.id.ftpbtn_status){
            StatusTukarPoinFragment fragment = new StatusTukarPoinFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, StatusTukarPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }
}
