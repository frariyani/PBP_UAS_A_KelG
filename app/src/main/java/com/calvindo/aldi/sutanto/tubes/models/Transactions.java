package com.calvindo.aldi.sutanto.tubes.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "transactions")
public class Transactions implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idkost")
    public int idkost;

    @ColumnInfo(name = "uid")
    public String uid;

    @ColumnInfo(name = "lamaSewa")
    public int lamaSewa;

    @ColumnInfo(name = "totalBayar")
    public double totalBayar;

    public Transactions(int idkost, String uid, int lamaSewa, double totalBayar) {
        this.idkost = idkost;
        this.uid = uid;
        this.lamaSewa = lamaSewa;
        this.totalBayar = totalBayar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdkost() {
        return idkost;
    }

    public void setIdkost(int idkost) {
        this.idkost = idkost;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getLamaSewa() {
        return lamaSewa;
    }

    public void setLamaSewa(int lamaSewa) {
        this.lamaSewa = lamaSewa;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }
}
