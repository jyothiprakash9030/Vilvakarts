package com.example.vcartbusbooking.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://api-629-bis.vilvabusiness.com/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit_assets = null;


    private static final String BASE_URL_ASSETS = "http://api-629-bis.vilvabusiness.com/";


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return retrofit;
    }

    public static Retrofit getClientAssets() {
        if (retrofit_assets == null) {
            retrofit_assets = new Retrofit.Builder()
                    .baseUrl(BASE_URL_ASSETS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit_assets;
    }










}
