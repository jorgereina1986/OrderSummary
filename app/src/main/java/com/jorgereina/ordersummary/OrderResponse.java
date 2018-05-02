package com.jorgereina.ordersummary;

import java.util.List;

/**
 * Created by jorgereina on 5/1/18.
 */

public class OrderResponse {

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
