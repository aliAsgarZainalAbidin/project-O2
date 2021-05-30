package com.oa.needyou.pelanggan.api;

import com.oa.needyou.URL_IP;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServerPelanggan {

    private final static String LINK_API = URL_IP.ALAMAT_IP+ "api_needyou/pelanggan/";
//    private final static String LINK_API = URL_IP.ALAMAT_IP+ "https://needyou.id/api_needyou/pelanggan/";

    private static Retrofit retrofit;

    public static Retrofit getClientPelanggan(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(LINK_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
