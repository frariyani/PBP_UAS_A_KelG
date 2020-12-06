package com.calvindo.aldi.sutanto.tubes.Database;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;

public class KostClientAccess {
    @SerializedName("id")
    private String id;

    @SerializedName("nama_kost")
    private String nama_kost;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("harga_sewa")
    private String harga_sewa;

    @SerializedName("gambar")
    private String gambar;

    public KostClientAccess(String id, String nama_kost, String alamat, String longitude, String latitude, String harga_sewa, String gambar) {
        this.id = id;
        this.nama_kost = nama_kost;
        this.alamat = alamat;
        this.longitude = longitude;
        this.latitude = latitude;
        this.harga_sewa = harga_sewa;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_kost() {
        return nama_kost;
    }

    public void setNama_kost(String nama_kost) {
        this.nama_kost = nama_kost;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getHarga_sewa() {
        return harga_sewa;
    }

    public void setHarga_sewa(String harga_sewa) {
        this.harga_sewa = harga_sewa;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
//
//    @BindingAdapter("android:loadImage")
//    public static void loadImage(ImageView imageView, String imgURL){
//        Glide.with(imageView)
//                .load(imgURL)
//                .into(imageView);
//    }
}
