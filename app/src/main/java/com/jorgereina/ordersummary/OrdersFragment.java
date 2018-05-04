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
import android.widget.ProgressBar;
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

    private CardView ordersByProvince;
    private CardView ordersByYear;
    private TextView ordersByProvinceTv;
    private TextView ordersByYearTv;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;

    private List<Order> orderList = new ArrayList<>();
    private List<Order> fulfilledOrdersList = new ArrayList<>();
    private List<Order> pendingPaymentOrdersList = new ArrayList<>();
    private List<Order> cancelledOrdersList = new ArrayList<>();
    private List<Order> partiallyPaidOrdersList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_fragment, container, false);
        ordersByProvince = view.findViewById(R.id.orders_by_province_cv);
        ordersByYear = view.findViewById(R.id.orders_by_year);
        ordersByProvinceTv = view.findViewById(R.id.orders_to_fulfill_counter_tv);
        ordersByYearTv = view.findViewById(R.id.pending_payments_counter_tv);
        progressBar1 = view.findViewById(R.id.pb1);
        progressBar2 = view.findViewById(R.id.pb2);
        showProgressBar();
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
            Call<OrderResponse> orders = api.getOrders();

            orders.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    Log.d(TAG, "onResponse: " + response.body().getOrders().get(0).getShippingAddress().getProvince());
                    showProgressBar();
                    orderList.clear();
                    orderList.addAll(response.body().getOrders());
                    ordersByProvince(orderList);
                    ordersByYear(orderList);
                    hideProgressBar();

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

    private void ordersByProvince(final List<Order> orderList) {

        //TODO: IMPLEMENT THIS AND CHANGE

        Log.d(TAG, "orderstofulfill: " + orderList.get(3).getFulfillmentStatus());
        fulfilledOrdersList.clear();
        for (Order order : orderList) {
            if (order.getFulfillmentStatus() == null
                    || order.getFulfillmentStatus().equals("partial")) {
                fulfilledOrdersList.add(order);
            }
        }
        ordersByProvinceTv.setText(fulfilledOrdersList.size() + "");

        onCardViewSelection(ordersByProvince, fulfilledOrdersList);
    }



    private void ordersByYear(List<Order> orderList) {

        //TODO: IMPLEMENT THIS AND CHANGE

        Log.d(TAG, "ordersByYear: " + orderList.get(3).getPaymentStatus());
        pendingPaymentOrdersList.clear();
        for (Order order : orderList) {
            if (order.getPaymentStatus().equals("pending")) {
                pendingPaymentOrdersList.add(order);
            }
        }
        ordersByYearTv.setText(pendingPaymentOrdersList.size() + "");

        onCardViewSelection(ordersByYear, pendingPaymentOrdersList);
    }

    private void onCardViewSelection(CardView cardView, final List<Order> orderList) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailedCategoryFragment detailedCategoryFragment =
                        DetailedCategoryFragment.newInstance(orderList);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, detailedCategoryFragment, "DetailedFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void showProgressBar() {
        ordersByProvinceTv.setVisibility(View.GONE);
        ordersByYearTv.setVisibility(View.GONE);
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        ordersByProvinceTv.setVisibility(View.VISIBLE);
        ordersByYearTv.setVisibility(View.VISIBLE);
        progressBar1.setVisibility(View.GONE);
        progressBar2.setVisibility(View.GONE);

    }
}
