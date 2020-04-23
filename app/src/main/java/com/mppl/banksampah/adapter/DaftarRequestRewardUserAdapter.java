package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.RequestedReward;

import java.util.ArrayList;

public class DaftarRequestRewardUserAdapter extends RecyclerView.Adapter<DaftarRequestRewardUserAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<RequestedReward> listRequestedRewards;

    public DaftarRequestRewardUserAdapter(Context context1, ArrayList<RequestedReward> listRequestedRewards1){
        context = context1;
        listRequestedRewards = listRequestedRewards1;
    }

    private OnItemCallback onItemCallback;
    public void setOnItemCallback(OnItemCallback onItemCallback){
        this.onItemCallback = onItemCallback;
    }

    public void setListRequestedRewards(ArrayList<RequestedReward> listRequestedRewards1){
        listRequestedRewards = listRequestedRewards1;
    }

    public ArrayList<RequestedReward> getListRequestedRewards(){
        return listRequestedRewards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_requestreward, parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        RequestedReward requestedReward = listRequestedRewards.get(position);
        holder.tanggalRequest.setText(listRequestedRewards.get(position).getTanggalRequest());
        holder.emailRequest.setText(listRequestedRewards.get(position).getEmailRequester());
        holder.aksiRequest.setText("Penukaran "+ listRequestedRewards.get(position).getPoinBarangRequest() +" Poin menjadi "+ listRequestedRewards.get(position).getNamaBarangRequest());
    }

    @Override
    public int getItemCount() {
        return listRequestedRewards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tanggalRequest;
        private TextView emailRequest;
        private TextView aksiRequest;

        public CardViewHolder(View itemView){
            super(itemView);
            tanggalRequest = itemView.findViewById(R.id.tv_date_requestreward);
            emailRequest = itemView.findViewById(R.id.tv_person_requestreward);
            aksiRequest = itemView.findViewById(R.id.tv_action_requestreward);
        }

        @Override
        public void onClick(View v) {

        }
    }

   public interface OnItemCallback{
        void onItemclicked(RequestedReward data);
   }
}
