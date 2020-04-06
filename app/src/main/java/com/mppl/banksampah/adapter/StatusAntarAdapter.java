package com.mppl.banksampah.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.DaftarPengguna;
import com.mppl.banksampah.ui.tentang.StatusAntar;

import java.util.ArrayList;

public class StatusAntarAdapter extends RecyclerView.Adapter<StatusAntarAdapter.CardViewViewHolder> {

    private ArrayList<StatusAntar> list;
    private Context context;

    public StatusAntarAdapter(Context context, ArrayList<StatusAntar> p) {
        this.context = context;
        list = p;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_status_antar, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        String berat = (String.valueOf(list.get(position).getBerat()));
        String satuan = (String.valueOf(list.get(position).getSatuan()));
        String jenisSampah = (String.valueOf(list.get(position).getJenisSampah()));

        String rincian = jenisSampah + " " + berat + " " + satuan;

        holder.tvRincian.setText(rincian);
        holder.tvTanggal.setText(String.valueOf(list.get(position).getTanggal()));
        holder.tvPoin.setText("10");

        holder.tvStatus.setText(list.get(position).getStatus());
        String status = list.get(position).getStatus();
        if (status.equals("Sedang diproses")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.proses));
        }
        else if (status.equals("Berhasil")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.berhasil));
        }

        else if (status.equals("Tidak Berhasil")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gagal));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CardViewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTanggal;
        private TextView tvStatus;
        private TextView tvRincian;
        private TextView tvPoin;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.status);
            tvRincian = itemView.findViewById(R.id.rincian);
            tvTanggal = itemView.findViewById(R.id.tanggal);
            tvPoin = itemView.findViewById(R.id.tvpoin);
        }

    }

}


