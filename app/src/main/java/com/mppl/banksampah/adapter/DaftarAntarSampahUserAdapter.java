package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.AntarSampahUser;
import com.mppl.banksampah.user.model.User;

import java.util.ArrayList;

public class DaftarAntarSampahUserAdapter extends RecyclerView.Adapter<DaftarAntarSampahUserAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<AntarSampahUser> listAntarSampahUser;
    private ArrayList<User> listUser;

    public DaftarAntarSampahUserAdapter(Context context1, ArrayList<AntarSampahUser> listAntarSampahUser1){
        context = context1;
        listAntarSampahUser = listAntarSampahUser1;
    }
    private OnItemCallback onItemCallback;

    public void setOnItemCallback(OnItemCallback onItemCallback1){
        onItemCallback=onItemCallback1;
    }

    public void setListAntarSampahUser(ArrayList<AntarSampahUser> listAntarSampahUser1){
        listAntarSampahUser = listAntarSampahUser1;
    }

    private ArrayList<AntarSampahUser> getListAntarSampahUser(){
        return listAntarSampahUser;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_permintaan_antar, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        AntarSampahUser antarSampahUser = listAntarSampahUser.get(position);
        holder.tvTanggalReqAntar.setText(listAntarSampahUser.get(position).getTanggal());
        holder.tvUserReqAntar.setText(listAntarSampahUser.get(position).getCurrentId());
    }

    @Override
    public int getItemCount() {
        return listAntarSampahUser.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTanggalReqAntar;
        private TextView tvUserReqAntar;
        private TextView tvNoHPUserReqAntar;

        public CardViewHolder(View itemView){
            super(itemView);
            tvTanggalReqAntar = itemView.findViewById(R.id.tv_date_pa);
            tvUserReqAntar = itemView.findViewById(R.id.tv_person_pa);
            tvNoHPUserReqAntar = itemView.findViewById(R.id.tv_phone_pa);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            AntarSampahUser antarSampahUser = getListAntarSampahUser().get(position);
            antarSampahUser.setCurrentId(antarSampahUser.getCurrentId());
        }
    }

    public interface OnItemCallback{
        void onItemclicked(AntarSampahUser data);
    }

}
