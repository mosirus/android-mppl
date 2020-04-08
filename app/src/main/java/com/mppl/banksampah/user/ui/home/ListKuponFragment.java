package com.mppl.banksampah.user.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mppl.banksampah.R;

import org.jetbrains.annotations.NotNull;


public class ListKuponFragment extends Fragment implements View.OnClickListener {
    private Button btnListBarang;
    private Button btnStatus;
    private RecyclerView rv_listKupon;

    private String[] nama_kupon = {"Potongan Uang Kuliah Rp250.000,-","Potongan Uang Kuliah Rp500.000,-"};

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list_kupon, container, false);

        btnListBarang = root.findViewById(R.id.ftpbtn_listbarang);
        btnListBarang.setOnClickListener(this);

        btnStatus = root.findViewById(R.id.ftpbtn_status);
        btnStatus.setOnClickListener(this);

        rv_listKupon = root.findViewById(R.id.rvtp_list_kupon);
        rv_listKupon.setHasFixedSize(true);
        rv_listKupon.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_listKupon.setAdapter(new SimpleRVAdapter(nama_kupon));

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
        else if(v.getId() == R.id.ftpbtn_status){
            StatusTukarPoinFragment fragment = new StatusTukarPoinFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, StatusTukarPoinFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<ListViewHolder>{
        private String[] data;
        public SimpleRVAdapter(String[] dataArgs){
            data = dataArgs;
        }
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_kupon,parent,false);
            ListViewHolder viewHolder = new ListViewHolder(view);
            return viewHolder;
        }
        public void onBindViewHolder(ListViewHolder holder, int position){
            holder.namaKupon.setText(data[position]);
        }
        public int getItemCount(){
            return data.length;
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView namaKupon;
        public ListViewHolder(View itemView) {
            super(itemView);
            namaKupon = itemView.findViewById(R.id.tv_list_kupon);
        }
    }

}
