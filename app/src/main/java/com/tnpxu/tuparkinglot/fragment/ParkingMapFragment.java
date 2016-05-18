package com.tnpxu.tuparkinglot.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tnpxu.tuparkinglot.MainActivity;
import com.tnpxu.tuparkinglot.ParkingDetailActivity;
import com.tnpxu.tuparkinglot.R;
import com.tnpxu.tuparkinglot.api.responsedata.AllData;
import com.tnpxu.tuparkinglot.parcelabledata.AllDataInParcel;
import com.tnpxu.tuparkinglot.parcelabledata.MapDataParcel;
import com.tnpxu.tuparkinglot.parcelabledata.MapDataResParcel;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;


/**
 * Created by tnpxu on 3/28/16 AD.
 */
public class ParkingMapFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {

    private FragmentActivity myContext;
    private GoogleMap googleMapInstance;
    private HashMap<String,String> nameMapToken = new HashMap<>();


    public static ParkingMapFragment newInstance(Bundle myData) {

        ParkingMapFragment fragment = new ParkingMapFragment();
        fragment.setArguments(myData);
        return fragment;
    }

    public ParkingMapFragment () {}

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_parking_map, container, false);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //transaction map fragment
        MapFragment mapFragment = (MapFragment) myContext.getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if(savedInstanceState != null) {
            // Restore state here

        }

    }

    public void onDestroyView() {
        super.onDestroyView();

        //kill MapFragment avoid duplicate fragment
        MapFragment mapFragment = (MapFragment) myContext.getFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            myContext.getFragmentManager().beginTransaction().remove(mapFragment).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMapInstance = googleMap;

        MapDataParcel getParcel = getArguments().getParcelable("MapData");
        getArguments().remove("MapData");

        List<AllDataInParcel> getList = getParcel.getAllDataInParcels();

        if(!getList.isEmpty()) {

            //fetch all MapData to pin each location on map
            for (int i = 0; i < getList.size(); i++) {

                //modified data type
                AllDataInParcel dataA = getList.get(i);
                String name = dataA.getName();
                float latitude = Float.parseFloat(dataA.getLatitude());
                float longtitude = Float.parseFloat(dataA.getLongtitude());
                String token = dataA.getToken();

                float flagColor = 0f;
                switch (dataA.getParkingStatus()) {
                    case "red" : flagColor = BitmapDescriptorFactory.HUE_RED;
                        break;
                    case "yellow" : flagColor = BitmapDescriptorFactory.HUE_YELLOW;
                        break;
                    case "green" : flagColor = BitmapDescriptorFactory.HUE_GREEN;
                        break;
                    default: flagColor = BitmapDescriptorFactory.HUE_RED;
                }

                //mapping name token
                nameMapToken.put(name,token);


                //pining
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longtitude))
                        .icon(BitmapDescriptorFactory.defaultMarker(flagColor))
                        .title(name)
                        .snippet("Total cars: " + dataA.getCarCount() + "/" + dataA.getSlotSize()));
            }
        }

        // Set a listener for info window events.
        googleMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        String tokenToSend = nameMapToken.get(marker.getTitle());
        intentTo(tokenToSend);

    }

    //intent to ParkingDetailActivity
    public void intentTo(String tokenToSend) {
        Intent intent = new Intent(myContext, ParkingDetailActivity.class);
        intent.putExtra("token",tokenToSend);
        startActivity(intent);
    }

}
