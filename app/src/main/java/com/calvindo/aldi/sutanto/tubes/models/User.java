package com.calvindo.aldi.sutanto.tubes.models;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

public class User extends BaseObservable {
    private String avatar;
    private String nama;
    private String username;
    private String email;
    private String notelp;
    private String alamat;

    public User(){}

    public User(String avatar, String nama, String username, String email, String notelp, String alamat) {
        this.avatar = avatar;
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.notelp = notelp;
        this.alamat = alamat;
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

//    @BindingAdapter("bind:src")
//    public static void loadImage(ImageView imageView, String imageURL){
//        Glide.with(imageView.getContext())
//                .setDefaultRequestOptions(new RequestOptions())
//                .load(imageURL)
//                .into(imageView);
//    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
