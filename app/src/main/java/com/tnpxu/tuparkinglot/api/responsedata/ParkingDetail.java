package com.tnpxu.tuparkinglot.api.responsedata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tnpxu on 5/6/16 AD.
 */
public class ParkingDetail {

    private List<SlotStatus> slotStatusList;
    private String parkingStatus;
    private String slotSize;
    private String carCount;

    public List<SlotStatus> getSlotStatusList() {
        return slotStatusList;
    }

    public void setSlotStatusList(List<SlotStatus> slotStatusList) {
        this.slotStatusList = slotStatusList;
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

    public ParkingDetail() {
    }

    public void add(SlotStatus slotStatus){
        if(slotStatusList == null){
            slotStatusList = new ArrayList<>();
        }
        slotStatusList.add(slotStatus);
    }

}
