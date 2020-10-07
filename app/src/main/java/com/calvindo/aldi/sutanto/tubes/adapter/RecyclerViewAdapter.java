package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
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

import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.databinding.CardviewBinding;
import com.calvindo.aldi.sutanto.tubes.models.Kost;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Kost> mData;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Kost> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardviewBinding cardviewBinding = CardviewBinding.inflate(layoutInflater, parent, false);
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



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private Button btn_fav;
        CardviewBinding cardviewBinding;


        public MyViewHolder(@NonNull CardviewBinding cardviewBinding) {

            super(cardviewBinding.getRoot());
            this.cardviewBinding = cardviewBinding;


        }
    }
}
