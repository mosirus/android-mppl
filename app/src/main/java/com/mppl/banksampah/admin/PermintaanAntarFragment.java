package com.mppl.banksampah.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mppl.banksampah.R;


public class PermintaanAntarFragment extends Fragment implements OnClickListener{
    private Button btnIsiForm;
    private RecyclerView rvListPermintaanAntar;

    private String namaPeminta[] = {"Pangondion K Naibaho","Zephyr Sensei","Lae Monang","Sulastri Tambunan","Angel Napitupulu"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permintaan_antar, container, false);

        rvListPermintaanAntar = root.findViewById(R.id.pa_listPermintaan);
        rvListPermintaanAntar.setHasFixedSize(true);
        rvListPermintaanAntar.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListPermintaanAntar.setAdapter(new SimpleRVAdapter(namaPeminta));

        btnIsiForm = root.findViewById(R.id.btn_isi_form);
        btnIsiForm.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_isi_form){
            TerimaSampahFragment fragment = new TerimaSampahFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, TerimaSampahFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<ListViewHolder>{
        private String[] datas;
        public SimpleRVAdapter(String[] dataset){
            datas = dataset;
        }
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_permintaan_antar,parent,false);
            ListViewHolder viewHolder = new ListViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            holder.namaPeminta.setText(datas[position]);
        }

        public int getItemCount(){
            return datas.length;
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView namaPeminta;
        public ListViewHolder(View itemView){
            super(itemView);
            namaPeminta = itemView.findViewById(R.id.tv_person_pa);
        }
    }
}
