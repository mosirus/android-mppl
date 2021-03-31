// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.jemputsampah.KonfirmasiJemputFragment;
import com.mppl.banksampah.user.model.JemputSampahUser;
import com.mppl.banksampah.user.model.User;

import java.util.ArrayList;

public class DaftarJemputSampahAdapter extends RecyclerView.Adapter<DaftarJemputSampahAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<JemputSampahUser> listJemputSampahUser;
    private ArrayList<User> listUser;
    private OnItemCallback onItemCallback;

    public DaftarJemputSampahAdapter(Context context1, ArrayList<JemputSampahUser> listJemputSampahUser1) {
        context = context1;
        listJemputSampahUser = listJemputSampahUser1;
    }

    public void setOnItemCallback(OnItemCallback onItemCallback1) {
        onItemCallback = onItemCallback1;
    }

    private ArrayList<JemputSampahUser> getListJemputSampahUser() {
        return listJemputSampahUser;
    }

    public void setListJemputSampahUser(ArrayList<JemputSampahUser> listJemputSampahUser1) {
        listJemputSampahUser = listJemputSampahUser1;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {
        //String email = listJemputSampahUser.get(position).getEmail();
        //JemputSampahUser jemputSampahUser = listJemputSampahUser.get(position);
        holder.tvTanggalReqJemput.setText(listJemputSampahUser.get(position).getTanggal());
        holder.tvUserReqJemput.setText(listJemputSampahUser.get(position).getEmail());
        holder.tvLokasiReqJemput.setText(listJemputSampahUser.get(position).getLokasiJemput());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemCallback.onItemclicked(listJemputSampahUser.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listJemputSampahUser.size();
    }

    public interface OnItemCallback {
        void onItemclicked(JemputSampahUser data);
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTanggalReqJemput;
        private TextView tvUserReqJemput;
        private TextView tvLokasiReqJemput;

        CardViewHolder(View itemView) {
            super(itemView);
            tvTanggalReqJemput = itemView.findViewById(R.id.idTanggal);
            tvUserReqJemput = itemView.findViewById(R.id.idEmail);
            tvLokasiReqJemput = itemView.findViewById(R.id.idLokasi);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            JemputSampahUser jemputSampahUser = getListJemputSampahUser().get(position);
            jemputSampahUser.setEmail(jemputSampahUser.getEmail());
            jemputSampahUser.setTanggal(jemputSampahUser.getTanggal());
            jemputSampahUser.setLokasiJemput(jemputSampahUser.getLokasiJemput());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), KonfirmasiJemputFragment.class);
            moveWithObjectIntent.putExtra(KonfirmasiJemputFragment.EXTRA, (Parcelable) jemputSampahUser);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
