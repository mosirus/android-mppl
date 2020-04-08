package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.ui.EventAdmin;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CardViewViewHolder>{

    private ArrayList<EventAdmin> listEvent;
    private Context context;

    public EventAdapter(Context context) {
        this.context = context;
        listEvent = new ArrayList<>();
    }

    public void setListEvent(ArrayList<EventAdmin> listEvent) {
        this.listEvent = listEvent;
    }

    public ArrayList<EventAdmin> getListEvent(){
        return listEvent;
    }


    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_event_admin, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        EventAdmin eventAdmin = getListEvent().get(position);

        holder.tanggaldanwaktu.setText(eventAdmin.getTanggaldanwaktu());
        holder.deskripsi.setText(eventAdmin.getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }



    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        private TextView tanggaldanwaktu;
        private TextView deskripsi;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            tanggaldanwaktu = itemView.findViewById(R.id.tanggal_riwayat_event1);
            deskripsi = itemView.findViewById(R.id.rvDaftarEvent);
        }
    }
}
