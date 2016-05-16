package com.tnpxu.tuparkinglot.parcelabledata;

import android.os.Parcel;
import android.os.Parcelable;

import com.tnpxu.tuparkinglot.api.responsedata.ParkingDetail;

/**
 * Created by tnpxu on 5/8/16 AD.
 */
public class ParkingDetailWrapParcel implements Parcelable {

    private String token;
    private String parkinglotName;
    private String captureDate;
    private ParkingDetailParcel parkingDetailParcel;
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

    public ParkingDetailParcel getParkingDetailParcel() {
        return parkingDetailParcel;
    }

    public void setParkingDetailParcel(ParkingDetailParcel parkingDetailParcel) {
        this.parkingDetailParcel = parkingDetailParcel;
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




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.parkinglotName);
        dest.writeString(this.captureDate);
        dest.writeParcelable(this.parkingDetailParcel, flags);
        dest.writeString(this.parkingPicUrl);
        dest.writeString(this.parkingWidth);
        dest.writeString(this.parkingHeight);
    }

    public ParkingDetailWrapParcel() {
    }

    protected ParkingDetailWrapParcel(Parcel in) {
        this.token = in.readString();
        this.parkinglotName = in.readString();
        this.captureDate = in.readString();
        this.parkingDetailParcel = in.readParcelable(ParkingDetailParcel.class.getClassLoader());
        this.parkingPicUrl = in.readString();
        this.parkingWidth = in.readString();
        this.parkingHeight = in.readString();
    }

    public static final Creator<ParkingDetailWrapParcel> CREATOR = new Creator<ParkingDetailWrapParcel>() {
        @Override
        public ParkingDetailWrapParcel createFromParcel(Parcel source) {
            return new ParkingDetailWrapParcel(source);
        }

        @Override
        public ParkingDetailWrapParcel[] newArray(int size) {
            return new ParkingDetailWrapParcel[size];
        }
    };
}
