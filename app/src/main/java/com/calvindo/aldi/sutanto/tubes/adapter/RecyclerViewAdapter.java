package com.calvindo.aldi.sutanto.tubes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.TransaksiResponse;
import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.KostOnMAP;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.databinding.CardviewBinding;
import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Kost;
import com.calvindo.aldi.sutanto.tubes.models.Transactions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "QUDA";

    List<KostClientAccess> mData;
    private Context mContext;
    private final String[] saLamaSewa = new String[]{"1", "2", "3", "4", "5"};
    private AutoCompleteTextView edLamaSewa;
    private int lamaSewa = 1;
//    private KostClientAccess kost;

    private String uid, id_transaksi;
    private String id;
    private Double cost, totalBayar;

    //Firebase
    private FirebaseAuth auth;
    private FirebaseUser user;

    public RecyclerViewAdapter(Context context, List<KostClientAccess> mData) {
        this.mContext = context;
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview, parent, false);
//        mContext = parent.getContext();

//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final KostClientAccess kost = mData.get(position);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        double harga_sewa = Double.parseDouble(kost.getHarga_sewa());
        String harga = kursIndonesia.format(harga_sewa) + "/ bulan";

        holder.nama.setText(kost.getNama_kost());
        holder.alamat.setText(kost.getAlamat());
        holder.cost.setText(harga);
        Glide.with(mContext)
                .load(kost.getGambar())
                .apply(new RequestOptions().override(100, 150))
                .into(holder.image);

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = kost.getLatitude();
                String longitude = kost.getLongitude();
                Intent intent = new Intent(view.getContext(), KostOnMAP.class);
                intent.putExtra("LONGITUDE", longitude);
                intent.putExtra("LATITUDE", latitude);
                view.getContext().startActivity(intent);
            }
        });

        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());

                View dialogV = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_booking, null);

                TextView namaKost = dialogV.findViewById(R.id.twNamaKost);
                namaKost.setText(kost.getNama_kost());
                edLamaSewa = dialogV.findViewById(R.id.edLamaSewa);

                builder.setView(dialogV);
                builder.setCancelable(true);
                builder.show();

                final ArrayAdapter<String> adapterSewa = new ArrayAdapter<>(mContext,
                        R.layout.dd_list, R.id.dd_list, saLamaSewa);

                edLamaSewa.setAdapter(adapterSewa);
                edLamaSewa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lamaSewa = Integer.parseInt(saLamaSewa[i]);
                        Log.i(TAG, "lama sewa : " + lamaSewa + "saLamaSewa :" + saLamaSewa[i]);
                    }
                });

//                    int kost_id = mData.get(getAdapterPosition()).getId();
                id = kost.getId();



                MaterialButton btnBooking = dialogV.findViewById(R.id.btnBooking);

                btnBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cost = Double.parseDouble(kost.getHarga_sewa());
                        uid = user.getUid();

                        totalBayar = lamaSewa * cost;
                        Log.i(TAG, "total bayar: " + totalBayar + "lama sewa: " + lamaSewa + "cost: " + cost);


                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//TransaksiClientAccess(String id, String id_user, String id_kost, int lama_sewa, double total_pembayaran)
                        retrofit2.Call<TransaksiResponse> add = apiService.createTransaksi(
                                uid,
                                id,
                                lamaSewa,
                                totalBayar
                        );

                        add.enqueue(new Callback<TransaksiResponse>() {
                            @Override
                            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
//                    Toast.makeText(mContext, "uid: "+uid+
//                                                  "id_kost: "+id+
//                                                  "lama sewa: "+lamaSewa+
//                                                  "total bayar: "+totalBayar, Toast.LENGTH_LONG).show();
                                Log.i("Quda : ", "Masuk RESPONSE ," + response.body());
                            }

                            @Override
                            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                                Log.i("Gagal: ", t.getMessage());
                            }
                        });
                        Log.i("Gagal Dab: ", "kepencet dab");
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, kota, alamat, latitude, longitude, hp, cost;
        ImageView image;
        int status;
        String id;
        double totalBayar;
        Button btnBook, btnMap;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();

            nama = itemView.findViewById(R.id.nama_kost);
            alamat = itemView.findViewById(R.id.namakota);
            hp = itemView.findViewById(R.id.noHPOwner);
            cost = itemView.findViewById(R.id.cost);
            image = itemView.findViewById(R.id.kota_image);
            btnBook = itemView.findViewById(R.id.btn_booking);
            btnMap = itemView.findViewById(R.id.btn_map);
