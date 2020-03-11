package com.mppl.banksampah.ui.akun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mppl.banksampah.R;
import com.mppl.banksampah.User;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        tv_name = root.findViewById(R.id.editnama);
        tv_email = root.findViewById(R.id.editemail);
        tv_address = root.findViewById(R.id.editalamat);
        tv_telp = root.findViewById(R.id.editno);
        tv_job = root.findViewById(R.id.editpekerjaan);
        tv_id_number = root.findViewById(R.id.editidentitas);

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

        editProfile = root.findViewById(R.id.txtsunting);
        editProfile.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtsunting){
            EditProfileFragment fragment = new EditProfileFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, EditProfileFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
    }
}