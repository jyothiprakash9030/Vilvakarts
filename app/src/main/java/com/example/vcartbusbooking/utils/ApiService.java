package com.example.vcartbusbooking.utils;


import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/admin/signin")
    Call<SigninResponse> signin(@Body SigninRequest signinRequest);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer pZQpIV1kLqkBXmltpLTrfQ17m3xJiukhzW3PkroV"
    })
    @POST("api/prod/ticket/source")
    Call<CityResponse> GetCities(@Body RequestBody body);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer pZQpIV1kLqkBXmltpLTrfQ17m3xJiukhzW3PkroV"
    })
    @POST("api/prod/ticket/source")
    Call<ResponseBody> postTicketSource(@Body RequestBody body);


    @FormUrlEncoded
    @POST("api/admin/dashboard")
    Call<DashboardResponse> getDashboardData(
            @Header("Authorization") String token,
            @Field("storeid") String storeId
    );

    @Headers("Content-Type: application/json")
    @POST("api/admin/orders/notpacked")
    Call<NotPackedOrdersResponse> getNotPackedOrders(@Header("Authorization") String token,
                                                     @Body RequestBody body);

    @POST("api/admin/order/packed/update")
    Call<UpdateOrderResponse> updateOrder(
            @Header("Authorization") String authorization,
            @Body UpdateOrderRequest requestBody
    );

    @GET("ecomsaas/redisapi/couriers.json")
    Call<List<Courier>> getCouriers(@Query("version") long version);
}
