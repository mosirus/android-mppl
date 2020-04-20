package com.mppl.banksampah.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.JemputModel;

import java.util.List;

public class TableViewAdapter extends RecyclerView.Adapter {
    private List jemputList;

    public TableViewAdapter(List jemputList){
        this.jemputList = jemputList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_item, parent, false);
        return new RowViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.idTanggal.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.idEmail.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.idLokasi.setBackgroundResource(R.drawable.table_header_cell_bg);

            rowViewHolder.idTanggal.setText("Tanggal");
            rowViewHolder.idEmail.setText("Email");
            rowViewHolder.idLokasi.setText("Lokasi");

        } else {
            JemputModel modal = (JemputModel) jemputList.get(rowPos - 1);

            rowViewHolder.idTanggal.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.idEmail.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.idLokasi.setBackgroundResource(R.drawable.table_content_cell_bg);

            rowViewHolder.idTanggal.setText(modal.getTanggal() + "");
            rowViewHolder.idEmail.setText(modal.getEmail());
            rowViewHolder.idLokasi.setText(modal.getLokasi() + "");
        }

    }

    @Override
    public int getItemCount() {
        return jemputList.size() + 1;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        TextView idTanggal;
        TextView idEmail;
        TextView idLokasi;


        RowViewHolder(View itemView) {
            super(itemView);
            idTanggal = itemView.findViewById(R.id.idTanggal);
            idEmail = itemView.findViewById(R.id.idEmail);
            idLokasi = itemView.findViewById(R.id.idLokasi);
        }
    }
}
