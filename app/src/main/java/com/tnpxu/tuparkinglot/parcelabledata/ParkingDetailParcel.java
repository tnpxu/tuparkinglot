package com.tnpxu.tuparkinglot.parcelabledata;

import android.os.Parcel;
import android.os.Parcelable;

import com.tnpxu.tuparkinglot.api.responsedata.AllData;
import com.tnpxu.tuparkinglot.api.responsedata.ParkingDetail;
import com.tnpxu.tuparkinglot.api.responsedata.SlotStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tnpxu on 5/6/16 AD.
 */
public class ParkingDetailParcel implements Parcelable {

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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.slotStatusList);
        dest.writeString(this.parkingStatus);
        dest.writeString(this.slotSize);
        dest.writeString(this.carCount);
    }

    public ParkingDetailParcel() {
    }

    protected ParkingDetailParcel(Parcel in) {
        this.slotStatusList = new ArrayList<SlotStatus>();
        in.readList(this.slotStatusList, SlotStatus.class.getClassLoader());
        this.parkingStatus = in.readString();
        this.slotSize = in.readString();
        this.carCount = in.readString();
    }

    public static final Creator<ParkingDetailParcel> CREATOR = new Creator<ParkingDetailParcel>() {
        @Override
        public ParkingDetailParcel createFromParcel(Parcel source) {
            return new ParkingDetailParcel(source);
        }

        @Override
        public ParkingDetailParcel[] newArray(int size) {
            return new ParkingDetailParcel[size];
        }
    };
}
