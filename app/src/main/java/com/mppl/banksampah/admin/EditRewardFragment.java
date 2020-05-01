package com.mppl.banksampah.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.mppl.banksampah.R;

import static android.app.Activity.RESULT_OK;

public class EditRewardFragment extends Fragment implements View.OnClickListener{

    private ImageView ivEditReward;
    private TextView tvTakePictureReward;
    private EditText etNamaReward;
    private EditText etPoinReward;
    private Spinner spjenisReward;
    private Button btnBatalEditReward;
    private Button btnSimpanEditReward;

    private String namaReward;
    private int poinReward;
    private String jenisReward;
    private String URLReward;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    private Bitmap bitmap;
    private Uri uri;


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
        btnSimpanEditReward = root.findViewById(R.id.btn_simpan_edit);
        btnSimpanEditReward.setOnClickListener(this);

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
        Glide.with(getActivity())
                .load(URLReward)
                .into(ivEditReward);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_foto){
            Ambil_Foto();
        }
        if(v.getId() == R.id.btn_batal_edit){
                KuponFragment fragment = new KuponFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentEditReward, fragment, KuponFragment.class.getSimpleName())
                        .addToBackStack(null).commit();
        }
        if(v.getId() == R.id.btn_simpan_edit){
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.alertdialog_editreward);
            Button positiveeditbutton = dialog.findViewById(R.id.positiveeditbuttondialogRewardUser);
            Button negativeeditbutton = dialog.findViewById(R.id.negativeeditbuttondialogRewardUser);
            positiveeditbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            negativeeditbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }

    }

    private boolean validasiFromEditReward(){
        boolean result = true;

        if(TextUtils.isEmpty(etNamaReward.getText().toString())){
            etNamaReward.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            etNamaReward.setError(null);
        }
        if(TextUtils.isEmpty(etPoinReward.getText().toString())){
            etPoinReward.setError("Data tidak bolej kosong !");
            result = false;
        }else {
            etPoinReward.setError(null);
        }
        if(TextUtils.isEmpty(spjenisReward.getSelectedItem().toString())){
            Toast.makeText(getActivity(),"Pilih jenis Reward !",
                    Toast.LENGTH_LONG).show();
            result = false;
        }
        if (ivEditReward.getDrawable() instanceof ColorDrawable) {
            Toast.makeText(getActivity(),"Ambil gambar terlebih dahulu !",
                    Toast.LENGTH_LONG).show();
            result = false;
        }

        return result;
    }

    public void Ambil_Foto(){
        CharSequence[] pilihan = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Upload Gambar Dari")
                .setItems(pilihan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(CameraIntent, REQUEST_CODE_CAMERA);
                                break;

                            case 1:
                                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(GalleryIntent, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        dialog.create();
        dialog.show();
    }

    public void onActivityResult(int RequestCode, int resultCode, Intent data){
        super.onActivityResult(RequestCode,resultCode,data);

        switch(RequestCode){
            case REQUEST_CODE_CAMERA:
                if(resultCode == RESULT_OK){
                    ivEditReward.setVisibility(View.VISIBLE);
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ivEditReward.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    ivEditReward.setVisibility(View.VISIBLE);
                    uri = data.getData();
                    ivEditReward.setImageURI(uri);
                }
                break;
        }
    }

}