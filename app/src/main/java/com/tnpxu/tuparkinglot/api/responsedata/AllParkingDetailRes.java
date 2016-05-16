package com.tnpxu.tuparkinglot.api.responsedata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tnpxu on 5/5/16 AD.
 */
public class AllParkingDetailRes {

    List<AllDataParkingDetail> allDataParkingDetails;

    public List<AllDataParkingDetail> getAllDataParkingDetails() {
        return allDataParkingDetails;
    }

    public void setAllDataParkingDetails(List<AllDataParkingDetail> allDataParkingDetails) {
        this.allDataParkingDetails = allDataParkingDetails;
    }

    public AllParkingDetailRes() {}

    public void add(AllDataParkingDetail allDataEach){
        if(allDataParkingDetails == null){
            allDataParkingDetails = new ArrayList<>();
        }
        allDataParkingDetails.add(allDataEach);
    }

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