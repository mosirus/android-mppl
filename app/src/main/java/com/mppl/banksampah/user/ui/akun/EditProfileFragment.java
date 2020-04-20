package com.mppl.banksampah.user.ui.akun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mppl.banksampah.R;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private DatabaseReference ref;

    private CircleImageView imageView;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_telp;
    private EditText edt_job;
    private EditText edt_id_number;
    private EditText edt_address;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    private StorageReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel homeViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        TextView edt_gambar = root.findViewById(R.id.txtSuntingFoto);
        edt_name = root.findViewById(R.id.editnama);
        edt_email = root.findViewById(R.id.editemail);
        edt_address = root.findViewById(R.id.editalamat);
        edt_telp = root.findViewById(R.id.editno);
        edt_job = root.findViewById(R.id.editpekerjaan);
        edt_id_number = root.findViewById(R.id.editidentitas);
        imageView = root.findViewById(R.id.fotoprofiledit);

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama = Objects.requireNonNull(dataSnapshot.child("nama_lengkap").getValue()).toString();
                String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                String nomor_telepon = Objects.requireNonNull(dataSnapshot.child("no_telp").getValue()).toString();
                String alamat = Objects.requireNonNull(dataSnapshot.child("alamat").getValue()).toString();
                String pekerjaan = Objects.requireNonNull(dataSnapshot.child("pekerjaan").getValue()).toString();
                String no_identitas = Objects.requireNonNull(dataSnapshot.child("no_identitas").getValue()).toString();

                edt_name.setText(nama);
                edt_email.setText(email);
                edt_telp.setText(nomor_telepon);
                edt_address.setText(alamat);
                edt_id_number.setText(no_identitas);
                edt_job.setText(pekerjaan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = storageReference.child("UserProfilePictures").child(currentuser).child("profile_image");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();

                if (isAdded()) {
                    Glide.with(Objects.requireNonNull(getContext()))
                            .load(url)
                            .into(imageView);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        Button btn_simpan = root.findViewById(R.id.btn_simpan_edit);
        Button btn_batal = root.findViewById(R.id.btn_batal_edit);
        Button btn_edit_password = root.findViewById(R.id.btn_edit_password);

        edt_gambar.setOnClickListener(this);
        btn_batal.setOnClickListener(this);
        btn_simpan.setOnClickListener(this);
        btn_edit_password.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_simpan_edit) {
            updateProfile();

        } else if (v.getId() == R.id.btn_batal_edit) {
            ProfileFragment fragment = new ProfileFragment();

            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ProfileFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        } else if (v.getId() == R.id.txtSuntingFoto) {
            chooseImage();
        } else if (v.getId() == R.id.btn_edit_password) {
            EditPasswordFragment fragment = new EditPasswordFragment();

            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, EditPasswordFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }

    private void chooseImage() {
        Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(GalleryIntent, REQUEST_CODE_GALLERY);
    }

//    private void chooseImage() {
//        CharSequence[] pilihan = {"Kamera", "Galeri"};
//        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
//                .setTitle("Upload Gambar Dari")
//                .setItems(pilihan, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(CameraIntent, REQUEST_CODE_CAMERA);
//                                break;
//
//                            case 1:
//                                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                startActivityForResult(GalleryIntent, REQUEST_CODE_GALLERY);
//                                break;
//                        }
//                    }
//                });
//        dialog.create();
//        dialog.show();
//    }

    private void updateProfile() {

        final String namaEdit = edt_name.getText().toString().trim();
        String telpEdit = edt_telp.getText().toString().trim();
        String pekerjaanEdit = edt_job.getText().toString().trim();
        String noIdentitasEdit = edt_id_number.getText().toString().trim();
        String alamatEdit = edt_address.getText().toString().trim();
        ref.child("nama_lengkap").setValue(namaEdit);
        ref.child("no_telp").setValue(telpEdit);
        ref.child("pekerjaan").setValue(pekerjaanEdit);
        ref.child("no_identitas").setValue(noIdentitasEdit);
        ref.child("alamat").setValue(alamatEdit);

        uploadImage();

        ProfileFragment fragment = new ProfileFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ProfileFragment.class.getSimpleName())
                .addToBackStack(null).commit();
    }

    @Override
    public void onActivityResult(int RequestCode, int resultCode, Intent data) {
        super.onActivityResult(RequestCode, resultCode, data);

        switch (RequestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK) {
                    imageView.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK) {
                    imageView.setVisibility(View.VISIBLE);
                    Uri pickedImgUri = data.getData();
                    imageView.setImageURI(pickedImgUri);
                }
                break;
        }
    }

    private void uploadImage() {
        final StorageReference reference = storageReference.child("UserProfilePictures").child(currentuser)
                .child("profile_image");

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();

        //upload gambar
        UploadTask uploadTask = reference.putBytes(bytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String photo_url = uri.toString().trim();
                        ref.child("profile_image_url").setValue(photo_url);
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NotNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    }
                });
    }


}