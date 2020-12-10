package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.R;

import java.util.List;

public class KostAdminAdapter extends RecyclerView.Adapter<KostAdminAdapter.KostViewHolder> {
    List<KostClientAccess> listKost;
    Context context;

    public KostAdminAdapter(List<KostClientAccess> listKost, Context context) {
        this.listKost = listKost;
        this.context = context;
    }

    @NonNull
    @Override
    public KostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_kost_admin, parent, false);

        return new KostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull KostAdminAdapter.KostViewHolder holder, int position) {
        final KostClientAccess kost = listKost.get(position);

        holder.tvNama.setText(kost.getNama_kost());
        holder.tvAlamat.setText(kost.getAlamat());
        holder.tvCost.setText(String.valueOf(kost.getHarga_sewa()));
        Glide.with(context)
                .load(kost.getGambar())
                .apply(new RequestOptions().override(100, 150))
                .into(holder.ivGambar);
    }

    @Override
    public int getItemCount() {
        return listKost.size();
    }

    class KostViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGambar;
        TextView tvNama, tvAlamat, tvCost;
        Button btnDelete, btnEdit;

        public KostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_kost);
            tvAlamat = itemView.findViewById(R.id.alamat);
            tvCost = itemView.findViewById(R.id.cost);

            ivGambar = itemView.findViewById(R.id.imgkost);
        }
    }
}
