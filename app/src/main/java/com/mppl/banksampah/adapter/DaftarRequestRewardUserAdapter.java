// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.user.model.RequestedReward;
import com.mppl.banksampah.user.model.User;

import java.util.ArrayList;

public class DaftarRequestRewardUserAdapter extends RecyclerView.Adapter<DaftarRequestRewardUserAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<RequestedReward> listRequestedRewards;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    private DatabaseReference reference3;
    private String getEmailUser;

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
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {
        final RequestedReward requestedReward = listRequestedRewards.get(position);
        final String email = listRequestedRewards.get(position).getEmailRequester();

        final String namaBarang = listRequestedRewards.get(position).getNamaBarangRequest();
        final String statusBarang = listRequestedRewards.get(position).getStatusRequested();

        final int poinBarang = Integer.parseInt(listRequestedRewards.get(position).getPoinBarangRequest());
        holder.tanggalRequest.setText(listRequestedRewards.get(position).getTanggalRequest());
        holder.emailRequest.setText(listRequestedRewards.get(position).getEmailRequester());
        holder.aksiRequest.setText("Penukaran "+ listRequestedRewards.get(position).getPoinBarangRequest() +" Poin menjadi "+ listRequestedRewards.get(position).getNamaBarangRequest());
        holder.btnTerimaRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference().child("Users").child(email);
                reference2 = database.getReference().child("RequestRewardUser").child(email);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        int userPoin = user.getPoint();
                        if(userPoin >= poinBarang){
                            //Mengurangi Poin
                            int currentPoin = userPoin - poinBarang;
                            user.setPoint(currentPoin);
                            dataSnapshot.getRef().setValue(user);

                            //Ubah status
                            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot_e1 : dataSnapshot.getChildren()){
                                        if((snapshot_e1.child("namaBarangRequest").getValue().toString().equals(namaBarang)) &&
                                                (snapshot_e1.child("statusRequested").getValue().toString().equals(statusBarang))){
                                            RequestedReward requestedReward1 = snapshot_e1.getValue(RequestedReward.class);
                                            String newStatus = "Berhasil";
                                            requestedReward1.setStatusRequested(newStatus);
                                            snapshot_e1.getRef().setValue(requestedReward1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(holder.itemView.getContext(), "Request berhasil diterima", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }else{
                            Toast.makeText(holder.itemView.getContext(), "Poin user tidak mencukupi, request tidak dapat diterima !", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        holder.btnTolakRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference3 = database.getReference().child("RequestRewardUser").child(email);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot_e1 : dataSnapshot.getChildren()){
                            if((snapshot_e1.child("namaBarangRequest").getValue().toString().equals(namaBarang)) &&
                                    (snapshot_e1.child("statusRequested").getValue().toString().equals(statusBarang))){
                                RequestedReward requestedReward1 = snapshot_e1.getValue(RequestedReward.class);
                                String newStatus = "Tidak Berhasil";
                                requestedReward1.setStatusRequested(newStatus);
                                snapshot_e1.getRef().setValue(requestedReward1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(holder.itemView.getContext(), "Request ditolak", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return listRequestedRewards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tanggalRequest;
        private TextView emailRequest;
        private TextView aksiRequest;
        private Button btnTerimaRequest;
        private Button btnTolakRequest;

        public CardViewHolder(View itemView){
            super(itemView);
            tanggalRequest = itemView.findViewById(R.id.tv_date_requestreward);
            emailRequest = itemView.findViewById(R.id.tv_person_requestreward);
            aksiRequest = itemView.findViewById(R.id.tv_action_requestreward);
            btnTerimaRequest = itemView.findViewById(R.id.btn_terima_requestreward);
            btnTolakRequest = itemView.findViewById(R.id.btn_tolak_requestreward);
        }

        @Override
        public void onClick(View v) {

        }
    }

   public interface OnItemCallback{
        void onItemclicked(RequestedReward data);
   }
}
