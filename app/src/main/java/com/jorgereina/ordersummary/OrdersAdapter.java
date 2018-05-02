package com.jorgereina.ordersummary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jorgereina on 5/2/18.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.email.setText(order.getEmail());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrdersViewHolder extends RecyclerView.ViewHolder {

        private TextView email;

        public OrdersViewHolder(View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.item_email_tv);
        }
    }
}
