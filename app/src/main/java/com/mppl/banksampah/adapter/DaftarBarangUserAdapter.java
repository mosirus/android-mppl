package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.Reward;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DaftarBarangUserAdapter extends RecyclerView.Adapter<DaftarBarangUserAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<Reward> listBarang;

    public DaftarBarangUserAdapter(Context context1,ArrayList<Reward> listBarang1 ){
        context = context1;
        listBarang = listBarang1;
    }

    private OnItemCallback onItemCallback;
    public void setOnItemCallback(OnItemCallback onItemCallback){
        this.onItemCallback = onItemCallback;
    }

    public void setListBarang(ArrayList<Reward> listBarang){
        this.listBarang = listBarang;
    }

    private ArrayList<Reward> getListBarang(){
        return listBarang;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_barang, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Reward reward = listBarang.get(position);
        holder.namaBarang.setText(listBarang.get(position).getNamaReward());
        holder.jenisBarang.setText("("+listBarang.get(position).getJenisReward()+")");
        Glide.with(holder.itemView.getContext())
                .load(reward.getURLReward())
                .apply(new RequestOptions().override(70,70))
                .into(holder.gambarBarang);
    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView namaBarang;
        private ImageView gambarBarang;
        private TextView jenisBarang;
        private Button btnBarang;

        public CardViewHolder(View itemView){
            super(itemView);
            namaBarang = itemView.findViewById(R.id.tv_list_barang);
            gambarBarang = itemView.findViewById(R.id.iv_list_barang);
            btnBarang = itemView.findViewById(R.id.btn_list_barang);
            jenisBarang = itemView.findViewById(R.id.tv_list_jenisreward);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Reward reward = getListBarang().get(position);
            reward.setNamaReward(reward.getNamaReward());

            /*if(v.getId() == R.id.btn_list_barang){

            }*/
        }
    }

    public interface OnItemCallback{
        void onItemclicked(Reward data);
    }
}
