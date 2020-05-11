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
import com.mppl.banksampah.RiwayatTukarSampah;
import com.mppl.banksampah.user.model.AntarSampahUser;

import java.util.ArrayList;

public class RiwayatTukarSampahAdapter<OnItemClickCallback> extends RecyclerView.Adapter<RiwayatTukarSampahAdapter.CardViewViewHolder> {

    private ArrayList<AntarSampahUser> list;
    private Context context;

    public RiwayatTukarSampahAdapter(Context context, ArrayList<AntarSampahUser> listRiwayatTukar){
        this.context = context;
        list = listRiwayatTukar;
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(View.OnClickListener onItemClickCallback){
        this.onItemClickCallback = (OnItemClickCallback) onItemClickCallback;
    }

    public void setList(ArrayList<AntarSampahUser> list) {
        this.list = list;
    }

    private ArrayList<AntarSampahUser> getList(){
        return list;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_list_riwayat_tukar_sampah, viewGroup, false);
        return new CardViewViewHolder(mView);
    }


    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        AntarSampahUser currentPosition = list.get(position);
        String tanggal = (String.valueOf(currentPosition.getTanggal()));
        String email = (String.valueOf(currentPosition.getCurrentId()));
        String poin = (String.valueOf(currentPosition.getPoin()));
        String status = (String.valueOf(currentPosition.getStatus()));

        holder.tvTanggal.setText(tanggal);
        holder.tvEmail.setText(email);
        holder.tvPoin.setText(poin);
        holder.tvStatus.setText(status);
        switch (status) {
            case "Berhasil":
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.berhasil));
                holder.tvStatus.setText("Diterima");
                break;
            case "Tidak Berhasil":
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gagal));
                holder.tvStatus.setText("Ditolak");
                break;
        }
//    }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

        @Override
        public void onClick(View view){
             int position = getAdapterPosition();
            RiwayatTukarSampah riwayatTukarSampah = getList().get(position);

            riwayatTukarSampah.setTanggal(riwayatTukarSampah.getTanggal());
            riwayatTukarSampah.setEmail(riwayatTukarSampah.getEmail());
            riwayatTukarSampah.setPoin(riwayatTukarSampah.getPoin());
            riwayatTukarSampah.setStatus(riwayatTukarSampah.getstatus());

        }
    }

    public interface OnItemClickCallBack{
        void onItemClicked(RiwayatTukarSampah data);
    }
}
