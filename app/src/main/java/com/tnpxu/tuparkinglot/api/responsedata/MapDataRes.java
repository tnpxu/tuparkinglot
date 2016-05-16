package com.tnpxu.tuparkinglot.api.responsedata;

import com.tnpxu.tuparkinglot.api.requestdata.MapDataReq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tnpxu on 4/27/16 AD.
 */
public class MapDataRes {

    private List<AllData> allData;

    public MapDataRes(List<AllData> allData) {
        this.allData = allData;
    }

    public MapDataRes() {

    }

    public List<AllData> getAllData() {

        return allData;
    }

    public void setAllData(List<AllData> allData) {
        this.allData = allData;
    }

    public void add(AllData allDataEach){
        if(allData == null){
            allData = new ArrayList<>();
        }
        allData.add(allDataEach);
    }

}
