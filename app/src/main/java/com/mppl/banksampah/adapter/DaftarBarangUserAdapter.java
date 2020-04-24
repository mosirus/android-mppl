package com.mppl.banksampah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.Reward;
import com.mppl.banksampah.user.model.RequestedReward;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DaftarBarangUserAdapter extends RecyclerView.Adapter<DaftarBarangUserAdapter.CardViewHolder> {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private Context context;
    private ArrayList<Reward> listBarang;

    public DaftarBarangUserAdapter(Context context1,ArrayList<Reward> listBarang1 ){
        context = context1;
        listBarang = listBarang1;
    }

    private OnItemCallback onItemCallback;
    public void setOnItemCallback(OnItemCallback onItemCallback){
        this.onItemCallback = onItemCallback;
    }

    public void setListBarang(ArrayList<Reward> listBarang){
        this.listBarang = listBarang;
    }

    private ArrayList<Reward> getListBarang(){
        return listBarang;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_barang, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, final int position) {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        final String userEmail = user.getEmail().replace(".","_");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("RequestRewardUser").child(userEmail);


        Reward reward = listBarang.get(position);
        holder.namaBarang.setText(listBarang.get(position).getNamaReward());
        holder.jenisBarang.setText("("+listBarang.get(position).getJenisReward()+")");
        Glide.with(holder.itemView.getContext())
                .load(reward.getURLReward())
                .apply(new RequestOptions().override(70,70))
                .into(holder.gambarBarang);
        int a = listBarang.get(position).getPointReward();
        String b = Integer.toString(a);
        holder.poinBarang.setText(b+" Poin");
        holder.btnBeliBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                String dateRequested = dateFormat.format(date).toString();
                String emailRequester = userEmail;
                String poinRewardRequested = Integer.toString(listBarang.get(position).getPointReward());
                String namaRewardRequested = listBarang.get(position).getNamaReward();
                String statusSementara = "Sedang Diproses";
                RequestedReward requestedReward = new RequestedReward(dateRequested,emailRequester,poinRewardRequested,namaRewardRequested,statusSementara);
                String refkey = reference.push().getKey();

                reference.child(refkey).setValue(requestedReward).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(holder.itemView.getContext(), "Request berhasil dilakukan, silahkan tunggu persetujuan Admin", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView namaBarang;
        private ImageView gambarBarang;
        private TextView jenisBarang;
        private TextView poinBarang;
        private ImageButton btnBeliBarang;

        public CardViewHolder(View itemView){
            super(itemView);
            namaBarang = itemView.findViewById(R.id.tv_list_barang);
            gambarBarang = itemView.findViewById(R.id.iv_list_barang);
            jenisBarang = itemView.findViewById(R.id.tv_list_jenisreward);
            poinBarang = itemView.findViewById(R.id.tv_list_poinreward);
            btnBeliBarang = itemView.findViewById(R.id.btn_list_barang);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Reward reward = getListBarang().get(position);
            reward.setNamaReward(reward.getNamaReward());
            reward.setPointReward(reward.getPointReward());


        }
    }

    public interface OnItemCallback{
        void onItemclicked(Reward data);
    }
}
