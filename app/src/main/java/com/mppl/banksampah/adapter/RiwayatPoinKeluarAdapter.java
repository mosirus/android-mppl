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
import com.mppl.banksampah.user.model.RequestedReward;

import java.util.ArrayList;

public class RiwayatPoinKeluarAdapter extends RecyclerView.Adapter<RiwayatPoinKeluarAdapter.CardViewViewHolder> {

    private ArrayList<RequestedReward> listRiwayat;
    private Context context;

    public RiwayatPoinKeluarAdapter(Context context, ArrayList<RequestedReward> listPoin) {
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
        String namaBarang = (String.valueOf(listRiwayat.get(position).getNamaBarangRequest()));
        String poinBarang = (String.valueOf(listRiwayat.get(position).getPoinBarangRequest()));
        String statusRequested = (String.valueOf(listRiwayat.get(position).getStatusRequested()));

        String ketPenukaran = namaBarang + " " + poinBarang + " Poin";

        holder.tvRincian.setText(ketPenukaran);
        holder.tvTanggal.setText(String.valueOf(listRiwayat.get(position).getTanggalRequest()));
        holder.tvPoin.setText(statusRequested);

        switch (statusRequested) {
            case "Berhasil":
                holder.tvPoin.setTextColor(ContextCompat.getColor(context, R.color.berhasil));
                break;
            case "Tidak Berhasil":
                holder.tvPoin.setTextColor(ContextCompat.getColor(context, R.color.gagal));
                break;
        }
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
