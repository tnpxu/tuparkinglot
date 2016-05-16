package com.tnpxu.tuparkinglot.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tnpxu.tuparkinglot.api.responsedata.ParkingDetail;
import com.tnpxu.tuparkinglot.api.responsedata.ParkingDetailWrap;
import com.tnpxu.tuparkinglot.api.responsedata.SlotStatus;

import java.lang.reflect.Type;

/**
 * Created by tnpxu on 5/6/16 AD.
 */
public class ParkingDetailGsonDeserializarJson implements JsonDeserializer<ParkingDetailWrap> {

    @Override
    public ParkingDetailWrap deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {

        ParkingDetailWrap parkingDetailWrap = new ParkingDetailWrap();

        //1st chunck
        JsonObject parkingDetailWrapJson = je.getAsJsonObject();
        parkingDetailWrap.setToken(parkingDetailWrapJson.get("token").getAsString());
        parkingDetailWrap.setParkinglotName(parkingDetailWrapJson.get("parkinglotName").getAsString());
        parkingDetailWrap.setCaptureDate(parkingDetailWrapJson.get("captureDate").getAsString());
        parkingDetailWrap.setParkingPicUrl(parkingDetailWrapJson.get("parkingPicUrl").getAsString());
        parkingDetailWrap.setParkingWidth(parkingDetailWrapJson.get("parkingWidth").getAsString());
        parkingDetailWrap.setParkingHeight(parkingDetailWrapJson.get("parkingHeight").getAsString());

        //parkingdetail chunck
        JsonObject parkingDetailJson = parkingDetailWrapJson.get("parkingDetail").getAsJsonObject();
        ParkingDetail parkingDetail = new ParkingDetail();
        parkingDetail.setParkingStatus(parkingDetailJson.get("parkingStatus").getAsString());
        parkingDetail.setSlotSize(parkingDetailJson.get("slotSize").getAsString());
        parkingDetail.setCarCount(parkingDetailJson.get("carCount").getAsString());

        JsonArray slotStatusJson = parkingDetailJson.get("slotStatus").getAsJsonArray();
        for(JsonElement element : slotStatusJson) {

            JsonObject slotStatusChunk = element.getAsJsonObject();
            SlotStatus slotStatus = new SlotStatus();
            slotStatus.setSlotID(slotStatusChunk.get("slotID").getAsInt());
            slotStatus.setIsAvailable(slotStatusChunk.get("isAvailable").getAsBoolean());
            slotStatus.setX(slotStatusChunk.get("x").getAsString());
            slotStatus.setY(slotStatusChunk.get("y").getAsString());
            slotStatus.setWidth(slotStatusChunk.get("width").getAsString());
            slotStatus.setHeight(slotStatusChunk.get("height").getAsString());

            parkingDetail.add(slotStatus);

        }

        parkingDetailWrap.setParkingDetail(parkingDetail);


        return parkingDetailWrap;
    }
}
