package com.tnpxu.tuparkinglot.api;

import com.tnpxu.tuparkinglot.api.requestdata.MapDataReq;
import com.tnpxu.tuparkinglot.api.requestdata.MapDetailReq;
import com.tnpxu.tuparkinglot.api.responsedata.AllData;
import com.tnpxu.tuparkinglot.api.responsedata.AllDataParkingDetail;
import com.tnpxu.tuparkinglot.api.responsedata.AllParkingDetailRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataResWrap;
import com.tnpxu.tuparkinglot.api.responsedata.ParkingDetailWrap;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;

/**
 * Created by tnpxu on 4/27/16 AD.
 */
public interface CallingLocationData {

    //Parking Map

    @POST("/api/mobile/datamap")
    Call<MapDataResWrap> getDataMap(@Body MapDataReq dataReq);

    //Parking Detail
    @POST("/api/mobile/allparkingdetail")
    Call<List<AllDataParkingDetail>> getAllParkingDetail(@Body MapDetailReq mapDetailReq);

    //Parking Detail
    @POST("/api/mobile/parkingdetail")
    Call<ParkingDetailWrap> getParkingDetail(@Body MapDetailReq mapDetailReq);

}
