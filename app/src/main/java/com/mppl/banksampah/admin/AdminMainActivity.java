package com.mppl.banksampah.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mppl.banksampah.R;
import com.mppl.banksampah.StartActivity;
import com.mppl.banksampah.admin.RiwayatTukar.RiwayatTukarSampahFragment;
import com.mppl.banksampah.admin.daftarpengguna.DaftarPenggunaFragment;
import com.mppl.banksampah.admin.datasampah.DataSampahFragment;
import com.mppl.banksampah.admin.event.EventFragment;
import com.mppl.banksampah.admin.jemputsampah.PermintaanJemputFragment;
import com.mppl.banksampah.admin.reward.KuponFragment;
import com.mppl.banksampah.admin.terimasampah.TerimaSampahFragmentContainer;

import org.jetbrains.annotations.NotNull;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

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
                    new TerimaSampahFragmentContainer()).commit();
            navigationView.setCheckedItem(R.id.navigation1);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TerimaSampahFragmentContainer()).commit();
                break;
            case R.id.navigation2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PermintaanJemputFragment()).commit();
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
            case R.id.navigation7:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DataSampahFragment()).commit();
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


