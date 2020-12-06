package com.calvindo.aldi.sutanto.tubes.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.TransaksiResponse;
import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.TransaksiClientAccess;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.models.Transactions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Path;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    Context context;
    List<TransaksiClientAccess> transactionsList;
    AutoCompleteTextView edLamaSewa;
    private final String[] saLamaSewa = new String[]{ "1", "2", "3", "4", "5"};
    private int lamaSewa=1;
    String uid;
    private String idTrans;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Double totalbayar;
    private ProgressDialog pd;

    public TransaksiAdapter(Context context, List<TransaksiClientAccess> transactionsList) {
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaksi, parent,false);

        return new TransaksiViewHolder(view);
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder{
        private TextView twNamaUser, twNamaKost, twDurasi, twTotal;
        private MaterialButton mbEdit, mbDelete;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
//            twNamaUser = itemView.findViewById(R.id.twNamaUser);
            mbEdit = itemView.findViewById(R.id.btnEdit);
            twNamaKost = itemView.findViewById(R.id.twNamaKost);
            twDurasi = itemView.findViewById(R.id.twDurasi);
            twTotal = itemView.findViewById(R.id.twTotalBayar);
            mbDelete = itemView.findViewById(R.id.btnDelete);



        }
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.TransaksiViewHolder holder, int position) {
        final TransaksiClientAccess transactions = transactionsList.get(position);

        holder.twNamaKost.setText(transactions.getId_kost());
        holder.twTotal.setText(String.valueOf(transactions.getTotal_pembayaran()));
        holder.twDurasi.setText(String.valueOf(transactions.getLama_sewa()));

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();

        idTrans = transactions.getId();


        holder.mbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(idTrans);
                Toast.makeText(context, "ID trans: "+idTrans, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public void update(final String id){
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
////        Call<TransaksiResponse> updateTransaksi(@Path("id")int id,
////        @Field("id_user")String id_user,
////        @Field("id_kost")String id_kost,
////        @Field("lama_sewa") int lama_sewa,
////        @Field("total_pembayaran") double total_pembayaran);
//        Call<TransaksiResponse> request = apiService.updateTransaksi(idTrans, uid, id, lamaSewa, )
//
//        request.enqueue(new Callback<TransaksiResponse>() {
//            @Override
//            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
//
//            }
//        });
    }

    public void delete(final String id_trans){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse> req = apiService.deleteTransaksi(id_trans);

        req.enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(context, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void deleteData(String id){
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<KostResponse> call = apiInterface.deleteKost(id);
//
//        call.enqueue(new Callback<KostResponse>() {
//            @Override
//            public void onResponse(Call<KostResponse> call, Response<KostResponse> response) {
//                Toast.makeText(itemView.getContext(),"Data Berhasil di Hapus", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<KostResponse> call, Throwable t) {
//                Toast.makeText(itemView.getContext(),"Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
