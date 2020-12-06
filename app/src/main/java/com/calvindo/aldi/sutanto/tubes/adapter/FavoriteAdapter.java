package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.KostOnMAP;
import com.calvindo.aldi.sutanto.tubes.databinding.FavCardviewBinding;
import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Kost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private int id;
    List<Kost> mData;
    List<Favorites> fav;
    private Context mContext;

    private String uid;

    //Firebase
    private FirebaseAuth auth;
    private FirebaseUser user;

    public FavoriteAdapter(Context context, List<Favorites> fav) {
        this.mContext = context;
        this.fav = fav;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FavCardviewBinding favCardviewBinding = FavCardviewBinding.inflate(layoutInflater, parent, false);

//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);

        return new MyViewHolder(favCardviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Favorites favorites = fav.get(position);
        holder.favCardviewBinding.setFav(favorites);
        holder.favCardviewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return fav.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        String nama,kota,alamat,latitude,longitude,hp,image;
        int status;
        double cost;
        FavCardviewBinding favCardviewBinding;

        public MyViewHolder(@NonNull FavCardviewBinding favCardviewBinding) {

            super(favCardviewBinding.getRoot());
            this.favCardviewBinding = favCardviewBinding;
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();


            favCardviewBinding.btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = fav.get(getAdapterPosition()).getIdkost();
                    uid = user.getUid();
                    if (fav.get(getAdapterPosition()).getStatus() == 0){
                        fav.get(getAdapterPosition()).setStatus(1);
                        status = fav.get(getAdapterPosition()).getStatus();
                        update(id,1);
                        Toast toast = Toast.makeText(view.getContext(), "Successfully added to favorite", Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
//                        mData.get(getAdapterPosition()).setStatus(0);
//                        status = mData.get(getAdapterPosition()).getStatus();
                        update(id,0);
                        delete(id,uid);
                        Toast toast = Toast.makeText(view.getContext(), "Successfully removed to favorite", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

            favCardviewBinding.btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    latitude = fav.get(getAdapterPosition()).getLatitude();
                    longitude = fav.get(getAdapterPosition()).getLongitude();
                    Intent intent = new Intent(view.getContext(), KostOnMAP.class);
                    intent.putExtra("LONGITUDE", longitude);
                    intent.putExtra("LATITUDE", latitude);
                    view.getContext().startActivity(intent);
                }
            });
        }
        public void update(final int id, final int status){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Kost kost = DatabaseClient.getInstance(mContext)
                            .getDatabase().kostDAO().findkostbyid(id);

                    kost.setStatus(status);

                    DatabaseClient.getInstance(mContext).getDatabase()
                            .kostDAO().update(kost);
                }
            }).start();
        }

        public void delete(final int id, String uid){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Favorites cekFav = DatabaseClient.getInstance(mContext)
                            .getDatabase().favDAO().fav(id,uid);

                    DatabaseClient.getInstance(mContext).getDatabase()
                            .favDAO().delete(cekFav);
                }
            }).start();
        }

    }

}
