package com.jorgereina.ordersummary;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jorgereina on 5/1/18.
 */

public interface ShopifyAPI {

    //https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6
    @GET("admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6")
    Call<OrderResponse> getOrders();
}
