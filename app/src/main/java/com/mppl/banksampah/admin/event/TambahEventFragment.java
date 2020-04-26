package com.mppl.banksampah.admin.event;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mppl.banksampah.R;

public class TambahEventFragment extends Fragment implements View.OnClickListener {

    private Button btnTambah;
    private Button btnBatal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_tambah_event_admin, container, false);

        btnTambah = root.findViewById(R.id.btn_tambah);
        btnTambah.setOnClickListener(this);
        btnBatal = root.findViewById(R.id.btn_batal);
        btnBatal.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tambah){
            DaftarEventFragment daftarEventFragment = new DaftarEventFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.drawer_layout, daftarEventFragment, DaftarEventFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }else if (v.getId() == R.id.btn_batal) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Batal")
                    .setMessage("Anda membatalkan untuk menambah Event?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DaftarEventFragment daftarEventFragment = new DaftarEventFragment();

                            FragmentManager fragmentManager = getChildFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragment_container, daftarEventFragment, DaftarEventFragment.class.getSimpleName())
                                    .addToBackStack(null).commit();
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            EventFragment daftarEventFragment = new EventFragment();

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.fragment_container, daftarEventFragment, EventFragment.class.getSimpleName())
                                    .addToBackStack(null).commit();
                        }
                    })
                    .show();
        }
    }
}
