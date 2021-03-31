// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.datasampah.EditSampahFragment;

import java.util.ArrayList;

public class DaftarSampahAdapter extends RecyclerView.Adapter<DaftarSampahAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<String> listSampah;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public DaftarSampahAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        listSampah = list;
    }

    private DaftarSampahAdapter.OnItemCallback onItemCallback;

    private ArrayList<String> getListSampah() {
        return listSampah;
    }

    public void setOnItemCallback(OnItemCallback onItemCallback1) {
        onItemCallback = onItemCallback1;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_daftar_sampah, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {
        if (position == 0) {
            holder.tvNamaSampah.setText("Jenis Sampah");
            holder.tvNamaSampah.setGravity(Gravity.CENTER);
            holder.tvPoinSampah.setText("Poin");
            holder.tvPoinSampah.setGravity(Gravity.CENTER);
            holder.imgEdit.setVisibility(View.GONE);
            holder.imgHapus.setVisibility(View.GONE);
        } else {
            final String[] dataSampah = String.valueOf(listSampah.get(position - 1)).split(" - ");
            final String[] poinSampah = dataSampah[0].split(" ");
            holder.tvNamaSampah.setText(dataSampah[1]);
            holder.tvPoinSampah.setText(poinSampah[0]);
            holder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCallback.onItemclicked(listSampah.get(holder.getAdapterPosition() - 1));

                }
            });
            holder.imgHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialogDeleteSampah = new Dialog(holder.itemView.getContext());
                    dialogDeleteSampah.setContentView(R.layout.alertdialog_deletesampah);
                    TextView tvquestiondelsampah = dialogDeleteSampah.findViewById(R.id.tvquestion_dialogdeleteSampah);
                    Button positivedelsampah = dialogDeleteSampah.findViewById(R.id.positivedelbuttondialogsampah);
                    Button negativedelsampah = dialogDeleteSampah.findViewById(R.id.negativedelbuttondialogsampah);

                    tvquestiondelsampah.setText("Ingin menghapus " + dataSampah[1] + " dari data sampah?");

                    positivedelsampah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String tujuan = poinSampah[0] + " Poin - " + dataSampah[1];
                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference().child("Jenis_Sampah");
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                        if (snapshot1.child("JenisSampah").getValue().toString().equals(tujuan)) {
                                            snapshot1.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(holder.itemView.getContext(), "Data sampah berhasil dihapus", Toast.LENGTH_SHORT).show();
                                                    dialogDeleteSampah.dismiss();
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

                    negativedelsampah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogDeleteSampah.dismiss();
                        }
                    });
                    dialogDeleteSampah.show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listSampah.size() + 1;
    }

    public interface OnItemCallback {
        void onItemclicked(String data);
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvNamaSampah;
        private TextView tvPoinSampah;
        private ImageView imgEdit;
        private ImageView imgHapus;

        CardViewHolder(View itemView) {
            super(itemView);
            tvNamaSampah = itemView.findViewById(R.id.tv_nama_sampah);
            tvPoinSampah = itemView.findViewById(R.id.tv_poin_sampah);
            imgEdit = itemView.findViewById(R.id.imgEditSampah);
            imgHapus = itemView.findViewById(R.id.imgHapusSampah);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String sampah = getListSampah().get(position - 1);

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), EditSampahFragment.class);
            moveWithObjectIntent.putExtra(EditSampahFragment.EXTRA, sampah);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }

    }

}
