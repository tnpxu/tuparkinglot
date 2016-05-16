package com.tnpxu.tuparkinglot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.gson.Gson;
import com.tnpxu.tuparkinglot.api.CallingLocationData;
import com.tnpxu.tuparkinglot.api.ServiceGenerator;
import com.tnpxu.tuparkinglot.api.requestdata.MapDetailReq;
import com.tnpxu.tuparkinglot.api.responsedata.ParkingDetailWrap;
import com.tnpxu.tuparkinglot.fragment.BookmarkFragment;
import com.tnpxu.tuparkinglot.fragment.InformationFragment;
import com.tnpxu.tuparkinglot.fragment.ParkingDetailFragment;
import com.tnpxu.tuparkinglot.fragment.ParkingMapFragment;
import com.tnpxu.tuparkinglot.parcelabledata.ParkingDetailParcel;
import com.tnpxu.tuparkinglot.parcelabledata.ParkingDetailWrapParcel;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ParkingDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton imageViewRefresh;
    private ImageView imageViewBookmark;
    private ProgressBar progress;
    private String token;
    public ParkingDetailWrapParcel parkingDetailWrapParcel;

    public Handler detailHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            //hide downloading
            hideProgress();

            switch(msg.what) {
                case 1: showFragment();
                    break;
                default: showFragment();
            }
            return false;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail);

        //receive token
        Intent intent = getIntent();
        token = intent.getExtras().getString("token");
        intent.removeExtra("token");

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar_parking_detail);
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        toolbar.setNavigationIcon(R.mipmap.ic_back_resize);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageViewRefresh = (ImageButton)findViewById(R.id.refresh_view);
        imageViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killDetailFragment();
                getApi();
            }
        });

        progress = (ProgressBar) findViewById(R.id.progressBarParkingDetail);

        getApi();

    }

    public void killDetailFragment() {
        Fragment detailFragment = getSupportFragmentManager().findFragmentById(R.id.flContentParkingDetail);
        if (detailFragment != null)
            getSupportFragmentManager().beginTransaction().remove(detailFragment).commit();
    }

    public void hideProgress() {
        progress.setVisibility(ProgressBar.INVISIBLE);
        progress.getLayoutParams().height = 0;
        progress.getLayoutParams().width = 0;
    }

    public void showProgress() {
        progress.setVisibility(ProgressBar.VISIBLE);
        progress.getLayoutParams().height = progress.getLayoutParams().MATCH_PARENT;
        progress.getLayoutParams().width = progress.getLayoutParams().WRAP_CONTENT;
    }

    public void showFragment() {
        Bundle myData = new Bundle();
        myData.putParcelable("DetailData",parkingDetailWrapParcel);
        Fragment fragment = ParkingDetailFragment.newInstance(myData);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContentParkingDetail, fragment).commit();
    }

    public void getApi() {

        //progress downloading
        showProgress();

        //request body
        MapDetailReq mapDetailReq = new MapDetailReq();
        mapDetailReq.setToken(token);
        final CallingLocationData service = ServiceGenerator.createService(CallingLocationData.class);
        Call<ParkingDetailWrap> call = service.getParkingDetail(mapDetailReq);
        call.enqueue(new Callback<ParkingDetailWrap>() {
            @Override
            public void onResponse(Response<ParkingDetailWrap> response, Retrofit retrofit) {


                //store to parcel
                ParkingDetailParcel parkingDetailParcel = new ParkingDetailParcel();
                parkingDetailParcel.setSlotStatusList(response.body().getParkingDetail().getSlotStatusList());
                parkingDetailParcel.setParkingStatus(response.body().getParkingDetail().getParkingStatus());
                parkingDetailParcel.setCarCount(response.body().getParkingDetail().getCarCount());
                parkingDetailParcel.setSlotSize(response.body().getParkingDetail().getSlotSize());

                parkingDetailWrapParcel = new ParkingDetailWrapParcel();
                parkingDetailWrapParcel.setToken(response.body().getToken());
                parkingDetailWrapParcel.setCaptureDate(response.body().getCaptureDate());
                parkingDetailWrapParcel.setParkingDetailParcel(parkingDetailParcel);
                parkingDetailWrapParcel.setParkingHeight(response.body().getParkingHeight());
                parkingDetailWrapParcel.setParkingWidth(response.body().getParkingWidth());
                parkingDetailWrapParcel.setParkinglotName(response.body().getParkinglotName());
                parkingDetailWrapParcel.setParkingPicUrl(response.body().getParkingPicUrl());

                //store return data to parcel
//                parcel = new MapDataResParcel();
//                parcel.setAllData(response.body().getMapDataRes().getAllData());

//               Toast.makeText(ParkingDetailActivity.this,"" + new Gson().toJson(response),Toast.LENGTH_LONG).show();
//               Toast.makeText(ParkingDetailActivity.this,"" + response.body().getToken(),Toast.LENGTH_LONG).show();

                detailHandler.sendEmptyMessage(1);

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ParkingDetailActivity.this,"Loading Error",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