//            Glide.with(getActivity())
//                    .load(avatar)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(avatarIv);

//            btnFav.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "onClick: " + mData.get(getAdapterPosition()));
//
//                    nama = mData.get(getAdapterPosition()).getNama_kost();
//                    alamat = mData.get(getAdapterPosition()).getAlamat();
//                    latitude = mData.get(getAdapterPosition()).getLatitude();
//                    longitude = mData.get(getAdapterPosition()).getLongitude();
//
//                    cost = mData.get(getAdapterPosition()).getHarga_sewa();
//                    uid = user.getUid();
//                }
//            });
//
//            cardviewBinding.btnMap.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    latitude = mData.get(getAdapterPosition()).getLatitude();
//                    longitude = mData.get(getAdapterPosition()).getLongitude();
//                    Intent intent = new Intent(view.getContext(), KostOnMAP.class);
//                    intent.putExtra("LONGITUDE", longitude);
//                    intent.putExtra("LATITUDE", latitude);
//                    view.getContext().startActivity(intent);
//                }
//            });
//
//            cardviewBinding.btnBooking.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
//
//                    View dialogV = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_booking, null);
//
//                    TextView namaKost = dialogV.findViewById(R.id.twNamaKost);
//                    namaKost.setText(mData.get(getAdapterPosition()).getNama_kost());
//                    edLamaSewa = dialogV.findViewById(R.id.edLamaSewa);
//
//                    builder.setView(dialogV);
//                    builder.setCancelable(true);
//                    builder.show();
//
//                    final ArrayAdapter<String> adapterSewa = new ArrayAdapter<>(mContext,
//                            R.layout.dd_list, R.id.dd_list, saLamaSewa);
//
//                    edLamaSewa.setAdapter(adapterSewa);
//                    edLamaSewa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            lamaSewa = Integer.parseInt(saLamaSewa[i]);
//                            Log.i(TAG, "lama sewa : " + lamaSewa + "saLamaSewa :" + saLamaSewa[i]);
//                        }
//                    });
//
////                    int kost_id = mData.get(getAdapterPosition()).getId();
//                    id = mData.get(getAdapterPosition()).getId();
//
//                    cost = mData.get(getAdapterPosition()).getHarga_sewa();
//                    uid = user.getUid();
//
//                    totalBayar = lamaSewa * Double.parseDouble(cost);
//                    Log.i(TAG, "total bayar: " + totalBayar + "lama sewa: " + lamaSewa + "cost: " + cost);
//
//
//                    MaterialButton btnBooking = dialogV.findViewById(R.id.btnBooking);
//
//                    btnBooking.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            booking();
//                            Log.i("Gagal Dab: ", "kepencet dab");
//                        }
//                    });
//                }
//            });
        }



        public void booking() {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//TransaksiClientAccess(String id, String id_user, String id_kost, int lama_sewa, double total_pembayaran)
            retrofit2.Call<TransaksiResponse> add = apiService.createTransaksi(
                    uid,
                    id,
                    lamaSewa,
                    totalBayar
            );

            add.enqueue(new Callback<TransaksiResponse>() {
                @Override
                public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
//                    Toast.makeText(mContext, "uid: "+uid+
//                                                  "id_kost: "+id+
//                                                  "lama sewa: "+lamaSewa+
//                                                  "total bayar: "+totalBayar, Toast.LENGTH_LONG).show();
                    Log.i("Quda : ", "Masuk RESPONSE ," + response.body());
                }

                @Override
                public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                    Log.i("Gagal: ", t.getMessage());
                }
            });
        }
    }
}
