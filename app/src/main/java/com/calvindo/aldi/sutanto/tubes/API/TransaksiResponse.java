package com.calvindo.aldi.sutanto.tubes.API;

import com.calvindo.aldi.sutanto.tubes.Database.TransaksiClientAccess;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransaksiResponse {
    @SerializedName("data")
    @Expose
    private List<TransaksiClientAccess> transaksi = null;

    @SerializedName("message")
    @Expose
    private String message;

    public List<TransaksiClientAccess> getTransaksi(){
        return transaksi;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
