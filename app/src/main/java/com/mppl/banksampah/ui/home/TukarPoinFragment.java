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

    private Button btnListBarang;
    private RecyclerView rv_listBarang;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_tukar_poin, container, false);

        return root;
    }

    @Override
    public void onClick(View v) {


    }
}
