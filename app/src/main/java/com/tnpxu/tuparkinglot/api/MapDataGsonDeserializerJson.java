package com.tnpxu.tuparkinglot.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tnpxu.tuparkinglot.api.responsedata.AllData;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataResWrap;


import java.lang.reflect.Type;

/**
 * Created by tnpxu on 5/5/16 AD.
 */
public class MapDataGsonDeserializerJson implements JsonDeserializer<MapDataResWrap> {
    @Override
    public MapDataResWrap deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {
        MapDataResWrap mapDataResWrap = new MapDataResWrap();
        MapDataRes mapDataRes = new MapDataRes();


        JsonObject mapDataResWrapJsonChunk = je.getAsJsonObject();
        JsonObject mapDataResJsonChunk = mapDataResWrapJsonChunk.get("MapDataRes").getAsJsonObject();
        JsonArray allDataJsonChunk = mapDataResJsonChunk.get("AllData").getAsJsonArray();
        for(JsonElement element : allDataJsonChunk){

            JsonObject fetchJson = element.getAsJsonObject();

            AllData allData = new AllData();
            allData.setName(fetchJson.get("name").getAsString());
            allData.setLatitude(fetchJson.get("latitude").getAsString());
            allData.setLongtitude(fetchJson.get("longtitude").getAsString());
            allData.setToken(fetchJson.get("token").getAsString());
            mapDataRes.add(allData);
        }

        mapDataResWrap.setMapDataRes(mapDataRes);


        return mapDataResWrap;
    }
}


// JSON Pattern
//{
//        "MapDataRes": {
//        "AllData": [
//        {
//        "name": "engr1",
//        "latitude": "14.027016799011212",
//        "longtitude": "100.59494018554688",
//        "token": "a7c0-ac65"
//        },
//        {
//        "name": "sc1",
//        "latitude": "14.0636532638738",
//        "longtitude": "100.60257911682129",
//        "token": "df8c-8595"
//        }
//}
