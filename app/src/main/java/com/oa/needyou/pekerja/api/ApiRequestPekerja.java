package com.oa.needyou.pekerja.api;

import com.oa.needyou.pekerja.beranda.model.ResultSaldo;
import com.oa.needyou.pekerja.beranda.model.SaldoResponModel;
import com.oa.needyou.pekerja.model.OrderModel;
import com.oa.needyou.pekerja.model.OrderResponModel;
import com.oa.needyou.pekerja.model.PekerjaModel;
import com.oa.needyou.pelanggan.model.ResponseModelPelanggan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestPekerja {

    @FormUrlEncoded
    @POST("add_pekerja.php")
    Call<ResponseModelPelanggan> addPelanggan(@Field("nama_pelanggan") String nama_pelanggan,
                                              @Field("email_pelanggan") String email_pelanggan,
                                              @Field("password_pelanggan") String password_pelanggan);
    @GET("read_one_pekerja.php")
    Call<List<PekerjaModel>> readOnePekerja(@Query("email_pekerja") String username, @Query("pass_pekerja") String password);

    @GET("read_one_id.php")
    Call<List<PekerjaModel>> readOneIDPekerja(@Query("id_pekerja") String id_pekerja);

    @GET("read_one_order.php")
    Call<OrderModel> getDataOrderSingle(@Query("kode_order") String kode_order);

    @GET("read_kode_order.php")
    Call<OrderModel> getDataOrderkode(@Query("kode_order") String kode_order1);

    @GET("read_order_id_pekerja.php")
    Call<OrderModel> getDataOrderIdPekerja(@Query("id_pekerja") String id_pekerja);

    @FormUrlEncoded
    @POST("update_status_order.php")
    Call<OrderResponModel> updateStatusOrder(@Field("id_order") String id_order,
                                             @Field("status_order") String status_order);

    @GET("read_saldo_pekerja.php")
    Call<SaldoResponModel> getDataSaldo(@Query("saldo_id_pekerja") String saldo_id_pekerja);

    @FormUrlEncoded
    @POST("update_saldo_pekerja.php")
    Call<OrderResponModel> updateSaldoPekerja(@Field("saldo_id_pekerja") String saldo_id_pekerja,
                                             @Field("jumlah_saldo") String jumlah_saldo);


}
