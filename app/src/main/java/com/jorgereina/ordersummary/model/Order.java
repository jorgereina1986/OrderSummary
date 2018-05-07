package com.jorgereina.ordersummary.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by jorgereina on 5/1/18.
 */

@Parcel
public class Order {

    public long id;
    public String email;
    @SerializedName("financial_status")
    public String paymentStatus;
    @SerializedName("cancelled_at")
    public String cancelled;
    @SerializedName("")
    public String fulfillmentStatus;
    @SerializedName("name")
    public String orderName;
    @SerializedName("total_price_usd")
    public String totalPrice;
    @SerializedName("shipping_address")
    public ShippingAddress shippingAddress;
    @SerializedName("created_at")
    public String dateCreated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
