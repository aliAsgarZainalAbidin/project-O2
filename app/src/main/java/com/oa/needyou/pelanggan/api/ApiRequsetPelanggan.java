package com.oa.needyou.pelanggan.api;

import com.oa.needyou.pekerja.model.OrderResponModel;
import com.oa.needyou.pekerja.model.PekerjaModel;
import com.oa.needyou.pelanggan.model.OrderModel;
import com.oa.needyou.pelanggan.model.PelangganModel;
import com.oa.needyou.pelanggan.model.ResponseModelPelanggan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequsetPelanggan {

    @FormUrlEncoded
    @POST("add_pelanggan.php")
    Call<ResponseModelPelanggan> addPelanggan(@Field("nama_pelanggan") String nama_pelanggan,
                                              @Field("email_pelanggan") String email_pelanggan,
                                              @Field("password_pelanggan") String password_pelanggan);

    @FormUrlEncoded
    @POST("add_order_pelanggan.php")
    Call<OrderModel> addOrderan(@Field("id_pekerja") String id_pekerja,
                                @Field("id_pelanggan") String id_pelanggan,
                                @Field("path_order") String path_order,
                                @Field("keterangan_order") String keterangan_order,
                                @Field("biaya_order") String biaya_order,
                                @Field("alamat_order") String alamat_order,
                                @Field("latitude_order") String latitude_order,
                                @Field("longitude_order") String longitude_order,
                                @Field("status_order") String status_order,
                                @Field("tanggal_order") String tanggal_order,
                                @Field("jam_order") String jam_order,
                                @Field("kode_order") String kode_order);


    @GET("cek_email.php")
    Call<List<PelangganModel>> cekEmail(@Query("email_pelanggan") String email_pelanggan);


    @GET("read_order_id_pelanggan.php")
    Call<com.oa.needyou.pekerja.model.OrderModel> getDataOrderIdPelanggan(@Query("id_pelanggan") String id_pelanggan);

    @GET("read_id_pelanggan.php")
    Call<List<PelangganModel>> readIdPelanggan(@Query("id_pelanggan") String id_pelanggan);

    @GET("read_one_pelanggan.php")
    Call<List<PelangganModel>> readOnePelanggan(@Query("email_pelanggan") String username, @Query("password_pelanggan") String password);

    @FormUrlEncoded
    @POST("update_rating_order.php")
    Call<OrderResponModel> updateRatingOrder(@Field("id_order") String id_order,
                                             @Field("rating_order") String rating_order);

    @FormUrlEncoded
    @POST("update_pelanggan.php")
    Call<OrderResponModel> updateDataPelanggan(@Field("id_pelanggan") String id_pelanggan,
                                               @Field("nama_pelanggan") String nama_pelanggan,
                                               @Field("email_pelanggan") String email_pelanggan,
                                               @Field("telpon_pelanggan") String telpon_pelanggan,
                                               @Field("gender_pelanggan") String gender_pelanggan,
                                               @Field("alamat_pelanggan") String alamat_pelanggan,
                                               @Field("path_pelanggan") String path_pelanggan);

}
