package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.EventAdmin;

import java.util.ArrayList;

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


    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_event_admin, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {
        EventAdmin eventAdmin = getListEvent().get(position);

        Glide.with(context).load(eventAdmin.getPhotoEvent()).into(holder.imgEvent);
        holder.descEvent.setText(eventAdmin.getNamaEvent());
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }



    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgEvent;
        private TextView descEvent;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            imgEvent = itemView.findViewById(R.id.event1);
            descEvent = itemView.findViewById(R.id.ket_riwayat);
        }
    }
}
