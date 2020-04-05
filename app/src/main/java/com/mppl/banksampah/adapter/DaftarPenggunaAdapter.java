package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.RiwayatPoin;
import com.mppl.banksampah.admin.DaftarPengguna;

import java.util.ArrayList;

public class DaftarPenggunaAdapter extends RecyclerView.Adapter<DaftarPenggunaAdapter.CardViewViewHolder> {

    private ArrayList<DaftarPengguna> listPengguna;
    private Context context;

    public DaftarPenggunaAdapter(Context context, ArrayList<DaftarPengguna> p) {
        this.context = context;
        listPengguna = p;
    }

    public void setListPengguna(ArrayList<DaftarPengguna> listPengguna) {
        this.listPengguna = listPengguna;
    }

    public ArrayList<DaftarPengguna> getListPengguna() {
        return listPengguna;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_daftar_pengguna, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        holder.email.setText(listPengguna.get(position).getEmail());
        holder.point.setText(String.valueOf(listPengguna.get(position).getPoint()));
    }

    @Override
    public int getItemCount() {
        return listPengguna.size();
    }

    static class CardViewViewHolder extends RecyclerView.ViewHolder {
        private TextView email;
        private TextView point;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email_pengguna_adm);
            point = itemView.findViewById(R.id.poin_pengguna_adm);
        }
    }
}
