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
import com.mppl.banksampah.admin.RiwayatTukarSampahFragment;

import java.util.ArrayList;

public class RiwayatTukarSampahAdapter extends RecyclerView.Adapter<RiwayatTukarSampahAdapter.CardViewViewHolder> {

    private ArrayList<RiwayatTukarSampahFragment> list;
    private Context context;

    public RiwayatTukarSampahAdapter(Context context, ArrayList<RiwayatTukarSampahFragment> listRiwayatTukar){
        this.context = context;
        list = listRiwayatTukar;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_list_riwayat_tukar_sampah, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {

        if (position == 0){
            holder.tvTanggal.setBackgroundResource(R.drawable.table_content_cell_bg2);
            holder.tvEmail.setBackgroundResource(R.drawable.table_content_cell_bg2);
            holder.tvPoin.setBackgroundResource(R.drawable.table_content_cell_bg2);
            holder.tvStatus.setBackgroundResource(R.drawable.table_content_cell_bg2);

            holder.tvTanggal.setText("Tanggal");
            holder.tvTanggal.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvTanggal.setTextSize(14);

            holder.tvEmail.setText("Email");
            holder.tvEmail.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvEmail.setTextSize(15);

            holder.tvPoin.setText("Poin");
            holder.tvPoin.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvPoin.setTextSize(15);

            holder.tvStatus.setText("Status");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvStatus.setTextSize(15);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    static class CardViewViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTanggal;
        private TextView tvEmail;
        private TextView tvPoin;
        private TextView tvStatus;

         CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tanggal_riwayat_tukar);
            tvEmail = itemView.findViewById(R.id.email_riwayat_tukar);
            tvPoin = itemView.findViewById(R.id.tvpoin_riwayat_tukar);
            tvStatus = itemView.findViewById(R.id.status_riwayat_tukar);
        }
    }
}
