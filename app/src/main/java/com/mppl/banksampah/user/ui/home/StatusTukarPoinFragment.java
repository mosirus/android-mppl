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


public class StatusTukarPoinFragment extends Fragment implements View.OnClickListener{

    private Button btnListBarang;
    private Button btnListKupon;
    private RecyclerView rv_ListStatus;

    private String[] tanggal_status = {"18 Agustus 2020","04 November 2020","19 Desember 2020"};

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_status_tukar_poin, container, false);

        btnListBarang = root.findViewById(R.id.ftpbtn_listbarang);
        btnListBarang.setOnClickListener(this);

        rv_ListStatus = root.findViewById(R.id.rv_list_sttp);
        rv_ListStatus.setHasFixedSize(true);
        rv_ListStatus.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_ListStatus.setAdapter(new SimpleRVAdapter(tanggal_status));

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

    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<ListViewHolder>{
        private String[] data;
        public SimpleRVAdapter(String[] dataArgs){
            data = dataArgs;
        }
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_sttukarpoin, parent, false);
            ListViewHolder viewHolder = new ListViewHolder(view);
            return viewHolder;
        }
        public void onBindViewHolder(ListViewHolder holder, int position){
            holder.tvTanggal.setText(data[position]);
        }
        public int getItemCount(){
            return data.length;
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTanggal;
        public ListViewHolder(View itemView){
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tv_date_sttp);
        }

    }
}
