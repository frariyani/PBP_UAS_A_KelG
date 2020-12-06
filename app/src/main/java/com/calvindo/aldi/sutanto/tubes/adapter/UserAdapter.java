package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Context context;
    List<User> userList;
//    User user;

    //constructor
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView tvEmail, tvUsername, tvNama, tvAlamat, tvNoTelp;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.twEmail);
            tvUsername = itemView.findViewById(R.id.twUsername);
            tvNama = itemView.findViewById(R.id.twNama);
            tvAlamat = itemView.findViewById(R.id.twAlamat);
            tvNoTelp = itemView.findViewById(R.id.twNoTelp);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        final User user =  userList.get(position);

        holder.tvEmail.setText(user.getEmail());
        holder.tvUsername.setText(user.getUsername());
        holder.tvNama.setText(user.getNama());
        holder.tvAlamat.setText(user.getAlamat());
        holder.tvNoTelp.setText(user.getNotelp());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
