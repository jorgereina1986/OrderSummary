package com.jorgereina.ordersummary.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by jorgereina on 5/4/18.
 */

@Parcel
public class ShippingAddress {

    public String province;

    public ShippingAddress() {
    }

    public ShippingAddress(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
