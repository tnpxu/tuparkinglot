package com.tnpxu.tuparkinglot.api.responsedata;

/**
 * Created by tnpxu on 5/5/16 AD.
 */


/** WRAPPING JSON hierarchy // MapDataResWrap --> MapDataRes --> AllData // **/

public class MapDataResWrap {

    MapDataRes mapDataRes;

    public MapDataResWrap() {
    }

    public MapDataRes getMapDataRes() {
        return mapDataRes;
    }

    public void setMapDataRes(MapDataRes mapDataRes) {
        this.mapDataRes = mapDataRes;
    }

}
