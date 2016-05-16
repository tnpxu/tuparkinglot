package com.tnpxu.tuparkinglot.api.responsedata;

/**
 * Created by tnpxu on 5/6/16 AD.
 */
public class ParkingDetailWrap {

    private String token;
    private String parkinglotName;
    private String captureDate;
    private ParkingDetail parkingDetail;
    private String parkingPicUrl;
    private String parkingWidth;
    private String parkingHeight;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParkinglotName() {
        return parkinglotName;
    }

    public void setParkinglotName(String parkinglotName) {
        this.parkinglotName = parkinglotName;
    }

    public String getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }

    public ParkingDetail getParkingDetail() {
        return parkingDetail;
    }

    public void setParkingDetail(ParkingDetail parkingDetail) {
        this.parkingDetail = parkingDetail;
    }

    public String getParkingPicUrl() {
        return parkingPicUrl;
    }

    public void setParkingPicUrl(String parkingPicUrl) {
        this.parkingPicUrl = parkingPicUrl;
    }

    public String getParkingWidth() {
        return parkingWidth;
    }

    public void setParkingWidth(String parkingWidth) {
        this.parkingWidth = parkingWidth;
    }

    public String getParkingHeight() {
        return parkingHeight;
    }

    public void setParkingHeight(String parkingHeight) {
        this.parkingHeight = parkingHeight;
    }


    public ParkingDetailWrap() {
    }
}
