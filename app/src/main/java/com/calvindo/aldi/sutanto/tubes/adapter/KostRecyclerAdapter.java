package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.KostResponse;
import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.EditKostActivity;
import com.calvindo.aldi.sutanto.tubes.KostOnMAP;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.databinding.CardviewBinding;
import com.calvindo.aldi.sutanto.tubes.databinding.ItemKostAdminBinding;
import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Kost;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KostRecyclerAdapter extends RecyclerView.Adapter<KostRecyclerAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    List<KostClientAccess> mData;
    List<KostClientAccess> filteredDataList;
    private Context mContext;

    private String uid;

    //Firebase
    private FirebaseAuth auth;
    private FirebaseUser user;

    public KostRecyclerAdapter(Context context, List<KostClientAccess> mData) {
        this.mContext = context;
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_kost_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KostRecyclerAdapter.MyViewHolder holder, int position) {
        final KostClientAccess kost = mData.get(position);
        Log.i("DAPET NAMA KOST : ", "" + kost.getNama_kost());
        holder.nama_kost.setText(kost.getNama_kost());
        holder.alamat.setText(kost.getAlamat());
        holder.cost.setText(kost.getHarga_sewa());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama_kost,alamat,cost;
        MaterialButton btnEdit, btnDelete;
        String snama, salamat, sharga, slongitude, slatitude, sgambar;
        LinearLayout mParent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_kost = itemView.findViewById(R.id.nama_kost);
            alamat = itemView.findViewById(R.id.alamat);
            cost = itemView.findViewById(R.id.cost);
            btnEdit = itemView.findViewById(R.id.btn_edit);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snama = mData.get(getAdapterPosition()).getNama_kost();
                    salamat = mData.get(getAdapterPosition()).getAlamat();
                    sharga = mData.get(getAdapterPosition()).getHarga_sewa();
                    slongitude = mData.get(getAdapterPosition()).getLongitude();
                    slatitude = mData.get(getAdapterPosition()).getLatitude();
                    sgambar = mData.get(getAdapterPosition()).getGambar();

                    Intent i = new Intent(itemView.getContext(), EditKostActivity.class);
                    i.putExtra("snama", snama);
                    i.putExtra("salamat", salamat);
                    i.putExtra("sharga", sharga);
                    i.putExtra("slongitude", slongitude);
                    i.putExtra("slatitude", slatitude);
                    i.putExtra("sgambar", sgambar);

                    view.getContext().startActivity(i);
                }
            });
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteData(mData.get(getAdapterPosition()).getId());
                }
            });

            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
        }

        public void deleteData(String id){
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<KostResponse> call = apiInterface.deleteKost(id);

            call.enqueue(new Callback<KostResponse>() {
                @Override
                public void onResponse(Call<KostResponse> call, Response<KostResponse> response) {
                    Log.i("Quda : ", "Masuk RESPONSE ," + response.code() );
                }

                @Override
                public void onFailure(Call<KostResponse> call, Throwable t) {
                    Log.i("Quda : ", "Masuk FAILURE ," + t.getMessage() );
                }
            });
        }
    }
}

