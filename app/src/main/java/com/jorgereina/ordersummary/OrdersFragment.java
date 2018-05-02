package com.jorgereina.ordersummary;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
    private TextView partiallyPaidOrdersTv;

    private List<Order> orderList = new ArrayList<>();
    private List<Order> fulfilledOrdersList = new ArrayList<>();
    private List<Order> pendingPaymentOrdersList = new ArrayList<>();
    private List<Order> cancelledOrdersList = new ArrayList<>();
    private List<Order> partiallyPaidOrdersList = new ArrayList<>();

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
        partiallyPaidOrdersTv = view.findViewById(R.id.partially_paid_counter_tv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchData();
    }

    private void fetchData() {

        if (((MainActivity) getActivity()).isNetworkAvailable()) {
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
                    partiallyPaidOrders(orderList);
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_LONG).show();
        }
    }

    private void ordersToFulfill(final List<Order> orderList) {

        Log.d(TAG, "orderstofulfill: " + orderList.get(3).getFulfillmentStatus());
        for (Order order : orderList) {
            if (order.getFulfillmentStatus() == null
                    || order.getFulfillmentStatus().equals("partial")) {
                fulfilledOrdersList.add(order);
            }
        }
        fulfilledOrdersTv.setText(fulfilledOrdersList.size() + "");

        fulfilledOrdersCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailedCategoryFragment detailedCategoryFragment = DetailedCategoryFragment.newInstance(orderList);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, detailedCategoryFragment, "DetailedFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void pendingPayments(List<Order> orderList) {

        Log.d(TAG, "pendingPayments: " + orderList.get(3).getPaymentStatus());
        for (Order order : orderList) {

            if (order.getPaymentStatus().equals("pending")) {
                pendingPaymentOrdersList.add(order);
            }
        }
        pendingPaymentsTv.setText(pendingPaymentOrdersList.size() + "");
    }

    private void cancelledOrders(List<Order> orderList) {

        Log.d(TAG, "cancelledOrders: " + orderList.get(3).getCancelled());
        for (Order order : orderList) {
            if (order.getCancelled() != null) {
                cancelledOrdersList.add(order);
            }
        }
        cancelledOrdersTv.setText(cancelledOrdersList.size() + "");
    }

    private void partiallyPaidOrders(List<Order> orderList) {

        Log.d(TAG, "pendingPayments: " + orderList.get(3).getCancelled());
        for (Order order : orderList) {
            if (order.getPaymentStatus().equals("partially_paid")) {
                partiallyPaidOrdersList.add(order);
            }
        }
        partiallyPaidOrdersTv.setText(cancelledOrdersList.size() + "");
    }
}
