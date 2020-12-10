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
import com.calvindo.aldi.sutanto.tubes.API.KostResponse;
import com.calvindo.aldi.sutanto.tubes.API.TransaksiResponse;
import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.Database.TransaksiClientAccess;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.models.Kost;
import com.calvindo.aldi.sutanto.tubes.models.ListKost;
import com.calvindo.aldi.sutanto.tubes.models.Transactions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    private int lamaSewa = 1;
    String uid;
    private String idTrans;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String harga_sewa;
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
        private TextView twIdtrans, twNamaKost, twDurasi, twTotal;
        private MaterialButton mbEdit, mbDelete;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            twIdtrans = itemView.findViewById(R.id.twIdtrans);
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
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String harga = kursIndonesia.format(transactions.getTotal_pembayaran());
        holder.twIdtrans.setText(transactions.getId());
        holder.twNamaKost.setText(transactions.getId_kost());
        holder.twTotal.setText(harga);
        holder.twDurasi.setText(String.valueOf(transactions.getLama_sewa()));

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();

        holder.mbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(transactions.getId());
                Toast.makeText(context, "Booking id : "+ transactions.getId() +" has been deleted ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());

                View dialogV = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_booking, null);

                TextView namaKost = dialogV.findViewById(R.id.twNamaKost);
                namaKost.setText(transactions.getId_kost());
                edLamaSewa = dialogV.findViewById(R.id.edLamaSewa);

                builder.setView(dialogV);
                builder.setCancelable(true);
                builder.show();

                final ArrayAdapter<String> adapterSewa = new ArrayAdapter<>(context,
                        R.layout.dd_list, R.id.dd_list, saLamaSewa);

                edLamaSewa.setAdapter(adapterSewa);
                edLamaSewa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lamaSewa = Integer.parseInt(saLamaSewa[i]);
                        Log.i(TAG, "lama sewa : " + lamaSewa + "saLamaSewa :" + saLamaSewa[i]);
                    }
                });

                ApiInterface  apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<KostResponse> kost = apiInterface.getKostbyID(transactions.getId_kost());

                kost.enqueue(new Callback<KostResponse>() {
                    @Override
                    public void onResponse(Call<KostResponse> call, Response<KostResponse> response) {
                        harga_sewa = response.body().getSingleKost().getHarga_sewa();
                        MaterialButton btnBooking = dialogV.findViewById(R.id.btnBooking);
                        btnBooking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i("QUDA", "ToTAL BAYAR :: " + Double.parseDouble(harga_sewa) * lamaSewa );
                                updateData(transactions, lamaSewa, Double.parseDouble(harga_sewa) * lamaSewa);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<KostResponse> call, Throwable t) {
                        Log.i("QUDA : ",t.getMessage());
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public void updateData(TransaksiClientAccess transaksiClientAccess,  int lamaSewa, double totalbayar){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse> request = apiService.updateTransaksi(
            transaksiClientAccess.getId(),
            transaksiClientAccess.getId_user(),
            transaksiClientAccess.getId_kost(),
                lamaSewa, totalbayar
        );

        request.enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                Toast.makeText(context, "Berhasil dab : " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(context, "Gagal dab : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findKostbyID(String id){

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
}
