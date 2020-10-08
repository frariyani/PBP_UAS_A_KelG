package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.FavDAO;
import com.calvindo.aldi.sutanto.tubes.MainActivity;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.databinding.CardviewBinding;
import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Kost;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    List<Kost> mData;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Kost> mData) {
        this.mContext = context;
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardviewBinding cardviewBinding = CardviewBinding.inflate(layoutInflater, parent, false);

//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);

        return new MyViewHolder(cardviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Kost kost = mData.get(position);
        holder.cardviewBinding.setKost(kost);
        holder.cardviewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        String nama,kota,alamat,latitude,longitude,hp,image;
        int status;
        double cost;
        CardviewBinding cardviewBinding;

        public MyViewHolder(@NonNull CardviewBinding cardviewBinding) {

            super(cardviewBinding.getRoot());
            this.cardviewBinding = cardviewBinding;

            cardviewBinding.btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"onClick: " + mData.get(getAdapterPosition()) );
                    nama = mData.get(getAdapterPosition()).getName();
                    kota = mData.get(getAdapterPosition()).getKota();
                    alamat = mData.get(getAdapterPosition()).getAlamat();
                    latitude = mData.get(getAdapterPosition()).getLatitude();
                    longitude = mData.get(getAdapterPosition()).getLongitude();
                    hp = mData.get(getAdapterPosition()).getHPOwner();
                    image = mData.get(getAdapterPosition()).getImage();
                    cost = mData.get(getAdapterPosition()).getCost();
                    status = mData.get(getAdapterPosition()).getStatus();

                    addFav();
                    Toast toast = Toast.makeText(view.getContext(), "Item has been added to Favorites!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        public void addFav(){
            class AddFav extends AsyncTask<Void,Void,Void>{
                @Override
                protected Void doInBackground(Void... voids) {
                    Favorites favorites = new Favorites(
                            nama,kota,alamat,latitude,longitude,hp,cost,image,status);

                    DatabaseClient.getInstance(mContext).getDatabase()
                            .favDAO().insert(favorites);
                    return null;
                }
            }

            AddFav add = new AddFav();
            add.execute();
        }
    }
}
