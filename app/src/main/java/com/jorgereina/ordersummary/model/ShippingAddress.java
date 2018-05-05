package com.jorgereina.ordersummary.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jorgereina on 5/4/18.
 */

public class ShippingAddress {

    private String province;

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
