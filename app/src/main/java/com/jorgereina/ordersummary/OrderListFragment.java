package com.jorgereina.ordersummary;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorgereina.ordersummary.model.Order;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorgereina on 5/2/18.
 */

public class OrderListFragment extends Fragment {

    private static final String ORDER_LIST_PARCEL = "order_list_parcel";

    private RecyclerView recyclerView;
    private OrdersAdapter adapter;
    private List<Order> orderList = new ArrayList<>();

    public static OrderListFragment newInstance(List<Order> orders) {

        Bundle args = new Bundle();
        args.putParcelable(ORDER_LIST_PARCEL, Parcels.wrap(orders));

        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailed_category_fragment, container, false);

        recyclerView = view.findViewById(R.id.orders_rv);
        adapter = new OrdersAdapter(getActivity(), orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        List<Order> list = Parcels.unwrap(bundle.getParcelable(ORDER_LIST_PARCEL));
        orderList.clear();
        orderList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
