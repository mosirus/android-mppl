package com.mppl.banksampah.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.RiwayatPoin;
import com.mppl.banksampah.User;

import java.util.ArrayList;

public class RiwayatPoinAdapter extends RecyclerView.Adapter<RiwayatPoinAdapter.CardViewViewHolder> {

    private ArrayList<RiwayatPoin> listRiwayat;
    private Context context;

    public RiwayatPoinAdapter(Context context) {
        this.context = context;
        listRiwayat = new ArrayList<>();
    }

    public void setListRiwayat(ArrayList<RiwayatPoin> listRiwayat) {
        this.listRiwayat = listRiwayat;
    }

    public ArrayList<RiwayatPoin> getListRiwayat() {
        return listRiwayat;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_riwayat, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        RiwayatPoin riwayatPoin = getListRiwayat().get(position);

        holder.tanggal.setText(riwayatPoin.getTanggal());
        holder.keterangan.setText(riwayatPoin.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        private TextView tanggal;
        private TextView keterangan;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal =itemView.findViewById(R.id.tanggal_riwayat);
            keterangan = itemView.findViewById(R.id.ket_riwayat);
        }
    }
}
