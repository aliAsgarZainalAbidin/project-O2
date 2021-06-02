package com.oa.needyou.pekerja.api;

import com.oa.needyou.pekerja.beranda.model.ResultSaldo;
import com.oa.needyou.pekerja.beranda.model.SaldoResponModel;
import com.oa.needyou.pekerja.model.OrderModel;
import com.oa.needyou.pekerja.model.OrderResponModel;
import com.oa.needyou.pekerja.model.PekerjaModel;
import com.oa.needyou.pelanggan.model.ResponseModelPelanggan;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiRequestPekerja {

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

    @Multipart
    @POST("uploadGambar.php")
    Call<RequestBody> uploadGambar(@Part MultipartBody.Part body);

    @FormUrlEncoded
    @POST("update_pekerja.php")
    Call<OrderResponModel> updateDataPekerja(@Field("id_pekerja") String id_pekerja,
                                               @Field("nama_pekerja") String nama_pekerja,
                                               @Field("usia_pekerja") String usia_pekerja,
                                               @Field("pekerjaan_pekerja") String pekerjaan_pekerja,
                                               @Field("gender_pekerja") String gender_pekerja,
                                               @Field("email_pekerja") String email_pekerja,
                                             @Field("telpon_pekerja") String telpon_pekerja,
                                               @Field("path_profil") String path_profil);


}
