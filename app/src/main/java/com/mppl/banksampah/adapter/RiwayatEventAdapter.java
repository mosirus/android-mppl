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
import com.mppl.banksampah.admin.model.EventAdmin;

import java.util.ArrayList;

public class RiwayatEventAdapter extends RecyclerView.Adapter<RiwayatEventAdapter.CardViewViewHolder> {

    private ArrayList<EventAdmin> riwayatEventArrayList;
    private Context context;

    public RiwayatEventAdapter(Context context, ArrayList<EventAdmin> list){
        this.context = context;
        riwayatEventArrayList = list;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_list_riwayat_event, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        if (position == 0) {
            holder.tvTimeEvent.setBackgroundResource(R.drawable.table_header_cell_bg2);
            holder.tvNameEvent.setBackgroundResource(R.drawable.table_header_cell_bg2);

            holder.tvTimeEvent.setText("Tanggal");
            holder.tvTimeEvent.setTextSize(15);
            holder.tvTimeEvent.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvNameEvent.setText("Event");
            holder.tvNameEvent.setTextSize(15);
            holder.tvNameEvent.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        else{
            EventAdmin currentPosition = riwayatEventArrayList.get(position - 1);

            String time = (String.valueOf(currentPosition.getWaktuEvent()));
            String name = (String.valueOf(currentPosition.getNamaEvent()));

            holder.tvTimeEvent.setText(time);
            holder.tvNameEvent.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return riwayatEventArrayList.size() + 1;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTimeEvent;
        private TextView tvNameEvent;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeEvent = itemView.findViewById(R.id.tanggal_riwayat_event);
            tvNameEvent = itemView.findViewById(R.id.nama_riwayat_event);
        }
    }
}
