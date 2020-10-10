package com.calvindo.aldi.sutanto.tubes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String CHANNEL_ID = "Channel 1";
    Button kamera,favorit;
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

        kamera = (Button) findViewById(R.id.btn_camera);
        kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,0);
            }
        });

        favorit = (Button) findViewById(R.id.btn_fav);
        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotificationChannel();
                addNotification();
            }

            private void addNotification() {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("GMBKost Konfirmation")
                        .setContentText("Selamat pesanan kost anda berhasil ditambahkan atau kurangi")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Intent notificationIntent = new Intent(this, FavoriteFragment.class);
                PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0,builder.build());

            }

            private void createNotificationChannel() {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    CharSequence name = "Channel 1";
                    String description = "This is Channel 1";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
                    channel.setDescription(description);
                    //daftarkan channel di system anda
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
        {
            Log.d("Get","Your Photo has been Slain");

        }
    }
}