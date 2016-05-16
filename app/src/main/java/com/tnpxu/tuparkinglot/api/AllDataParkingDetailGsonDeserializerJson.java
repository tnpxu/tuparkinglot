package com.tnpxu.tuparkinglot.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tnpxu.tuparkinglot.api.responsedata.AllData;
import com.tnpxu.tuparkinglot.api.responsedata.AllDataParkingDetail;
import com.tnpxu.tuparkinglot.api.responsedata.AllParkingDetailRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataResWrap;

import java.lang.reflect.Type;

/**
 * Created by tnpxu on 5/5/16 AD.
 */
public class AllDataParkingDetailGsonDeserializerJson implements JsonDeserializer<AllParkingDetailRes> {

    @Override
    public AllParkingDetailRes deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {
        AllParkingDetailRes allParkingDetailRes = new AllParkingDetailRes();


        JsonArray allParkingDetailResChunk = je.getAsJsonArray();
        for(JsonElement element : allParkingDetailResChunk){

            JsonObject fetchJson = element.getAsJsonObject();

            AllDataParkingDetail allDataParkingDetail = new AllDataParkingDetail();
            allDataParkingDetail.setToken(fetchJson.get("token").getAsString());
            allDataParkingDetail.setParkinglotName(fetchJson.get("parkinglotName").getAsString());
            allDataParkingDetail.setParkingStatus(fetchJson.get("yellow").getAsString());
            allDataParkingDetail.setSlotSize(fetchJson.get("slotSize").getAsString());
            allDataParkingDetail.setCarCount(fetchJson.get("carCount").getAsString());
            allParkingDetailRes.add(allDataParkingDetail);
        }

        return allParkingDetailRes;
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
