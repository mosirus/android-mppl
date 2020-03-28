package com.mppl.banksampah.ui.akun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.auth.FirebaseUser;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ProfileViewModel homeViewModel;
    private DatabaseReference ref;

    private CircleImageView imageView;

    private Uri pickedImgUri;

    private final int PICK_IMAGE_REQUEST = 71;

    private TextView edt_gambar;
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_telp;
    private EditText edt_job;
    private EditText edt_id_number;
    private EditText edt_address;

    private EditText edt_old_pass;
    private EditText edt_new_pass;
    private EditText edt_new_pass_confirmation;

    private Button btn_simpan;
    private Button btn_batal;
    private Button btn_edit_password;

    private String currentPassword;
    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    private StorageReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        edt_gambar = root.findViewById(R.id.txtSuntingFoto);
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
                String nama = dataSnapshot.child("nama_lengkap").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String nomor_telepon = dataSnapshot.child("no_telp").getValue().toString();
                String alamat = dataSnapshot.child("alamat").getValue().toString();
                String pekerjaan = dataSnapshot.child("pekerjaan").getValue().toString();
                String no_identitas = dataSnapshot.child("no_identitas").getValue().toString();

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

        reference = storageReference.child(currentuser).child("profile_image");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();

                if (isAdded()){
                    Glide.with(getContext())
                            .load(url)
                            .into(imageView);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        btn_simpan = root.findViewById(R.id.btn_simpan_edit);
        btn_batal = root.findViewById(R.id.btn_batal_edit);
        btn_edit_password = root.findViewById(R.id.btn_edit_password);

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
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ProfileFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        } else if (v.getId() == R.id.txtSuntingFoto) {
            chooseImage();
        }
        else if (v.getId() == R.id.btn_edit_password) {
            EditPasswordFragment fragment = new EditPasswordFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, EditPasswordFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            pickedImgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pickedImgUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
                byte[] dataImage = baos.toByteArray();
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (pickedImgUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference reference = storageReference.child(currentuser).child("profile_image");

            reference.putFile(pickedImgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
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
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

}