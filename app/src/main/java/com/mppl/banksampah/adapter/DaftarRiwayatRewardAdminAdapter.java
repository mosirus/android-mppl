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
import com.mppl.banksampah.user.model.RequestedReward;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DaftarRiwayatRewardAdminAdapter extends RecyclerView.Adapter<DaftarRiwayatRewardAdminAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<RequestedReward> listRequestedRewards;

    public DaftarRiwayatRewardAdminAdapter(Context context1, ArrayList<RequestedReward> listRequestedRewards1){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_riwayatreward, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        RequestedReward requestedReward = listRequestedRewards.get(position);
        holder.tanggalRequest.setText(listRequestedRewards.get(position).getTanggalRequest());
        holder.emailRequester.setText(listRequestedRewards.get(position).getEmailRequester());
        holder.aksiRequest.setText("Penukaran " + listRequestedRewards.get(position).getPoinBarangRequest() + " poin menjadi " + listRequestedRewards.get(position).getNamaBarangRequest());
        holder.statusRequest.setText(listRequestedRewards.get(position).getStatusRequested());
        String statusParam = listRequestedRewards.get(position).getStatusRequested();
        if(statusParam.equals("Sedang Diproses")){
            holder.statusRequest.setTextColor(ContextCompat.getColor(context,R.color.proses));
        }else if(statusParam.equals("Berhasil")){
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
        private TextView emailRequester;
        private TextView aksiRequest;
        private TextView statusRequest;

        public CardViewHolder(View itemView){
            super(itemView);
            tanggalRequest = itemView.findViewById(R.id.tv_date_riwayatreward);
            emailRequester = itemView.findViewById(R.id.tv_person_riwayatreward);
            aksiRequest = itemView.findViewById(R.id.tv_action_riwayatreward);
            statusRequest = itemView.findViewById(R.id.tv_status_riwayatreward);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnItemCallback{
        void onItemclicked(RequestedReward data);
    }
}
