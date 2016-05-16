package com.tnpxu.tuparkinglot.api.responsedata;

/**
 * Created by tnpxu on 4/28/16 AD.
 */
public class AllData {

    private String name;
    private String latitude;
    private String longtitude;
    private String token;

    public AllData(String name, String latitude, String longtitude, String token) {
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.token = token;
    }

    public AllData() {
        name = "";
        latitude = "";
        longtitude = "";
        token = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
