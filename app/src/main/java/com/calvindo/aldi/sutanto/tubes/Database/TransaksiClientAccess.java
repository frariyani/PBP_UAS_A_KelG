package com.calvindo.aldi.sutanto.tubes.Database;

import com.google.gson.annotations.SerializedName;

public class TransaksiClientAccess {
    @SerializedName("id")
    private String id;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("id_kost")
    private String id_kost;

    @SerializedName("lama_sewa")
    private int lama_sewa;

    @SerializedName("total_pembayaran")
    private double total_pembayaran;

    public TransaksiClientAccess(String id, String id_user, String id_kost, int lama_sewa, double total_pembayaran) {
        this.id = id;
        this.id_user = id_user;
        this.id_kost = id_kost;
        this.lama_sewa = lama_sewa;
        this.total_pembayaran = total_pembayaran;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_kost() {
        return id_kost;
    }

    public void setId_kost(String id_kost) {
        this.id_kost = id_kost;
    }

    public int getLama_sewa() {
        return lama_sewa;
    }

    public void setLama_sewa(int lama_sewa) {
        this.lama_sewa = lama_sewa;
    }

    public double getTotal_pembayaran() {
        return total_pembayaran;
    }

    public void setTotal_pembayaran(double total_pembayaran) {
        this.total_pembayaran = total_pembayaran;
    }
}
