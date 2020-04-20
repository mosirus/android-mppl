package com.mppl.banksampah.user.ui.akun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.mppl.banksampah.R;

import java.util.Objects;


public class EditPasswordFragment extends Fragment implements View.OnClickListener {

    //private String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().replace('.','_');
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ProfileViewModel homeViewModel;
    private DatabaseReference ref;

    private EditText edt_old_pass;
    private EditText edt_new_pass;
    private EditText edt_new_pass_confirmation;

    private Button btn_simpan;
    private Button btn_batal;

    private String currentPassword;
    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_password_edit, container, false);


        edt_old_pass = root.findViewById(R.id.editkatasandilama);
        edt_new_pass = root.findViewById(R.id.editkatasandibaru);
        edt_new_pass_confirmation = root.findViewById(R.id.editkonfirmasikatasandi);

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentPassword = dataSnapshot.child("password").getValue().toString();
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
            EditProfileFragment fragment = new EditProfileFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, EditProfileFragment.class.getSimpleName())
                    .addToBackStack(null).commit();
        }
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

        ProfileFragment fragment = new ProfileFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment, ProfileFragment.class.getSimpleName())
                .addToBackStack(null).commit();
    }

}