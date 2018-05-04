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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jorgereina on 5/2/18.
 */

public class DetailedCategoryFragment extends Fragment {

    private static final String ORDER_LIST_PARCEL = "orderlist_parcel";

    private RecyclerView recyclerView;
    private OrdersAdapter adapter;
    private List<Order> orderList = new ArrayList<>();



    public static DetailedCategoryFragment newInstance(List<Order> orders) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(ORDER_LIST_PARCEL, (ArrayList<? extends Parcelable>) orders);

        DetailedCategoryFragment fragment = new DetailedCategoryFragment();
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
        List<Order> list = bundle.getParcelableArrayList(ORDER_LIST_PARCEL);
        orderList.clear();
        orderList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
