package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.FavoriteFragment;
import com.calvindo.aldi.sutanto.tubes.HomeFragment;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.databinding.CardviewBinding;
import com.calvindo.aldi.sutanto.tubes.databinding.FavCardviewBinding;
import com.calvindo.aldi.sutanto.tubes.models.Kost;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private int id;
    List<Kost> mData;
    private Context mContext;
    FavoriteAdapter adapter;
    RecyclerView myRecyclerView;

    public FavoriteAdapter(Context context, List<Kost> mData) {
        this.mContext = context;
        this.mData = mData;
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
        final Kost kost = mData.get(position);
        holder.favCardviewBinding.setFav(kost);
        holder.favCardviewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        String nama,kota,alamat,latitude,longitude,hp,image;
        int status;
        double cost;
        FavCardviewBinding favCardviewBinding;

        public MyViewHolder(@NonNull FavCardviewBinding favCardviewBinding) {

            super(favCardviewBinding.getRoot());
            this.favCardviewBinding = favCardviewBinding;

            favCardviewBinding.btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"onClick: " + mData.get(getAdapterPosition()) );
                    id = mData.get(getAdapterPosition()).getId();

                    if (mData.get(getAdapterPosition()).getStatus() == 0){
                        mData.get(getAdapterPosition()).setStatus(1);
                        status = mData.get(getAdapterPosition()).getStatus();
                        update(id,1);
//                        getKost();
                        Toast toast = Toast.makeText(view.getContext(), "Successfully added to favorite", Toast.LENGTH_SHORT);
                        toast.show();
//                        myRecyclerView.setAdapter(adapter);
                    }else {
                        mData.get(getAdapterPosition()).setStatus(0);
                        status = mData.get(getAdapterPosition()).getStatus();
                        update(id,0);
//                        getKost();
                        Toast toast = Toast.makeText(view.getContext(), "Successfully removed to favorite", Toast.LENGTH_SHORT);
                        toast.show();
                    }
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


    }

}
