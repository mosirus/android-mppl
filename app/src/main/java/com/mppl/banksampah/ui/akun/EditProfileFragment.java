package com.mppl.banksampah.ui.akun;

import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mppl.banksampah.MainActivity;
import com.mppl.banksampah.R;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    private String currentPassword;
    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

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

        edt_old_pass = root.findViewById(R.id.editkatasandilama);
        edt_new_pass = root.findViewById(R.id.editkatasandibaru);
        edt_new_pass_confirmation = root.findViewById(R.id.editkonfirmasikatasandi);

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
                currentPassword = dataSnapshot.child("password").getValue().toString();


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

        btn_simpan = root.findViewById(R.id.btn_simpan_edit);
        btn_batal = root.findViewById(R.id.btn_batal_edit);

        edt_gambar.setOnClickListener(this);
        btn_batal.setOnClickListener(this);
        btn_simpan.setOnClickListener(this);

        return root;
    }

    private boolean updatePassword() {
        String oldPass = edt_old_pass.getText().toString().trim();
        String newPass = edt_new_pass.getText().toString().trim();
        String newPassConfirmation = edt_new_pass_confirmation.getText().toString().trim();

        boolean result = true;

        if (oldPass.equals(currentPassword)) {
            if (newPass.equals(newPassConfirmation)) {
                user.updatePassword(newPass);
                ref.child("password").setValue(newPass);
                edt_new_pass.setError(null);
                edt_new_pass_confirmation.setError(null);
            } else {
                edt_new_pass_confirmation.setError("Konfirmasi password tidak sesuai");
                result = false;
            }
        } else {
            edt_old_pass.setError("Password salah");
            result = false;
        }
        return result;
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
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void updateProfile() {

        if (!edt_old_pass.getText().toString().isEmpty()) {
            if (edt_new_pass.getText().toString().isEmpty()) {
                edt_new_pass.setError("Password baru tidak boleh kosong");
                return;
            } else {
                updatePassword();
                if (!updatePassword()) {
                    return;
                }
            }
        }

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