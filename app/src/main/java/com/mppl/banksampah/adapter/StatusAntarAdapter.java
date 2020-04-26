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
import com.mppl.banksampah.user.model.StatusAntar;

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
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_list_item_status_antar, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {

        if (position == 0) {
            holder.tvStatus.setBackgroundResource(R.drawable.table_header_cell_bg2);
            holder.tvRincian.setBackgroundResource(R.drawable.table_header_cell_bg2);
            holder.tvTanggal.setBackgroundResource(R.drawable.table_header_cell_bg2);
            holder.tvPoin.setBackgroundResource(R.drawable.table_header_cell_bg2);

            holder.tvStatus.setText("Status");
            holder.tvStatus.setTextSize(15);
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvRincian.setText("Rincian");
            holder.tvRincian.setTextSize(15);
            holder.tvRincian.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvTanggal.setText("Tanggal");
            holder.tvTanggal.setTextSize(14);
            holder.tvTanggal.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvPoin.setText("Poin");
            holder.tvPoin.setTextSize(15);
            holder.tvPoin.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        else{
            StatusAntar currentPosition = list.get(position-1);

            String berat = (String.valueOf(currentPosition.getBerat()));
            String satuan = (String.valueOf(currentPosition.getSatuan()));
            String jenisSampah = (String.valueOf(currentPosition.getJenisSampah()));

            String rincian = jenisSampah + " " + berat + " " + satuan;

            holder.tvRincian.setText(rincian);
            holder.tvTanggal.setText(String.valueOf(currentPosition.getTanggal()));
            holder.tvPoin.setText("100");

            holder.tvStatus.setText(currentPosition .getStatus());
            String status = currentPosition.getStatus();
            switch (status) {
                case "Sedang diproses":
                    holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.proses));
                    break;
                case "Berhasil":
                    holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.berhasil));
                    break;
                case "Tidak Berhasil":
                    holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gagal));
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
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


