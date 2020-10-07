package com.calvindo.aldi.sutanto.tubes.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Kost {

    private String name;
    private String kota;
    private String alamat;
    private String latitude;
    private String longitude;
    private String HPOwner;
    private Double cost;
    private String image;

    public Kost(String name, String kota, String alamat, String latitude, String longitude, String HPOwner, Double cost, String image) {
        this.name = name;
        this.kota = kota;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.HPOwner = HPOwner;
        this.cost = cost;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHPOwner() {
        return HPOwner;
    }

    public void setHPOwner(String HPOwner) {
        this.HPOwner = HPOwner;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String imgURL){
        Glide.with(imageView)
                .load(imgURL)
                .into(imageView);
    }
}
