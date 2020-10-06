package com.calvindo.aldi.sutanto.tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment default_frag = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                default_frag).commit();
        BottomNavigationView bottomNavigation = findViewById(R.id.navigation_view);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = new HomeFragment();

                    switch (item.getItemId()){
                        case R.id.action_home:
                            selected = new HomeFragment();
                            break;
                        case R.id.action_fav:
                            selected = new FavoriteFragment();
                            break;
                        case R.id.action_profile:
                            selected = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                            selected).commit();
                    return true;
                }
            };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu, menu);
//
//        return true;
//    }
}