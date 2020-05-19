package com.mppl.banksampah.user.ui.home;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mppl.banksampah.R;

public class DetailEventFragment extends Fragment {

    private ImageView ivPhotoEvent;
    private TextView tvNameEvent;
    private TextView tvTimeEvent;
    private TextView tvLocEvent;
    private TextView tvDescEvent;


    private String URlevent;
    private String nameEvent;
    private String timeEvent;
    private String locEvent;
    private String descEvent;


    private Bitmap bitmap;
    private Uri uri;

    private StorageReference reference;
    private DatabaseReference database;

    private String path1 = "Event";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivPhotoEvent = view.findViewById(R.id.gbr_detail_event);
        tvNameEvent = view.findViewById(R.id.tvname_detail_event);
        tvTimeEvent = view.findViewById(R.id.tvtime_detail_event);
        tvLocEvent = view.findViewById(R.id.tvlocation_detail_event);
        tvDescEvent = view.findViewById(R.id.tvdesc_detail_event);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            URlevent = bundle.getString("urlevent");
            nameEvent = bundle.getString("namaEvent");
            timeEvent = bundle.getString("waktuEvent");
            locEvent = bundle.getString("tempatEvent");
            descEvent = bundle.getString("descEvent");
        }
        tvNameEvent.setText(nameEvent);
        tvTimeEvent.setText(timeEvent);
        tvLocEvent.setText(locEvent);
        tvDescEvent.setText(descEvent);
        Glide.with(getActivity())
                .load(URlevent)
                .into(ivPhotoEvent);

        reference = FirebaseStorage.getInstance().getReference().child(path1);
        database = FirebaseDatabase.getInstance().getReference().child("Event");
    }
}
