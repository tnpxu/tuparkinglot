package com.tnpxu.tuparkinglot.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.tnpxu.tuparkinglot.api.responsedata.AllParkingDetailRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataResWrap;
import com.tnpxu.tuparkinglot.api.responsedata.ParkingDetailWrap;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by tnpxu on 11/12/2558.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = "http://parkingserver.cloudapp.net:3000";
//    public static final String API_BASE_URL = "http://192.168.43.185:3000";

//    public static final String API_BASE_URL = "http://172.16.105.153:3000";

    //custom deserialzation
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(MapDataResWrap.class, new MapDataGsonDeserializerJson())
            .registerTypeAdapter(AllParkingDetailRes.class, new AllDataParkingDetailGsonDeserializerJson())
            .registerTypeAdapter(ParkingDetailWrap.class, new ParkingDetailGsonDeserializarJson())
            .create();

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
