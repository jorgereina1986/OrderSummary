package com.jorgereina.ordersummary;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jorgereina on 5/1/18.
 */

public class Order implements Parcelable {

    private long id;
    private String email;
    @SerializedName("financial_status")
    private String paymentStatus;
    @SerializedName("cancelled_at")
    private String cancelled;
    @SerializedName("")
    private String fulfillmentStatus;
    @SerializedName("name")
    private String orderName;
    @SerializedName("total_price_usd")
    private String totalPrice;

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

    protected Order(Parcel in) {
        id = in.readLong();
        email = in.readString();
        paymentStatus = in.readString();
        cancelled = in.readString();
        fulfillmentStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(email);
        parcel.writeString(paymentStatus);
        parcel.writeString(cancelled);
        parcel.writeString(fulfillmentStatus);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
