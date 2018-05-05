package com.jorgereina.ordersummary;

/**
 * Created by jorgereina on 5/4/18.
 */

public class OrderByProvinceComparator implements java.util.Comparator<Order> {

    @Override
    public int compare(Order order, Order t1) {
        return order.getShippingAddress().getProvince().compareTo(t1.getShippingAddress().getProvince());
    }
}
