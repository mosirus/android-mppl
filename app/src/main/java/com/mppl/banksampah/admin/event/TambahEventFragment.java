package com.mppl.banksampah.admin.event;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mppl.banksampah.R;
import com.mppl.banksampah.admin.model.EventAdmin;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class TambahEventFragment extends Fragment implements View.OnClickListener {

    private Bitmap bitmap;
    private Uri uri;

    private ImageView photoEvent;
    private TextView tvPhoto;
    private EditText nameEvent;
    private SimpleDateFormat dateFormat;
    private TextView timeEvent;
    private EditText locationEvent;
    private EditText descEvent;

    private Button btnTambah;
    private Button btnBatal;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    private StorageReference reference;
    private StorageReference varReference;
    private DatabaseReference database;
    private DatabaseReference varDatabase;

    private ProgressBar progressBar;

    private String path1 = "Event";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_tambah_event_admin, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        photoEvent = view.findViewById(R.id.gbr_event);
        tvPhoto = view.findViewById(R.id.txtSuntingFoto);
        tvPhoto.setOnClickListener(this);
        nameEvent = view.findViewById(R.id.tambah_namaEvent);
        timeEvent = view.findViewById(R.id.picked_date);
        locationEvent = view.findViewById(R.id.tambah_tempat);
        descEvent = view.findViewById(R.id.tambahdeskripsi);

        btnTambah = view.findViewById(R.id.btn_tambah);
        btnTambah.setOnClickListener(this);
        btnBatal = view.findViewById(R.id.btn_batal);
        btnBatal.setOnClickListener(this);
        ImageButton imgBtnDatePicker = view.findViewById(R.id.date_picker_toggle_event);
        imgBtnDatePicker.setOnClickListener(this);

        progressBar = view.findViewById(R.id.pbTambahEvent);

        database = FirebaseDatabase.getInstance().getReference().child("Event");
        varDatabase = FirebaseDatabase.getInstance().getReference().child("URL");
        reference = FirebaseStorage.getInstance().getReference().child(path1);
        varReference = FirebaseStorage.getInstance().getReference();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tambah){
            uploadEvent();
        }else if (v.getId() == R.id.btn_batal) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Batal")
                    .setMessage("Anda membatalkan untuk menambah Event?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            returnToList();
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }else if (v.getId() == R.id.txtSuntingFoto){
            AmbilFoto();
        }else if (v.getId() == R.id.date_picker_toggle_event){
            showDateDialog();
        }
    }

    private void returnToList() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new EventFragment()).commit();
    }

    private void AmbilFoto(){
        CharSequence[] pilihan = {"Kamera", "Galeri"};
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getActivity())
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
                    photoEvent.setVisibility(View.VISIBLE);
                    bitmap = (Bitmap) data.getExtras().get("data");
                    photoEvent.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    photoEvent.setVisibility(View.VISIBLE);
                    uri = data.getData();
                    photoEvent.setImageURI(uri);
                }
                break;
        }
    }

    private boolean validasiFormTambahEvent() {
        boolean result = true;
        if(TextUtils.isEmpty(nameEvent.getText().toString())){
            nameEvent.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            nameEvent.setError(null);
        }
        if(TextUtils.isEmpty(timeEvent.getText().toString())){
            timeEvent.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            timeEvent.setError(null);
        }
        if(TextUtils.isEmpty(locationEvent.getText().toString())){
            locationEvent.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            locationEvent.setError(null);
        }
        if(photoEvent.getDrawable() instanceof ColorDrawable){
            Toast.makeText(getActivity(),"Ambil gambar terlebih dahulu !",
                    Toast.LENGTH_LONG).show();
            result = false;
        }
        if (TextUtils.isEmpty(timeEvent.getText().toString())) {
            new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setMessage("Harap pilih tanggal pelaksanaan event")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            result = false;
        }
        return result;

    }

    private void uploadEvent(){
        final String refKey = database.push().getKey();
        final String varRef = varDatabase.push().getKey();

        if(!validasiFormTambahEvent()){
            return;
        }
        else{
            photoEvent.setDrawingCacheEnabled(true);
            photoEvent.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) photoEvent.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
            byte[] bytes = stream.toByteArray();


            final String namaGambar = nameEvent.getText().toString()+".jpg";
            String path1 = "Event";
            String path1a = namaGambar;
            String path1b = "Nama Event";
            String path1c = "Time Event";
            String path1d = "Location Event";
            String path1e = "Desc Event";

            //upload gambar
            UploadTask uploadTask = reference.child(namaGambar).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Upload Berhasil", Toast.LENGTH_SHORT).show();
                    reference.child(namaGambar).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String Nama_Event = nameEvent.getText().toString().trim();
                            String Time_Event = timeEvent.getText().toString().trim();
                            String Loc_Event = locationEvent.getText().toString().trim();
                            String Desc_Event = descEvent.getText().toString().trim();
                            String URL_Event = uri.toString().trim();

                            EventAdmin event = new EventAdmin(URL_Event,Nama_Event,Time_Event,Loc_Event,Desc_Event);
                            database.child(refKey).setValue(event);

                            /*String param;
                            param = "Barang";

                            if(Jenis_Reward == param){
                                Reward reward = new Reward(Nama_Reward,Poin_Reward,Jenis_Reward,URL_Reward);
                                database.child("Barang").child(refKey).setValue(reward);
                            }else{
                                Reward reward = new Reward(Nama_Reward,Poin_Reward,Jenis_Reward,URL_Reward);
                                database.child("Kupon").child(refKey).setValue(reward);
                            }*/


                        }
                    });
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

            EventFragment fragment = new EventFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, EventFragment.class.getSimpleName())
                    .addToBackStack(null).commit();

        }
    }

    private void showDateDialog() {

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        /**
         * Set Calendar untuk menampung tanggal yang dipilih
         */
        /**
         * Update TextView dengan tanggal yang kita pilih
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                timeEvent.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

}
