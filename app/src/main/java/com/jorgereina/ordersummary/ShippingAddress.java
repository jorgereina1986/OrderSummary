package com.jorgereina.ordersummary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jorgereina on 5/4/18.
 */

public class ShippingAddress {

    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
