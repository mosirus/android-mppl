package com.mppl.banksampah.admin.RiwayatTukar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mppl.banksampah.R;

public class RiwayatTukarSampahDetailFragment extends Fragment {

    public static final String EXTRA = "extra";

    private String strTanggal;
    private String strEmail;
    private String strTelp;
    private String strJSampah;
    private String strPoin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_riwayat_tukar_detail,container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            strTanggal = bundle.getString("Tanggal");
            strEmail = bundle.getString("Email");
            strTelp = bundle.getString("No Telp");
            strJSampah = bundle.getString("Jenis Sampah");
            strPoin = bundle.getString("Poin");
        }

        Button btnOk = root.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiwayatTukarSampahFragment fragment = new RiwayatTukarSampahFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, RiwayatTukarSampahFragment.class.getSimpleName())
                        .addToBackStack(null).commit();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                RiwayatTukarSampahFragment fragment = new RiwayatTukarSampahFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, RiwayatTukarSampahFragment.class.getSimpleName())
                        .addToBackStack(null).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTanggal = view.findViewById(R.id.tv_tanggal);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvTelp = view.findViewById(R.id.tv_notelp);
        TextView tvJSampah = view.findViewById(R.id.tv_jenissampah);
        TextView tvPoin = view.findViewById(R.id.tv_poin);

        tvTanggal.setText(strTanggal);
        tvEmail.setText(strEmail);
        tvTelp.setText(strTelp);
        tvJSampah.setText(strJSampah);
        tvPoin.setText(strPoin);

    }




}
