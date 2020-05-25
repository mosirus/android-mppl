package com.mppl.banksampah.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.RequestedReward;

import java.util.ArrayList;

public class DaftarStRequestRewardUserAdapter extends RecyclerView.Adapter<DaftarStRequestRewardUserAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<RequestedReward> listRequestedRewards;

    public DaftarStRequestRewardUserAdapter(Context context1, ArrayList<RequestedReward> listRequestedRewards1){
        context = context1;
        listRequestedRewards = listRequestedRewards1;
    }
    private OnItemCallback onItemCallback;
    public void setOnItemCallback(OnItemCallback onItemCallback1){
        onItemCallback = onItemCallback1;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_sttukarpoin, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        RequestedReward requestedReward = listRequestedRewards.get(position);
        holder.tanggalRequest.setText(listRequestedRewards.get(position).getTanggalRequest());
        holder.aksiRequest.setText(listRequestedRewards.get(position).getNamaBarangRequest() + " " + listRequestedRewards.get(position).getPoinBarangRequest() + " Poin");
        holder.statusRequest.setText(listRequestedRewards.get(position).getStatusRequested());
        if(listRequestedRewards.get(position).getStatusRequested().equals("Sedang Diproses")){
            holder.statusRequest.setTextColor(ContextCompat.getColor(context,R.color.proses));
        }else if(listRequestedRewards.get(position).getStatusRequested().equals("Berhasil")){
            holder.statusRequest.setTextColor(ContextCompat.getColor(context,R.color.berhasil));
        }else{
            holder.statusRequest.setTextColor(ContextCompat.getColor(context,R.color.gagal));
        }


    }

    @Override
    public int getItemCount() {
        return listRequestedRewards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tanggalRequest;
        private TextView aksiRequest;
        private TextView statusRequest;

        public CardViewHolder(View itemView){
            super(itemView);
            tanggalRequest = itemView.findViewById(R.id.tv_date_sttp);
            aksiRequest = itemView.findViewById(R.id.tv_action_sttp);
            statusRequest = itemView.findViewById(R.id.tv_status_sttp);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnItemCallback{
        void onItemclicked(RequestedReward data);
    }
}
