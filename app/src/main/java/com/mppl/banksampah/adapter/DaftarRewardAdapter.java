package com.mppl.banksampah.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


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
import com.mppl.banksampah.admin.model.Reward;

import java.util.ArrayList;

public class DaftarRewardAdapter extends RecyclerView.Adapter<DaftarRewardAdapter.CardViewViewHolder> {
    private ArrayList<Reward> listReward;
    private Context context;

    private FirebaseDatabase database;
    private  DatabaseReference reference;

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
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        final Reward reward = listReward.get(position);
        Reward reward_e1 = listReward.get(position);
        holder.namaReward.setText(listReward.get(position).getNamaReward());
        Glide.with(holder.itemView.getContext())
                .load(reward.getURLReward())
                .apply(new RequestOptions().override(70,70))
                .into(holder.gambarReward);
        holder.btnDeleteReward.setOnClickListener(new View.OnClickListener() {
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
                        reference = database.getReference().child("Reward");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    if(snapshot.child("namaReward").getValue().toString().equals(listReward.get(position).getNamaReward())){
                                        snapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(holder.itemView.getContext(), "Data reward berhasil dihapus", Toast.LENGTH_SHORT).show();
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
        holder.btnEditReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemCallback.onItemclicked(listReward.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listReward.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namaReward;
        private ImageView gambarReward;
        private ImageButton btnDeleteReward;
        private ImageButton btnEditReward;

        CardViewViewHolder(View itemView){
            super(itemView);
            namaReward = itemView.findViewById(R.id.tv_list_reward);
            gambarReward = itemView.findViewById(R.id.iv_list_reward);
            btnDeleteReward = itemView.findViewById(R.id.btnReward_hapus_reward);
            btnEditReward = itemView.findViewById(R.id.btnReward_edit_reward);
            btnEditReward.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Reward listReward2 = getListReward().get(position);

        }
    }

    public interface OnItemCallback{
        void onItemclicked(Reward data);
    }

}
