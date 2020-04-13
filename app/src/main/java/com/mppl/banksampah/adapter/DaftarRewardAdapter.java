package com.mppl.banksampah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.Reward;

import java.security.acl.Group;
import java.util.ArrayList;

public class DaftarRewardAdapter extends RecyclerView.Adapter<DaftarRewardAdapter.CardViewViewHolder> {
    private ArrayList<Reward> listReward;
    private Context context;

    public DaftarRewardAdapter(Context context1, ArrayList<Reward> listReward1){
        context = context1;
        listReward = listReward1;
    }

    private OnItemCallback onItemCallback;
    public void setOnItemCallback(OnItemCallback onItemCallback){
        this.onItemCallback = onItemCallback;
    }

    public void setListReward(ArrayList<Reward> listReward){
        this.listReward = listReward;
    }

    private ArrayList<Reward> getListReward(){
        return listReward;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_reward, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        Reward reward = listReward.get(position);
        holder.namaReward.setText(listReward.get(position).getNamaReward());
    }

    @Override
    public int getItemCount() {
        return listReward.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namaReward;
        private ImageView gambarReward;

        CardViewViewHolder(View itemView){
            super(itemView);
            namaReward = itemView.findViewById(R.id.tv_list_reward);
            gambarReward = itemView.findViewById(R.id.iv_list_reward);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Reward reward = getListReward().get(position);

            reward.setNamaReward(reward.getNamaReward());
        }
    }

    public interface OnItemCallback{
        void onItemclicked(Reward data);
    }
}
