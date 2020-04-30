package com.mppl.banksampah.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.mppl.banksampah.R;

public class EditRewardFragment extends Fragment implements View.OnClickListener{

    private ImageView ivEditReward;
    private TextView tvTakePictureReward;
    private EditText etNamaReward;
    private EditText etPoinReward;
    private Spinner spjenisReward;
    private Button btnBatalEditReward;

    private String namaReward;
    private int poinReward;
    private String jenisReward;
    private String URLReward;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_reward_admin, container, false);

        ivEditReward = root.findViewById(R.id.gbr_event);
        tvTakePictureReward = root.findViewById(R.id.edit_foto);
        tvTakePictureReward.setOnClickListener(this);
        etNamaReward = root.findViewById(R.id.editnamaevent);
        etPoinReward = root.findViewById(R.id.edtpoin_reward);
        spjenisReward = root.findViewById(R.id.spinneredt_jenis_sampah);
        btnBatalEditReward = root.findViewById(R.id.btn_batal_edit);
        btnBatalEditReward.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            namaReward = bundle.getString("namaReward");
            poinReward = bundle.getInt("pointReward");
            jenisReward = bundle.getString("jenisReward");
            URLReward = bundle.getString("urlReward");
        }
        etNamaReward.setText(namaReward);
        etPoinReward.setText(Integer.toString(poinReward));
        spjenisReward.setSelection(((ArrayAdapter) spjenisReward.getAdapter()).getPosition(jenisReward));

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_batal_edit){
            KuponFragment fragment = new KuponFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentEditReward, fragment, KuponFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}