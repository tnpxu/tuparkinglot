package com.tnpxu.tuparkinglot.api.responsedata;

/**
 * Created by tnpxu on 5/5/16 AD.
 */
public class AllDataParkingDetail {

    private String token;
    private String parkinglotName;
    private String captureDate;
    private String parkingStatus;
    private String slotSize;
    private String carCount;

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

    public String getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(String parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public String getSlotSize() {
        return slotSize;
    }

    public void setSlotSize(String slotSize) {
        this.slotSize = slotSize;
    }

    public String getCarCount() {
        return carCount;
    }

    public void setCarCount(String carCount) {
        this.carCount = carCount;
    }


    public AllDataParkingDetail() {}
}

//        [
//        {
//        "token": "b4df-bbe4",
//        "parkinglotName": "sc1",
//        "captureDate": "1462448643933",
//        "parkingStatus": "yellow",
//        "slotSize": "19",
//        "carCount": "12"
//        }
//        ]