package com.mppl.banksampah.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.RiwayatPoin;
import com.mppl.banksampah.admin.DaftarPengguna;
import com.mppl.banksampah.admin.DaftarPenggunaDetailFragment;
import com.mppl.banksampah.admin.PermintaanAntarFragment;
import com.mppl.banksampah.ui.akun.EditProfileFragment;

import java.util.ArrayList;

public class DaftarPenggunaAdapter extends RecyclerView.Adapter<DaftarPenggunaAdapter.CardViewViewHolder> {

    private ArrayList<DaftarPengguna> listPengguna;
    private Context context;

    public DaftarPenggunaAdapter(Context context, ArrayList<DaftarPengguna> p) {
        this.context = context;
        listPengguna = p;
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListPengguna(ArrayList<DaftarPengguna> listPengguna) {
        this.listPengguna = listPengguna;
    }

    private ArrayList<DaftarPengguna> getListPengguna() {
        return listPengguna;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_daftar_pengguna, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        holder.email.setText(listPengguna.get(position).getEmail());
        holder.point.setText(String.valueOf(listPengguna.get(position).getPoint() + " Poin"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listPengguna.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPengguna.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView email;
        private TextView point;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email_pengguna_adm);
            point = itemView.findViewById(R.id.poin_pengguna_adm);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            DaftarPengguna daftarPengguna = getListPengguna().get(position);

            daftarPengguna.setEmail(daftarPengguna.getEmail());
            daftarPengguna.setPoint(daftarPengguna.getPoint());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), DaftarPenggunaDetailFragment.class);
            moveWithObjectIntent.putExtra(DaftarPenggunaDetailFragment.EXTRA, daftarPengguna);
            itemView.getContext().startActivity(moveWithObjectIntent);

//            DaftarPenggunaDetailFragment fragment = new DaftarPenggunaDetailFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, DaftarPenggunaDetailFragment.class.getSimpleName())
//                    .addToBackStack(null).commit();
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(DaftarPengguna data);
    }
}

