package com.mppl.banksampah.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mppl.banksampah.R;
import com.mppl.banksampah.ui.akun.EditProfileFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    private Button btnAntar;
    private Button btnJemput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        btnAntar = root.findViewById(R.id.btn_antar);
        btnAntar.setOnClickListener(this);
        btnJemput = root.findViewById(R.id.btn_jemput);
        btnJemput.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_antar) {
            AntarSampahFragment fragment = new AntarSampahFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, AntarSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

        else if (v.getId() == R.id.btn_jemput) {
            JemputSampahFragment fragment = new JemputSampahFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, JemputSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}