package com.calvindo.aldi.sutanto.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.KostResponse;
import com.calvindo.aldi.sutanto.tubes.API.UserResponse;
import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.databinding.TambahKostBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKostActivity extends AppCompatActivity {
    private ImageButton back;
    private MaterialButton btnCreate;
    private TextInputEditText etNama,etHarga,etLongitude, etLatitude, etLokasi, etURLGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_kost);

//        Init layout
        back = findViewById(R.id.back);
        etNama = findViewById(R.id.etNamaKost);
        etHarga = findViewById(R.id.etHargaSewa);
        etLongitude = findViewById(R.id.etLongitude);
        etLatitude = findViewById(R.id.etLatitude);
        etLokasi = findViewById(R.id.etLokasi);
        etURLGambar = findViewById(R.id.etURLGambar);
        btnCreate = findViewById(R.id.btnSave);



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNama.getText().toString().isEmpty()){
                    etNama.setError("Isikan dengan benar");
                    etNama.requestFocus();
                }else if (etHarga.getText().toString().isEmpty()){
                    etHarga.setError("Isikan dengan Benar");
                    etHarga.requestFocus();
                }else if (etLatitude.getText().toString().isEmpty()){
                    etLatitude.setError("Isikan dengan benar", null);
                    etLatitude.requestFocus();
                }else if (etLongitude.getText().toString().isEmpty()){
                    etLongitude.setError("Isikan dengan benar", null);
                    etLongitude.requestFocus();
                }else if (etLokasi.getText().toString().isEmpty()){
                    etLokasi.setError("Isikan dengan benar");
                    etLokasi.requestFocus();
                }else if (etURLGambar.getText().toString().isEmpty()){
                    etURLGambar.setError("Isikan dengan benar");
                    etURLGambar.requestFocus();
                }else {
                    saveUser();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void saveUser(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<KostResponse> add = apiInterface.createKost(
                etNama.getText().toString(),
                etLokasi.getText().toString(),
                etLongitude.getText().toString(),
                etLatitude.getText().toString(),
                etHarga.getText().toString(),
                etURLGambar.getText().toString()
        );

        add.enqueue(new Callback<KostResponse>() {
            @Override
            public void onResponse(Call<KostResponse> call, Response<KostResponse> response) {
                Toast.makeText(TambahKostActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Quda : ", "Masuk RESPONSE ," + response.code());
                onBackPressed();
            }

            @Override
            public void onFailure(Call<KostResponse> call, Throwable t) {
                Toast.makeText(TambahKostActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                Log.i("Quda : ", "Masuk FAILURE ," + t.getMessage());
            }
        });
    }
}