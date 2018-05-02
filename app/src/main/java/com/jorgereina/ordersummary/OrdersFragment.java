package com.jorgereina.ordersummary;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jorgereina on 5/1/18.
 */

public class OrdersFragment extends Fragment {

    private static final String BASE_URL = "https://shopicruit.myshopify.com/";
    private static final String TAG = "lagarto";

    private CardView fulfilledOrdersCv;
    private CardView pendingPaymentsCv;
    private CardView cancelledOrdersCv;
    private TextView fulfilledOrdersTv;
    private TextView pendingPaymentsTv;
    private TextView cancelledOrdersTv;

    private List<Order> orderList = new ArrayList<>();
    private List<Order> fulfilledOrdersList = new ArrayList<>();
    private List<Order> pendingPaymentsList = new ArrayList<>();
    private List<Order> cancelledOrdersList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_fragment, container, false);
        fulfilledOrdersCv = view.findViewById(R.id.orders_to_fulfill_cv);
        pendingPaymentsCv = view.findViewById(R.id.pending_payments_cv);
        cancelledOrdersCv = view.findViewById(R.id.cancelled_orders_cv);
        fulfilledOrdersTv = view.findViewById(R.id.orders_to_fulfill_counter_tv);
        pendingPaymentsTv = view.findViewById(R.id.pending_payments_counter_tv);
        cancelledOrdersTv = view.findViewById(R.id.cancelled_orders_counter_tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchData();


    }

    private void fetchData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopifyAPI api = retrofit.create(ShopifyAPI.class);
        final Call<OrderResponse> orders = api.getOrders();

        orders.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().getOrders().get(0).getEmail());
                orderList.addAll(response.body().getOrders());
                ordersToFulfill(orderList);
                pendingPayments(orderList);
                cancelledOrders(orderList);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ordersToFulfill(List<Order> orderList) {

        Log.d(TAG, "orderstofulfill: " + orderList.get(3).getFulfillmentStatus());
        for (Order order : orderList) {
            if (order.getFulfillmentStatus() != null) {
                fulfilledOrdersList.add(order);
            }
        }
        fulfilledOrdersTv.setText(fulfilledOrdersList.size()+"");
    }

    private void pendingPayments(List<Order> orderList) {

        Log.d(TAG, "pendingPayments: " + orderList.get(3).getPaymentStatus());
        for (Order order : orderList) {

            if (!order.getPaymentStatus().equals("paid")) {
                pendingPaymentsList.add(order);
            }
        }
        pendingPaymentsTv.setText(pendingPaymentsList.size()+"");
    }

    private void cancelledOrders(List<Order> orderList) {

        Log.d(TAG, "pendingPayments: " + orderList.get(3).getCancelled());
        for (Order order : orderList) {
            if (order.getCancelled() != null) {
                cancelledOrdersList.add(order);
            }
        }
        cancelledOrdersTv.setText(cancelledOrdersList.size()+"");
    }

    public interface OrderSelectedListener {
        void onItemSelected(Order order);
    }
}
