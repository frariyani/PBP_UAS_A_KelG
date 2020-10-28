package com.calvindo.aldi.sutanto.tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.os.Build.VERSION_CODES.O;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private int index;

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
                            index = 0;
                            break;
                        //memilih favFragment
                        case R.id.action_fav:
                            selected = new FavoriteFragment();
                            index = 1;
                            break;
                        //memilih profilFragment
                        case R.id.action_profile:
                            selected = new ProfileFragment();
                            index = 2;
                            break;
                    }
                    //mengubah fragment sesuai fragment yang di pilih
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                            selected).commit();
                    return true;
                }
            };

    private NavigationView.OnNavigationItemSelectedListener navViewListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = new HomeFragment();

                    switch (item.getItemId()){
                        //memilih homeFragment
                        case R.id.action_home:
                            selected = new HomeFragment();
                            index = 0;
                            break;
                        //memilih favFragment
                        case R.id.action_fav:
                            selected = new FavoriteFragment();
                            index = 1;
                            break;
                        //memilih profilFragment
                        case R.id.action_profile:
                            selected = new ProfileFragment();
                            index = 2;
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
        auth = FirebaseAuth.getInstance();
        int display_mode = getResources().getConfiguration().orientation;

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    finish();
                }
            }
        };


        //set default layout dengan homeFragment

        if(savedInstanceState != null) {
            int currentTab = savedInstanceState.getInt("CurrentTab", 0);
            /* Set currently selected tab */
        }else {
            Fragment default_frag = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    default_frag).commit();
        }
        //memanggil fungsi bottom navigation ke dalam layout
        if (display_mode == Configuration.ORIENTATION_PORTRAIT){
            BottomNavigationView bottomNavigation = findViewById(R.id.navigation_view);
            bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        }else{
            NavigationView navigationView = findViewById(R.id.navigation_view_land);
            navigationView.setNavigationItemSelectedListener(navViewListener);
        }
        createNotificationChannel();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String CHANNEL_ID = "Channel 1";
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String mag = "Successful";
                        if(!task.isSuccessful()){
                            mag = "Failed";
                        }
                        Toast.makeText(MainActivity.this, mag, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            BottomNavigationView navigation = findViewById(R.id.navigation_view);
            navigation.setRotation(90f);
            navigation.getLayoutParams().width=480;
            navigation.requestLayout();
            navigation.setY(600f);
            navigation.setX(-435f);
            // navigation.requestLayout();
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                final View iconView = menuView.getChildAt(i);
                iconView.setRotation(-90f);
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            recreate();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentTab", index);
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
}