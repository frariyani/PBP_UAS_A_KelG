package com.calvindo.aldi.sutanto.tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;

    //Inisialisasi variabel navListener pada saat menu di click
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = new HomeFragment();

                    switch (item.getItemId()){
                        //memilih homeFragment
                        case R.id.action_home:
                            selected = new HomeFragment();
                            break;
                        //memilih favFragment
                        case R.id.action_fav:
                            selected = new FavoriteFragment();
                            break;
                        //memilih profilFragment
                        case R.id.action_profile:
                            selected = new ProfileFragment();
                            break;
                    }
                    //mengubah fragment sesuai fragment yang di pilih
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                            selected).commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set default layout dengan homeFragment
        Fragment default_frag = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                default_frag).commit();

        //memanggil fungsi bottom navigation ke dalam layout
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation_view);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }


}