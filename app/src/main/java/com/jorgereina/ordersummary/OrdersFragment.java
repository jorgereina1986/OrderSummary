package com.jorgereina.ordersummary;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgereina.ordersummary.model.Order;
import com.jorgereina.ordersummary.model.OrderResponse;
import com.jorgereina.ordersummary.model.ShippingAddress;
import com.jorgereina.ordersummary.network.ShopifyAPI;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

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

    private CardView ordersByProvinceCv;
    private CardView ordersByYearCv;
    private TextView ordersByProvinceTv;
    private TextView ordersByYearTv;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;

    private List<Order> orderList = new ArrayList<>();
    private List<Order> ordersInYear2017 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_fragment, container, false);
        ordersByProvinceCv = view.findViewById(R.id.orders_by_province_cv);
        ordersByYearCv = view.findViewById(R.id.orders_by_year);
        ordersByProvinceTv = view.findViewById(R.id.orders_by_province_states_tv);
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

        String provinceText = "";
        Map<String, Integer> provinceMap = new TreeMap<>();

        for (Order order : orderList) {
            if (order.getShippingAddress() != null) {
                if (!provinceMap.containsKey(getProvinceName(order))) {
                    provinceMap.put(getProvinceName(order), 1);
                } else {
                    provinceMap.put(getProvinceName(order), provinceMap.get(getProvinceName(order))+1);
                }
            }
            else {
                order.setShippingAddress(new ShippingAddress(getString(R.string.no_shipping_info_available)));
            }
        }

        for (String key : provinceMap.keySet()) {
            provinceText = provinceText + key + " " + provinceMap.get(key) + "\n";
        }

        ordersByProvinceTv.setText(provinceText);

        Collections.sort(orderList, new OrderByProvinceComparator());
        onCardViewSelection(ordersByProvinceCv, orderList);
    }


    private void ordersByYear(List<Order> orderList) {

        ordersInYear2017.clear();
        for (Order order : orderList) {
            if (getOrderYear(order).equals("2017")) {
                ordersInYear2017.add(order);
            }
        }
        ordersByYearTv.setText(ordersInYear2017.size() + "");

        onCardViewSelection(ordersByYearCv, ordersInYear2017);
    }

    private String getProvinceName(Order order) {
        return order.getShippingAddress().getProvince();
    }

    private String getOrderYear(Order order) {

        DateTimeFormatter formatter = DateTimeFormat
                .forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                .withLocale(Locale.CANADA);

        LocalDate date = formatter.parseLocalDate(order.getDateCreated());

        return String.valueOf(date.getYear());
    }

    private void onCardViewSelection(CardView cardView, final List<Order> orderList) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderListFragment orderListFragment =
                        OrderListFragment.newInstance(orderList);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, orderListFragment, "DetailedFragment")
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
