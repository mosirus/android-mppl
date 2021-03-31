// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.adapter;

import android.content.Context;
import android.media.Image;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.EventAdmin;
import com.mppl.banksampah.user.model.EventUser;

import java.util.ArrayList;

public class EventAdapterUser extends RecyclerView.Adapter<EventAdapterUser.CardViewViewHolder> {

    private ArrayList<EventUser> listEvent;
    private Context context;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private OnItemCallback onItemCallback;
    public void setOnItemCallback(OnItemCallback onItemCallback){
        this.onItemCallback = onItemCallback;
    }

    public EventAdapterUser(Context context, ArrayList<EventUser> list){
        this.context = context;
        listEvent = list;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_event_user, viewGroup, false);
        return new CardViewViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        EventUser eventUser = listEvent.get(position);
        holder.tvNameEvent.setText(eventUser.getNamaEvent());
        holder.tvTimeEvent.setText(eventUser.getWaktuEvent());
        holder.tvLocEvent.setText(eventUser.getTempatEvent());
        Glide.with(holder.itemView.getContext())
                .load(eventUser.getURLEvent())
                .apply(new RequestOptions())
                .into(holder.imgEvent);

        holder.rlEventUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemCallback.onItemclicked(listEvent.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgEvent;
        private TextView tvNameEvent;
        private TextView tvTimeEvent;
        private TextView tvLocEvent;
        private RelativeLayout rlEventUser;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEvent = itemView.findViewById(R.id.ivEventUser);
            tvNameEvent = itemView.findViewById(R.id.tvNameEventUser);
            tvTimeEvent = itemView.findViewById(R.id.tvTimeEventUser);
            tvLocEvent = itemView.findViewById(R.id.tvLocEventUser);
            rlEventUser = itemView.findViewById(R.id.rl_event_user);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            EventUser listArrayEvent = listEvent.get(position);
        }
    }

    public interface OnItemCallback{
        void onItemclicked(EventUser data);
    }
}
