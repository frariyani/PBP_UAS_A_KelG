package com.calvindo.aldi.sutanto.tubes.API;

import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.Database.UserDAO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KostResponse {
    @SerializedName("data")
    @Expose
    private List<KostClientAccess> kost;

    @SerializedName("message")
    @Expose
    private String message;

    public List<KostClientAccess> getKost() {
        return kost;
    }

    public void setKost(List<KostClientAccess> users) {
        this.kost = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
