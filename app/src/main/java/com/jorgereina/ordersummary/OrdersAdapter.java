package com.jorgereina.ordersummary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

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
        holder.id.setText(order.getId() + "");
        holder.price.setText("$" + order.getTotalPrice());
        holder.name.setText(order.getOrderName());
        holder.date.setText(getOrderDateCreated(order.getDateCreated()));
        if (order.getShippingAddress() != null) {
            holder.province.setText(order.getShippingAddress().getProvince());
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrdersViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView name;
        private TextView email;
        private TextView price;
        private TextView date;
        private TextView province;

        public OrdersViewHolder(View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.item_email_tv);
            id = itemView.findViewById(R.id.item_order_id_tv);
            name = itemView.findViewById(R.id.item_order_name_tv);
            price = itemView.findViewById(R.id.item_total_price_tv);
            date = itemView.findViewById(R.id.item_date_tv);
            province = itemView.findViewById(R.id.item_province_tv);
        }
    }

    private String getOrderDateCreated(String orderDate) {
        DateTimeFormatter formatter = DateTimeFormat
                .forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                .withLocale(Locale.CANADA);

        LocalDate date = formatter.parseLocalDate(orderDate);
        return date.getMonthOfYear() + "/" + date.getDayOfMonth() + "/" + date.getYear();
    }
}
