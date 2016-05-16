package com.tnpxu.tuparkinglot.fragment;

/**
 * Created by tnpxu on 4/21/16 AD.
 */
public class BookmarkObject {

    private String iconFlag;
    private String parkingName;

    public BookmarkObject(String iconFlag,String parkingName) {
        this.iconFlag = iconFlag;
        this.parkingName = parkingName;
    }

    public String getIconFlag() {
        return iconFlag;
    }

    public String getParkingName() {
        return parkingName;
    }
}
