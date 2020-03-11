package com.mppl.banksampah.ui.akun;

import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private ProfileViewModel homeViewModel;
    private DatabaseReference ref;

    private EditText editProfile;
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_telp;
    private EditText edt_job;
    private EditText edt_id_number;
    private EditText edt_address;
    private EditText edt_pass;

    private Button btn_simpan;
    private Button btn_batal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_edit, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        edt_name = root.findViewById(R.id.editnama);
        edt_email = root.findViewById(R.id.editemail);
        edt_address = root.findViewById(R.id.editalamat);
        edt_telp = root.findViewById(R.id.editno);
        edt_job = root.findViewById(R.id.editpekerjaan);
        edt_id_number = root.findViewById(R.id.editidentitas);

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

        btn_simpan = root.findViewById(R.id.btn_simpan_edit);
        btn_batal = root.findViewById(R.id.btn_batal_edit);
        btn_batal.setOnClickListener(this);
        btn_simpan.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_simpan_edit){
            String namaEdit = edt_name.getText().toString().trim();
            String telpEdit = edt_telp.getText().toString().trim();
            String pekerjaanEdit = edt_job.getText().toString().trim();
            String noIdentitasEdit = edt_id_number.getText().toString().trim();
            String alamatEdit = edt_address.getText().toString().trim();
            ref.child("nama_lengkap").setValue(namaEdit);
            ref.child("no_telp").setValue(telpEdit);
            ref.child("pekerjaan").setValue(pekerjaanEdit);
            ref.child("no_identitas").setValue(noIdentitasEdit);
            ref.child("alamat").setValue(alamatEdit);

            ProfileFragment fragment = new ProfileFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ProfileFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }

        else if (v.getId() == R.id.btn_batal_edit){
            ProfileFragment fragment = new ProfileFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ProfileFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}