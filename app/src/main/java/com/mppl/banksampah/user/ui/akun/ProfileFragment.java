// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.mppl.banksampah.user.ui.akun;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.storage.StorageReference;
import com.mppl.banksampah.R;
import com.mppl.banksampah.StartActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    //private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.','_');

    private ProfileViewModel homeViewModel;
    private DatabaseReference ref;

    private TextView editProfile;
    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_telp;
    private TextView tv_job;
    private TextView tv_id_number;
    private TextView tv_address;
    private TextView tv_pass;
    private Button btn_keluar;

    private CircleImageView imageView;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    private StorageReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        tv_name = root.findViewById(R.id.editnama);
        tv_email = root.findViewById(R.id.editemail);
        tv_address = root.findViewById(R.id.editalamat);
        tv_telp = root.findViewById(R.id.editno);
        tv_job = root.findViewById(R.id.editpekerjaan);
        tv_id_number = root.findViewById(R.id.editidentitas);
        imageView = root.findViewById(R.id.fotoprofil);
        btn_keluar = root.findViewById(R.id.btn_keluar);

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

                tv_name.setText(nama);
                tv_email.setText(email);
                tv_telp.setText(nomor_telepon);
                tv_address.setText(alamat);
                tv_id_number.setText(no_identitas);
                tv_job.setText(pekerjaan);
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

                    Glide.with(getParentFragment())
                            .load(url)
                            .into(imageView);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        editProfile = root.findViewById(R.id.txtsunting);
        editProfile.setOnClickListener(this);
        btn_keluar.setOnClickListener(this);

        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (isAdded()) {

        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtsunting) {
            EditProfileFragment fragment = new EditProfileFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, EditProfileFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

        if (v.getId() == R.id.btn_keluar) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Logout")
                    .setMessage("Anda akan logout, lanjutkan ?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getActivity(), StartActivity.class));
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // user doesn't want to logout
                        }
                    })
                    .show();
        }
    }
}