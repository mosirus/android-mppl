package com.mppl.banksampah.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;

import java.util.ArrayList;

public class KuponFragment extends Fragment implements OnClickListener{

    private Button btnTambahReward;
    private Button btnPermintaanReward;
    private Button btnRiwayatReward;

    private RecyclerView rvListReward;

    private String nama_reward[] = {"Reward1","Reward2","Reward3","Reward4"};

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String GetUserID;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kupon, container, false);

        btnTambahReward = root.findViewById(R.id.btn_tambah_reward);
        btnTambahReward.setOnClickListener(this);

        btnPermintaanReward = root.findViewById(R.id.btnReward_permintaanReward);
        btnPermintaanReward.setOnClickListener(this);

        btnRiwayatReward = root.findViewById(R.id.btnReward_riwayatReward);
        btnRiwayatReward.setOnClickListener(this);

        rvListReward = root.findViewById(R.id.rv_list_reward);
        rvListReward.setHasFixedSize(true);
        rvListReward.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListReward.setAdapter(new SimpleRVAdapter(nama_reward));

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        database =  FirebaseDatabase.getInstance();
        reference = database.getReference();

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_tambah_reward){
            TambahRewardFragment fragment = new TambahRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, TambahRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_permintaanReward){
            PermintaanRewardFragment fragment = new PermintaanRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, PermintaanRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btnReward_riwayatReward){
            RiwayatRewardFragment fragment = new RiwayatRewardFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, RiwayatRewardFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

    }

    public class SimpleRVAdapter extends RecyclerView.Adapter<ListViewHolder>{

        private String[] data;
        public SimpleRVAdapter(String[] dataset){
            data = dataset;
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_reward, parent, false);
            ListViewHolder viewHolder = new ListViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            holder.namaReward.setText(data[position]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView namaReward;
        public ListViewHolder(View itemView){
            super(itemView);
            namaReward = itemView.findViewById(R.id.tv_list_reward);
        }
    }

}

