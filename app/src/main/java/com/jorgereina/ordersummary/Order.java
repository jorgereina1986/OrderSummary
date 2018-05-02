package com.jorgereina.ordersummary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jorgereina on 5/1/18.
 */

public class Order {

    private long id;
    private String email;
    @SerializedName("financial_status")
    private String paymentStatus;
    @SerializedName("cancelled_at")
    private String cancelled;
    @SerializedName("")
    private String fulfillmentStatus;

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
}
