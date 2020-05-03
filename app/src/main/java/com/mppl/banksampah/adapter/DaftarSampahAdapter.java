package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;

import java.util.ArrayList;

public class DaftarSampahAdapter extends RecyclerView.Adapter<DaftarSampahAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<String> listSampah;

    public DaftarSampahAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        listSampah = list;
    }

//    private OnItemCallback onItemCallback;
//
//    public void setOnItemCallback(OnItemCallback onItemCallback1) {
//        onItemCallback = onItemCallback1;
//    }

    public void setListSampah(ArrayList<String> list) {
        listSampah = list;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_daftar_sampah, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {
        if (position == 0) {
            holder.tvNamaSampah.setText("Jenis Sampah");
            holder.tvNamaSampah.setGravity(Gravity.CENTER);
            holder.tvPoinSampah.setText("Poin");
            holder.tvPoinSampah.setGravity(Gravity.CENTER);
            holder.imgEdit.setVisibility(View.GONE);
            holder.imgHapus.setVisibility(View.GONE);
        } else {
            String[] dataSampah = String.valueOf(listSampah.get(position - 1)).split(" - ");
            String[] poinSampah = dataSampah[0].split(" ");
            holder.tvNamaSampah.setText(dataSampah[1]);
            holder.tvPoinSampah.setText(poinSampah[0]);
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemCallback.onItemclicked(listSampah.get(holder.getAdapterPosition()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listSampah.size() + 1;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaSampah;
        private TextView tvPoinSampah;
        private ImageView imgEdit;
        private ImageView imgHapus;

        CardViewHolder(View itemView) {
            super(itemView);
            tvNamaSampah = itemView.findViewById(R.id.tv_nama_sampah);
            tvPoinSampah = itemView.findViewById(R.id.tv_poin_sampah);
            imgEdit = itemView.findViewById(R.id.imgEditSampah);
            imgHapus = itemView.findViewById(R.id.imgHapusSampah);
        }

    }

//    public interface OnItemCallback {
//        void onItemclicked(Sampah data);
//    }

}
