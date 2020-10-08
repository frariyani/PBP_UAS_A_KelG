package com.calvindo.aldi.sutanto.tubes.models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;

import java.io.Serializable;


@Entity(tableName = "favorites")
public class Favorites implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "kota")
    private String kota;

    @ColumnInfo(name = "alamat")
    private String alamat;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "HPOwner")
    private String HPOwner;

    @ColumnInfo(name = "cost")
    private Double cost;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "status")
    private int status;

    public Favorites(String name, String kota, String alamat, String latitude, String longitude, String HPOwner, Double cost, String image, int status) {
        this.name = name;
        this.kota = kota;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.HPOwner = HPOwner;
        this.cost = cost;
        this.image = image;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
//    @BindingAdapter("android:loadImage")
//    public static void loadImage(ImageView imageView, String imgURL){
//        Glide.with(imageView)
//                .load(imgURL)
//                .into(imageView);
//    }
}
