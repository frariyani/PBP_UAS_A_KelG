package com.calvindo.aldi.sutanto.tubes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.KostResponse;
import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.adapter.KostRecyclerAdapter;
import com.calvindo.aldi.sutanto.tubes.databinding.ShowListKostBinding;
import com.calvindo.aldi.sutanto.tubes.models.ListKost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKostActivity extends AppCompatActivity {
    private ImageButton back;
    private RecyclerView recyclerView;
    private KostRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_kost);

        back = findViewById(R.id.back);
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadKost();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListKostActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });

        progressBar = findViewById(R.id.progressBar);

        loadKost();
    }

    public void loadKost(){
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<KostResponse> call = apiInterface.getAllKost();

        call.enqueue(new Callback<KostResponse>() {
            @Override
            public void onResponse(Call<KostResponse> call, Response<KostResponse> response) {
                generateDataList(response.body().getKost());
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<KostResponse> call, Throwable t) {
                Toast.makeText(ListKostActivity.this,"Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<KostClientAccess> kostList){
        recyclerView = findViewById(R.id.rv_kost);
        adapter = new KostRecyclerAdapter(this,kostList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListKostActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}