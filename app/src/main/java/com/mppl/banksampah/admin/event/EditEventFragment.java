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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mppl.banksampah.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class EditEventFragment extends Fragment implements View.OnClickListener {

    private ImageView ivPhotoEvent;
    private TextView tvPhotoEvent;
    private EditText etNameEvent;
    private SimpleDateFormat dateFormat;
    private TextView tvTimeEvent;
    private EditText etLocEvent;
    private EditText etDescevent;
    private Button btnSimpanEdit;
    private Button btnBatalEdit;

    private String URlevent;
    private String nameEvent;
    private String timeEvent;
    private String locEvent;
    private String descEvent;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    private Bitmap bitmap;
    private Uri uri;

    private StorageReference reference;
    private DatabaseReference database;

    private ProgressBar progressBar;

    private String path1 = "Event";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_event_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        ivPhotoEvent = view.findViewById(R.id.gbr_event);
        tvPhotoEvent = view.findViewById(R.id.txtSuntingFoto);
        tvPhotoEvent.setOnClickListener(this);
        etNameEvent = view.findViewById(R.id.edit_namaEvent);
        tvTimeEvent = view.findViewById(R.id.picked_date);
        etLocEvent = view.findViewById(R.id.edit_tempat);
        etDescevent = view.findViewById(R.id.edit_deskripsi);

        btnBatalEdit = view.findViewById(R.id.btn_batal);
        btnBatalEdit.setOnClickListener(this);
        btnSimpanEdit = view.findViewById(R.id.btn_edit);
        btnSimpanEdit.setOnClickListener(this);

        progressBar = view.findViewById(R.id.pbEditEvent);

        ImageButton imgBtnDatePicker = view.findViewById(R.id.date_picker_toggle_event);
        imgBtnDatePicker.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            URlevent = bundle.getString("urlevent");
            nameEvent = bundle.getString("namaEvent");
            timeEvent = bundle.getString("waktuEvent");
            locEvent = bundle.getString("tempatEvent");
            descEvent = bundle.getString("descEvent");
        }
        etNameEvent.setText(nameEvent);
        tvTimeEvent.setText(timeEvent);
        etLocEvent.setText(locEvent);
        etDescevent.setText(descEvent);
        Glide.with(getActivity())
                .load(URlevent)
                .into(ivPhotoEvent);

        reference = FirebaseStorage.getInstance().getReference().child(path1);
        database = FirebaseDatabase.getInstance().getReference().child("Event");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_edit){
            editEvent();
        }else if (v.getId() == R.id.btn_batal) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Batal")
                    .setMessage("Anda membatalkan untuk mengedit Event?")
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

    private boolean validasiFormEditEvent() {
        boolean result = true;
        if(TextUtils.isEmpty(etNameEvent.getText().toString())){
            etNameEvent.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            etNameEvent.setError(null);
        }
        if(TextUtils.isEmpty(tvTimeEvent.getText().toString())){
            tvTimeEvent.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            tvTimeEvent.setError(null);
        }
        if(TextUtils.isEmpty(etLocEvent.getText().toString())){
            etLocEvent.setError("Data tidak boleh kosong !");
            result = false;
        }else{
            etLocEvent.setError(null);
        }
        if(ivPhotoEvent.getDrawable() instanceof ColorDrawable){
            Toast.makeText(getActivity(),"Ambil gambar terlebih dahulu !",
                    Toast.LENGTH_LONG).show();
            result = false;
        }
        if (TextUtils.isEmpty(tvTimeEvent.getText().toString())) {
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
                    ivPhotoEvent.setVisibility(View.VISIBLE);
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ivPhotoEvent.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    ivPhotoEvent.setVisibility(View.VISIBLE);
                    uri = data.getData();
                    ivPhotoEvent.setImageURI(uri);
                }
                break;
        }
    }

    private void editEvent() {
        if (!validasiFormEditEvent()) {
            return;
        } else {
            ivPhotoEvent.setDrawingCacheEnabled(true);
            ivPhotoEvent.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) ivPhotoEvent.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            final byte[] bytes = stream.toByteArray();

            final String namaGambar = etNameEvent.getText().toString() + ".jpg";

            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(final DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                        if(snapshot1.child("namaEvent").getValue().toString().equals(nameEvent)){


                            UploadTask uploadTask = reference.child(namaGambar).putBytes(bytes);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //progressBar.setVisibility(View.GONE);

                                    reference.child(namaGambar).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String newNameEvent = etNameEvent.getText().toString().trim();
                                            String newTimeEvent = tvTimeEvent.getText().toString().trim();
                                            String newLocEvent = etLocEvent.getText().toString().trim();
                                            String newDesEvent = etDescevent.getText().toString().trim();
                                            String newURL_Reward = uri.toString().trim();

                                            snapshot1.child("namaEvent").getRef().setValue(newNameEvent);
                                            snapshot1.child("waktuEvent").getRef().setValue(newTimeEvent);
                                            snapshot1.child("tempatEvent").getRef().setValue(newLocEvent);
                                            snapshot1.child("descEvent").getRef().setValue(newDesEvent);
                                            snapshot1.child("urlreward").getRef().setValue(newURL_Reward);
                                            Toast.makeText(getActivity(), "Upload Berhasil", Toast.LENGTH_SHORT).show();

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
                                    });
                                    /*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                            progressBar.setVisibility(View.VISIBLE);
                                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                            progressBar.setProgress( (int) progress);
                                        }
                                    });*/

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

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
                tvTimeEvent.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}