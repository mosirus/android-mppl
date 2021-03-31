// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.JemputSampahUser;

import java.util.ArrayList;

public class RiwayatTukarSampahJemputAdapter extends RecyclerView.Adapter<RiwayatTukarSampahJemputAdapter.CardViewViewHolder> {
    private ArrayList<JemputSampahUser> list;
    private Context context;

    public RiwayatTukarSampahJemputAdapter(Context context, ArrayList<JemputSampahUser> listRiwayatTukar){
        this.context = context;
        list = listRiwayatTukar;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_list_riwayat_jemput_sampah, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        JemputSampahUser currentPosition = list.get(position);
        String tanggal = (String.valueOf(currentPosition.getTanggal()));
        String email = (String.valueOf(currentPosition.getEmail()));
        String poin = (String.valueOf(currentPosition.getPoin()));
        String status = (String.valueOf(currentPosition.getStatus()));

        holder.tvTanggal.setText(tanggal);
        holder.tvEmail.setText(email);
        holder.tvPoin.setText(poin);
        holder.tvStatus.setText(status);
        switch (status){
            case "Berhasil":
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.berhasil));
                holder.tvStatus.setText("Diterima");
                break;
            case "Tidak Berhasil":
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gagal));
                holder.tvStatus.setText("Ditolak");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CardViewViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTanggal;
        private TextView tvEmail;
        private TextView tvPoin;
        private TextView tvStatus;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tanggal_riwayat_jemput);
            tvEmail = itemView.findViewById(R.id.email_riwayat_jemput);
            tvPoin = itemView.findViewById(R.id.tvpoin_riwayat_jemput);
            tvStatus = itemView.findViewById(R.id.status_riwayat_jemput);
        }
    }
}
