package com.calvindo.aldi.sutanto.tubes.API;

import androidx.room.Query;
import androidx.room.Update;

import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("kost")
    Call<KostResponse> getAllKost();

    @POST("kost")
    @FormUrlEncoded
    Call<KostResponse> createKost(@Field("nama_kost") String nama,
                                  @Field("alamat") String alamat,
                                  @Field("longitude") String longitude,
                                  @Field("latitude") String latitude,
                                  @Field("harga_sewa") String harga_sewa,
                                  @Field("gambar") String gambar);

    @POST("kost/{id}")
    @FormUrlEncoded
    Call<KostResponse> updateKost(@Path("id")String id,
                                  @Field("nama_kost") String nama,
                                  @Field("alamat") String alamat,
                                  @Field("longitude") String longitude,
                                  @Field("latitude") String latitude,
                                  @Field("harga_sewa") String harga_sewa,
                                  @Field("gambar") String gambar);

    @DELETE("kost/{id}")
    Call<KostResponse> deleteKost(@Path("id")String id);

    @GET("transaksi/{id}")
    Call<TransaksiResponse> getAllTransaksi(@Path("id")String uid);

    @POST("transaksi")
    @FormUrlEncoded
    Call<TransaksiResponse> createTransaksi(@Field("id_user")String id_user,
                                            @Field("id_kost")String id_kost,
                                            @Field("lama_sewa") int lama_sewa,
                                            @Field("total_pembayaran") double total_pembayaran);

    @PUT("transaksi/{id}")
    @FormUrlEncoded
    Call<TransaksiResponse> updateTransaksi(@Path("id")int id,
                                            @Field("id_user")String id_user,
                                            @Field("id_kost")String id_kost,
                                            @Field("lama_sewa") int lama_sewa,
                                            @Field("total_pembayaran") double total_pembayaran);

    @DELETE("transaksi/{id}")
    Call<TransaksiResponse> deleteTransaksi(@Path("id")String id);

}
