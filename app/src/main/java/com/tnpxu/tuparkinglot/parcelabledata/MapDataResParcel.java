package com.tnpxu.tuparkinglot.parcelabledata;

import android.os.Parcel;
import android.os.Parcelable;

import com.tnpxu.tuparkinglot.api.responsedata.AllData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tnpxu on 4/27/16 AD.
 */
public class MapDataResParcel implements Parcelable {

    List<AllData> allData;

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private MapDataResParcel(Parcel in) {
        allData = new ArrayList<AllData>();
        in.readList(allData,null);
    }

    public MapDataResParcel() {
        // Normal actions performed by class, since this is still a normal object!
    }

    public List<AllData> getAllData() {
        return allData;
    }

    public void setAllData(List<AllData> allData) {
        this.allData = allData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeList(allData);
    }


    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<MapDataResParcel> CREATOR = new Parcelable.Creator<MapDataResParcel>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public MapDataResParcel createFromParcel(Parcel in) {
            return new MapDataResParcel(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public MapDataResParcel[] newArray(int size) {
            return new MapDataResParcel[size];
        }
    };
}
