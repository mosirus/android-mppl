package com.mppl.banksampah.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mppl.banksampah.MainActivity;
import com.mppl.banksampah.R;

import static android.app.Activity.RESULT_OK;


public class TambahRewardFragment extends Fragment implements OnClickListener{

    private Button btnBatalEdit;
    private TextView tvAksesFile;
    private ImageView GambarPenampung;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tambahreward_admin, container, false);

        btnBatalEdit = root.findViewById(R.id.btnReward_batal_tambah);
        btnBatalEdit.setOnClickListener(this);

        tvAksesFile = root.findViewById(R.id.tvRewardUpdateFoto);
        tvAksesFile.setOnClickListener(this);

        GambarPenampung = root.findViewById(R.id.penampung_gbr_event);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnReward_batal_tambah){
            KuponFragment fragment = new KuponFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, KuponFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

        if(v.getId() == R.id.tvRewardUpdateFoto){
            AmbilFoto();
        }

    }

    private void AmbilFoto(){
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
                    GambarPenampung.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    GambarPenampung.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    GambarPenampung.setVisibility(View.VISIBLE);
                    Uri uri = data.getData();
                    GambarPenampung.setImageURI(uri);
                }
                break;
        }
    }
}
