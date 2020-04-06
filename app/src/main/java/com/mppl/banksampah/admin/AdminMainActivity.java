package com.mppl.banksampah.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mppl.banksampah.R;
import com.mppl.banksampah.StartActivity;
import com.mppl.banksampah.adapter.TableViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewJemputList);

        TableViewAdapter adapter = new TableViewAdapter(getJemputList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_close, R.string.navigation_drawer_open);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TerimaSampahFragment()).commit();
            navigationView.setCheckedItem(R.id.navigation1);
        }
    }

    private List getJemputList() {
        List jemputList = new ArrayList<>();

        jemputList.add(new JemputModel("04-06-2020", "laxojun1@gmail.com", "Audit"));
        jemputList.add(new JemputModel("03-04-2020", "laxo@gmail.com", "EH"));

        return  jemputList;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TerimaSampahFragment()).commit();
                break;
            case R.id.navigation2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new JemputFragment()).commit();
                break;
            case R.id.navigation3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DaftarPenggunaFragment()).commit();
                break;
            case R.id.navigation4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventFragment()).commit();
                break;
            case R.id.navigation5:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new KuponFragment()).commit();
                break;
            case R.id.navigatin6:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RiwayatTukarSampahFragment()).commit();
                break;
            case R.id.nav_admin_keluar:

                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Anda akan logout, lanjutkan ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(AdminMainActivity.this, StartActivity.class));
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to logout
                            }
                        })
                        .show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}


