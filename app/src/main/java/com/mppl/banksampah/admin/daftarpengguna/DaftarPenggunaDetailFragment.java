package com.mppl.banksampah.admin.daftarpengguna;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mppl.banksampah.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DaftarPenggunaDetailFragment extends Fragment {

    public static final String EXTRA = "extra";

    private String strUserName;
    private String strUserEmail;
    private String strUserTelp;
    private String strUserJob;
    private String strUserIdentityNumber;
    private String strUserAddress;
    private String userPoint;
    private String strPhotoURL;

    private CircleImageView ivfotoprofil;

    private FirebaseStorage storage;
    private StorageReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_daftarpengguna_isiform_admin, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            strUserName = bundle.getString("Name");
            strUserEmail = bundle.getString("Email");
            strUserTelp = bundle.getString("Telp");
            strUserJob = bundle.getString("Job");
            strUserIdentityNumber = bundle.getString("IdentityNumber");
            strUserAddress = bundle.getString("Address");
            userPoint = String.valueOf(bundle.getInt("Point"));
            strPhotoURL = bundle.getString("PhotoURL");
        }

        Button btnOk = root.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaftarPenggunaFragment fragment = new DaftarPenggunaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, DaftarPenggunaFragment.class.getSimpleName())
                        .addToBackStack(null).commit();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                DaftarPenggunaFragment fragment = new DaftarPenggunaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, DaftarPenggunaFragment.class.getSimpleName())
                        .addToBackStack(null).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        ivfotoprofil = root.findViewById(R.id.ivfotoprofil);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvUserName = view.findViewById(R.id.userName);
        TextView tvUserEmail = view.findViewById(R.id.userEmail);
        TextView tvUserTelp = view.findViewById(R.id.userTelephone);
        TextView tvUserJob = view.findViewById(R.id.userJob);
        TextView tvUserId = view.findViewById(R.id.userId);
        TextView tvUserAddress = view.findViewById(R.id.userAdress);
        TextView tvUserPoint = view.findViewById(R.id.userPoint);
        //ImageView profilePic = view.findViewById(R.id.fotoprofil);

        tvUserPoint.setText(userPoint);
        tvUserName.setText(strUserName);
        tvUserEmail.setText(strUserEmail);
        tvUserTelp.setText(strUserTelp);
        tvUserId.setText(strUserIdentityNumber);
        tvUserJob.setText(strUserJob);
        tvUserAddress.setText(strUserAddress);

        String emailtujuan = strUserEmail.replace(".","_").toLowerCase();
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference().child("UserProfilePictures").child(emailtujuan).child("profile_image");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                if(url != null){
                    Glide.with(getActivity())
                            .load(url)
                            .into(ivfotoprofil);
                }
            }
        });

        /*if (strPhotoURL != null) {
            Glide.with(getActivity())
                    .load(strPhotoURL)
                    .into(ivfotoprofil);
        }*/
    }

}
