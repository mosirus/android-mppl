package com.mppl.banksampah.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.StorageReference;
import com.mppl.banksampah.MainActivity;
import com.mppl.banksampah.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class TambahRewardFragment extends Fragment implements OnClickListener{

    private Bitmap bitmap;
    private Uri uri;

    private EditText namaReward;
    private EditText poinReward;
    private Spinner  jenisReward;

    private Button btnTambahReward;
    private Button btnBatalEdit;

    private TextView tvAksesFile;

    private ImageView GambarPenampung;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    private StorageReference reference;
    private DatabaseReference database;

    private ProgressBar progressBar;

    private String path1 = "Reward";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tambahreward_admin, container, false);

        //Data Teks
        namaReward = root.findViewById(R.id.editnamaevent);
        poinReward = root.findViewById(R.id.edit_waktutanggal);
        jenisReward = root.findViewById(R.id.spinner_jenis_reward);

        btnBatalEdit = root.findViewById(R.id.btnReward_batal_tambah);
        btnBatalEdit.setOnClickListener(this);

        btnTambahReward = root.findViewById(R.id.btnReward_tambah);
        btnTambahReward.setOnClickListener(this);

        tvAksesFile = root.findViewById(R.id.tvRewardUpdateFoto);
        tvAksesFile.setOnClickListener(this);

        GambarPenampung = root.findViewById(R.id.penampung_gbr_event);

        progressBar = root.findViewById(R.id.pbTambahReward);

        database = FirebaseDatabase.getInstance().getReference().child(path1);
        reference = FirebaseStorage.getInstance().getReference().child(path1);

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

        if(v.getId() == R.id.btnReward_tambah){
            uploadKupon();
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
                    bitmap = (Bitmap) data.getExtras().get("data");
                    GambarPenampung.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    GambarPenampung.setVisibility(View.VISIBLE);
                    uri = data.getData();
                    GambarPenampung.setImageURI(uri);
                }
                break;
        }
    }

    private boolean validasiFormTambahReward() {
        boolean result = true;
        if(TextUtils.isEmpty(namaReward.getText().toString())){
            namaReward.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            namaReward.setError(null);
        }
        if(TextUtils.isEmpty(poinReward.getText().toString())){
            poinReward.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            poinReward.setError(null);
        }
        if(TextUtils.isEmpty(jenisReward.getSelectedItem().toString())){
            Toast.makeText(getActivity(),"Pilih jenis Reward !",
                    Toast.LENGTH_LONG).show();
            result = false;
        }

        return result;

    }

    private void uploadKupon(){
        if(!validasiFormTambahReward()){
            return;
        }
        else{
            GambarPenampung.setDrawingCacheEnabled(true);
            GambarPenampung.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) GambarPenampung.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
            byte[] bytes = stream.toByteArray();


            String namaGambar = UUID.randomUUID()+namaReward.getText().toString()+".jpg";
            String path1 = "Reward";
            String path1a = "Gambar Reward" + namaGambar;
            String path1b = "Nama Reward";
            String path1c = "Poin Reward";
            String path1d = "Jenis Reward";
            UploadTask uploadTask = reference.child(path1a).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Upload Berhasil", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Upload Gagal", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressBar.setProgress( (int) progress);
                        }
                    });



            String Nama_Reward = namaReward.getText().toString().trim();
            String Poin_Reward = poinReward.getText().toString().trim();
            String Jenis_Reward = jenisReward.getSelectedItem().toString().trim();

            database.child(path1b).setValue(Nama_Reward);
            database.child(path1c).setValue(Poin_Reward);
            database.child(path1d).setValue(Jenis_Reward);

            KuponFragment fragment = new KuponFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, KuponFragment.class.getSimpleName())
                    .addToBackStack(null).commit();

        }
    }
}
