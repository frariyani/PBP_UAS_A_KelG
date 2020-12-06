package com.calvindo.aldi.sutanto.tubes.Database;

import com.google.gson.annotations.SerializedName;

public class UserDAO {
    @SerializedName("id")
    private String id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("prodi")
    private String prodi;

    @SerializedName("fakultas")
    private String fakultas;

    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;

    @SerializedName("password")
    private String password;

    public UserDAO(String id, String nama, String nim, String prodi, String fakultas, String jenis_kelamin, String password) {
        this.id = id;
        this.nama = nama;
        this.email = nim;
        this.prodi = prodi;
        this.fakultas = fakultas;
        this.jenis_kelamin = jenis_kelamin;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

//    public String getProdi() {
//        return prodi;
//    }
//
//    public String getFakultas() {
//        return fakultas;
//    }
//
//    public String getJenis_kelamin() {
//        return jenis_kelamin;
//    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String nim) {
        this.email = nim;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
