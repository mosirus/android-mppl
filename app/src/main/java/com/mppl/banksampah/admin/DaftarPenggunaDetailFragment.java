package com.mppl.banksampah.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mppl.banksampah.R;

public class DaftarPenggunaDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_daftarpengguna_isiform_admin, container, false);
        return root;
    }

//    private void showSelectedHero(DaftarPengguna daftarPengguna) {
//        Toast.makeText(getActivity(), "Kamu memilih " + daftarPengguna.getEmail(), Toast.LENGTH_SHORT).show();
//    }
}
