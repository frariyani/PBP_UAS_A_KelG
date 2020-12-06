package com.calvindo.aldi.sutanto.tubes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    private CardView cvTambahKost, cvListKost, cvTransaksi, cvListUser;
    private TextView tvLogout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_admin);

        //init
        cvTambahKost = findViewById(R.id.cvTambahKost);
        cvListKost = findViewById(R.id.cvListKost);
        cvTransaksi = findViewById(R.id.cvTransaksi);
        cvListUser = findViewById(R.id.cvListUser);
        tvLogout = findViewById(R.id.tvLogout);
        auth = FirebaseAuth.getInstance();

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
            }
        });

        cvTambahKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, TambahKostActivity.class);
                startActivity(i);
            }
        });

        cvListKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, ListKostActivity.class);
                startActivity(i);
            }
        });

        cvTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, ListTransaksiActivity.class);
                startActivity(i);
            }
        });

        cvListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, ListUserActivity.class);
                startActivity(i);
            }
        });


    }



}