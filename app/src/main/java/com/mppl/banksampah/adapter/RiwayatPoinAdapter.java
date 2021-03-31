// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.RiwayatPoin;

import java.util.ArrayList;

public class RiwayatPoinAdapter extends RecyclerView.Adapter<RiwayatPoinAdapter.CardViewViewHolder> {

    private ArrayList<RiwayatPoin> listRiwayat;
    private Context context;

    public RiwayatPoinAdapter(Context context, ArrayList<RiwayatPoin> listPoin) {
        this.context = context;
        listRiwayat = listPoin;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_riwayat_poin_masuk, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        String berat = (String.valueOf(listRiwayat.get(position).getBerat()));
        String satuan = (String.valueOf(listRiwayat.get(position).getSatuan()));
        String jenisSampah = (String.valueOf(listRiwayat.get(position).getJenisSampah()));

        String rincian = jenisSampah + " " + berat + " " + satuan;
        holder.tvRincian.setText(rincian);

        holder.tvTanggal.setText(String.valueOf(listRiwayat.get(position).getTanggal()));
        holder.tvPoin.setText(String.valueOf(listRiwayat.get(position).getPoin()));
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    static class CardViewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTanggal;
        private TextView tvRincian;
        private TextView tvPoin;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tv_date_pm);
            tvRincian = itemView.findViewById(R.id.tv_rincian_pm);
            tvPoin = itemView.findViewById(R.id.tv_poin_pm);
        }
    }
}
