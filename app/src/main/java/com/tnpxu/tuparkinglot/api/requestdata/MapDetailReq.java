package com.tnpxu.tuparkinglot.api.requestdata;

import com.google.gson.annotations.SerializedName;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataRes;

/**
 * Created by tnpxu on 5/5/16 AD.
 */
public class MapDetailReq {

    @SerializedName("token")
    String token;

    public MapDetailReq(String token) {
        this.token = token;
    }

    public MapDetailReq() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
