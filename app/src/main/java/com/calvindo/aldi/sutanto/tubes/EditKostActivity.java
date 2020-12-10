package com.calvindo.aldi.sutanto.tubes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.KostResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKostActivity extends AppCompatActivity {
    TextView nama_kost,alamat,cost, longitude, latitude,gambar;
    String snama,salamat,sharga,slongitude,slatitude,sgambar, sid;
    ImageButton back;
    MaterialButton btnSave,btnCancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_kost_admin);
        Intent intent = getIntent();
        snama = intent.getStringExtra("snama");
        salamat = intent.getStringExtra("salamat");
        sharga = intent.getStringExtra("sharga");
        slongitude = intent.getStringExtra("slongitude");
        slatitude = intent.getStringExtra("slatitude");
        sgambar = intent.getStringExtra("sgambar");
        sid = intent.getStringExtra("sid");

        nama_kost = findViewById(R.id.etNamaKost);
        alamat = findViewById(R.id.etLokasi);
        cost = findViewById(R.id.etHargaSewa);
        longitude = findViewById(R.id.etLongitude);
        latitude = findViewById(R.id.etLatitude);
        gambar = findViewById(R.id.etURLGambar);

        nama_kost.setText(snama);
        alamat.setText(salamat);
        cost.setText(sharga);
        longitude.setText(slongitude);
        latitude.setText(slatitude);
        gambar.setText(sgambar);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    public void updateData(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<KostResponse> call = apiInterface.updateKost(
                sid,
                nama_kost.getText().toString(),
                alamat.getText().toString(),
                longitude.getText().toString(),
                latitude.getText().toString(),
                cost.getText().toString(),
                gambar.getText().toString());

//
        call.enqueue(new Callback<KostResponse>() {
            @Override
            public void onResponse(Call<KostResponse> call, Response<KostResponse> response) {
                Toast.makeText(EditKostActivity.this,"" + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<KostResponse> call, Throwable t) {

            }
        });
    }
}
