package com.mppl.banksampah.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.EventAdmin;
import com.mppl.banksampah.admin.model.Reward;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CardViewViewHolder>{

    private ArrayList<EventAdmin> listEvent;
    private Context context;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public EventAdapter(Context context, ArrayList<EventAdmin> listEvent1) {
        this.context = context;
        listEvent = listEvent1;
    }

    private OnItemCallback onItemCallback;
    public void setOnItemCallback(OnItemCallback onItemCallback){
        this.onItemCallback = onItemCallback;
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
    public void onBindViewHolder(final CardViewViewHolder holder, final int position) {
        EventAdmin eventAdmin = getListEvent().get(position);
        holder.nameEvent.setText(listEvent.get(position).getNamaEvent());
        Glide.with(holder.itemView.getContext())
                .load(eventAdmin.getURLEvent())
                .apply(new RequestOptions())
                .into(holder.imgEvent);

        holder.btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog confDeleteDialog = new Dialog(holder.itemView.getContext());
                confDeleteDialog.setContentView(R.layout.alertdialog_deletereward);
                Button deleteItem = confDeleteDialog.findViewById(R.id.positivedelbuttondialogRewardUser);
                Button cancelDeleteItem = confDeleteDialog.findViewById(R.id.negativedelbuttondialogRewardUser);

                deleteItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database = FirebaseDatabase.getInstance();
                        reference = database.getReference().child("Event");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    if(snapshot.child("namaEvent").getValue().toString().equals(listEvent.get(position).getNamaEvent())){
                                        snapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(holder.itemView.getContext(), "Data Event berhasil dihapus", Toast.LENGTH_SHORT).show();
                                                confDeleteDialog.dismiss();
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                cancelDeleteItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confDeleteDialog.dismiss();
                    }
                });
                confDeleteDialog.show();
            }
        });

        holder.btnEditEvent.setOnClickListener(new View.OnClickListener(){
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
        private TextView nameEvent;
        private Button btnEditEvent;
        private Button btnDeleteEvent;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            imgEvent = itemView.findViewById(R.id.event1);
            nameEvent = itemView.findViewById(R.id.ket_riwayat);
            btnEditEvent = itemView.findViewById(R.id.btn_editEvent);
            btnDeleteEvent = itemView.findViewById(R.id.btn_deleteEvent);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            EventAdmin listEvent = getListEvent().get(position);

        }


    }
    public interface OnItemCallback{
        void onItemclicked(EventAdmin data);
    }
}
