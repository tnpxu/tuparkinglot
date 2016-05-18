package com.tnpxu.tuparkinglot;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.tnpxu.tuparkinglot.api.CallingLocationData;
import com.tnpxu.tuparkinglot.api.ServiceGenerator;
import com.tnpxu.tuparkinglot.api.requestdata.MapDataReq;
import com.tnpxu.tuparkinglot.api.requestdata.MapDetailReq;
import com.tnpxu.tuparkinglot.api.responsedata.AllData;
import com.tnpxu.tuparkinglot.api.responsedata.AllDataParkingDetail;
import com.tnpxu.tuparkinglot.api.responsedata.AllParkingDetailRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataRes;
import com.tnpxu.tuparkinglot.api.responsedata.MapDataResWrap;
import com.tnpxu.tuparkinglot.fragment.BookmarkFragment;
import com.tnpxu.tuparkinglot.fragment.InformationFragment;
import com.tnpxu.tuparkinglot.fragment.ParkingMapFragment;
import com.tnpxu.tuparkinglot.parcelabledata.AllDataInParcel;
import com.tnpxu.tuparkinglot.parcelabledata.MapDataParcel;
import com.tnpxu.tuparkinglot.parcelabledata.MapDataResParcel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private ProgressBar progress;

    private MapDataResParcel parcel;
    private MapDataRes collectMapDataRes;
    private AllParkingDetailRes collectAllDataParkingDetailRes;

    public enum FragmentTag {
        PARKINGMAP,
        BOOKMARK,
        INFORMATION
    }

    // handle which fragment will inflate
    public Handler mainHandler = new Handler (new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            //download progress done!!
            hideProgress();

            switch(msg.what) {
                case 1: showFragment(FragmentTag.PARKINGMAP);
                    break;
                case 2: showFragment(FragmentTag.BOOKMARK);
                    break;
                case 3: showFragment(FragmentTag.INFORMATION);
                    break;
                default:
                    break;

            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        //get api before initiate fragment
        getApi(FragmentTag.PARKINGMAP);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nv_view);
        //Don't Tint My icon u jerk!!!!
        nvDrawer.setItemIconTintList(null);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // init progress bar
        progress = (ProgressBar) findViewById(R.id.progress_download);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    // A.K.A menus on the left selected event handle
    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    // menus on the left selected!!
    private void selectDrawerItem(MenuItem menuItem) {

        //navigation drawer menus selected, always get api before inflate fragment
        switch (menuItem.getItemId()) {

            case R.id.nav_first_fragment:
                //kill Mapfragment for avoid duplicate fragment
                killMapFragment();
                getApi(FragmentTag.PARKINGMAP);
                break;
            case R.id.nav_second_fragment:
                getApi(FragmentTag.BOOKMARK);
                break;
            case R.id.nav_third_fragment:
                getApi(FragmentTag.INFORMATION);
                break;
            default:
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    // map fragment is nested on fragment kill it to be sure
    private void killMapFragment() {

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            getFragmentManager().beginTransaction().remove(mapFragment).commit();
    }

    private void hideProgress() {

        progress.setVisibility(ProgressBar.INVISIBLE);
        progress.getLayoutParams().height = 0;
        progress.getLayoutParams().width = 0;
    }

    private void showProgress() {
        progress.setVisibility(ProgressBar.VISIBLE);
        progress.getLayoutParams().height = progress.getLayoutParams().MATCH_PARENT;
        progress.getLayoutParams().width = progress.getLayoutParams().WRAP_CONTENT;
    }

    private void getApi(final FragmentTag fragmentTag) {

        //show download progress
        showProgress();
        //request body
        MapDataReq dataReq = new MapDataReq();
        final CallingLocationData service = ServiceGenerator.createService(CallingLocationData.class);
        Call<MapDataResWrap> call = service.getDataMap(dataReq);
        call.enqueue(new Callback<MapDataResWrap>() {
            @Override
            public void onResponse(Response<MapDataResWrap> response, Retrofit retrofit) {

                //store return data to parcel
                parcel = new MapDataResParcel();
                parcel.setAllData(response.body().getMapDataRes().getAllData());

                collectMapDataRes = new MapDataRes();
                collectMapDataRes.setAllData(response.body().getMapDataRes().getAllData());

                //receive status each location
                getApiAllDetail(fragmentTag);

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this,"Loading Error",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getApiAllDetail(final FragmentTag fragmentTag) {

        //request body
        MapDetailReq mapDetailReq = new MapDetailReq();
        mapDetailReq.setToken("helloworld");
        final CallingLocationData service = ServiceGenerator.createService(CallingLocationData.class);
        Call<List<AllDataParkingDetail>> call = service.getAllParkingDetail(mapDetailReq);
        call.enqueue(new Callback<List<AllDataParkingDetail>>() {
            @Override
            public void onResponse(Response<List<AllDataParkingDetail>> response, Retrofit retrofit) {

                collectAllDataParkingDetailRes = new AllParkingDetailRes();
                collectAllDataParkingDetailRes.setAllDataParkingDetails(response.body());


                //notify handler to show fragment
                switch (fragmentTag) {
                    case PARKINGMAP: mainHandler.sendEmptyMessage(1);
                        break;
                    case BOOKMARK: mainHandler.sendEmptyMessage(2);
                        break;
                    case INFORMATION: mainHandler.sendEmptyMessage(3);
                        break;
                    default: mainHandler.sendEmptyMessage(1);
                        break;
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this,"Loading Error",Toast.LENGTH_SHORT).show();
            }
        });

    }

    //fragment transaction here
    private void showFragment(FragmentTag fragmentTag) {

        Bundle myData = packingParcelBundle("MapData",collectMapDataRes,collectAllDataParkingDetailRes);
        // Create a new fragment and specify the fragment to show
        Fragment fragment = null;
        try {
            switch(fragmentTag) {
                case PARKINGMAP: fragment = ParkingMapFragment.newInstance(myData);
                    break;
                case BOOKMARK: fragment = BookmarkFragment.newInstance(myData);
                    break;
                case INFORMATION: fragment = InformationFragment.newInstance(myData);
                    break;
                default: fragment = ParkingMapFragment.newInstance(myData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main_content, fragment).commit();

    }

    //packing AllMapData and Each location
    private Bundle packingParcelBundle(String tagName, MapDataRes collectionMapData , AllParkingDetailRes collectionDataParking) {

        Bundle packingBundle = new Bundle();
        MapDataParcel mapDataParcel = new MapDataParcel();
        List<AllDataInParcel> allDataInParcelList = new ArrayList<>();

        List<AllData> allData = collectionMapData.getAllData();
        List<AllDataParkingDetail> allParkingDetailRes = collectionDataParking.getAllDataParkingDetails();

        //Packing
        for(AllDataParkingDetail aElement : allParkingDetailRes) {
            for(AllData bElement : allData) {
                //match token
                if(aElement.getToken().equals(bElement.getToken())) {
                    // add item to parcel
                    AllDataInParcel allDataInParcel = new AllDataInParcel();

                    allDataInParcel.setName(bElement.getName());
                    allDataInParcel.setLatitude(bElement.getLatitude());
                    allDataInParcel.setLongtitude(bElement.getLongtitude());
                    allDataInParcel.setToken(bElement.getToken());

                    allDataInParcel.setCaptureDate(aElement.getCaptureDate());
                    allDataInParcel.setParkingStatus(aElement.getParkingStatus());
                    allDataInParcel.setSlotSize(aElement.getSlotSize());
                    allDataInParcel.setCarCount(aElement.getCarCount());

                    allDataInParcelList.add(allDataInParcel);
                }
            }
        }

        mapDataParcel.setAllDataInParcels(allDataInParcelList);
        packingBundle.putParcelable(tagName,mapDataParcel);

        return packingBundle;
    }

}
