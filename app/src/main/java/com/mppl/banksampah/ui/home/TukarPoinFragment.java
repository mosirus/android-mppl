package com.mppl.banksampah.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mppl.banksampah.R;


public class TukarPoinFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_tukar_poin, container, false);
        return root;
    }

    @Override
    public void onClick(View v) {

    }
}
