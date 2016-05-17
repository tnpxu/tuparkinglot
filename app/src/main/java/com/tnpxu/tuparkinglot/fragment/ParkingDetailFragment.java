package com.tnpxu.tuparkinglot.fragment;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tnpxu.tuparkinglot.R;
import com.tnpxu.tuparkinglot.customview.ParkingView;
import com.tnpxu.tuparkinglot.parcelabledata.ParkingDetailWrapParcel;

import org.w3c.dom.Text;

/**
 * Created by tnpxu on 4/26/16 AD.
 */
public class ParkingDetailFragment extends Fragment {

    public ImageView imageViewBookmark;
    public ImageView imageViewPeakingMap;
    public ParkingView parkingView;

    public TextView textViewParkingName;
    public TextView textViewDate;
    public TextView textViewAvailable;
    public TextView textViewTotalCar;

    public ParkingDetailWrapParcel parkingDetailWrapParcel;

    public static ParkingDetailFragment newInstance(Bundle myData) {

        ParkingDetailFragment fragment = new ParkingDetailFragment();
        //args.put.....

        //set to argument
        fragment.setArguments(myData);
        return fragment;

    }

    public ParkingDetailFragment () {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_parking_detail, container, false);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        parkingDetailWrapParcel = getArguments().getParcelable("DetailData");
        getArguments().remove("DetailData");

        imageViewBookmark = (ImageView)getView().findViewById(R.id.bookmark_icon);
        imageViewBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int color = Color.parseColor("#ff0000"); // red color
                imageViewBookmark.setColorFilter(color);

            }
        });

        imageViewPeakingMap = (ImageView) getView().findViewById(R.id.peak_pic_icon);
        imageViewPeakingMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPeakingMapDialog();
            }
        });

        parkingView = (ParkingView)getView().findViewById(R.id.parking_view);
        //rendering parkingslot customview
        float realWidth = Float.parseFloat(parkingDetailWrapParcel.getParkingWidth());
        float realHeight = Float.parseFloat(parkingDetailWrapParcel.getParkingHeight());
        parkingView.setParkingSlot(parkingDetailWrapParcel.getParkingDetailParcel().getSlotStatusList(),realWidth,realHeight);
        parkingView.invalidate();

        //set all detail
        textViewParkingName = (TextView)getView().findViewById(R.id.header_text_parking_name);
        textViewParkingName.setText(parkingDetailWrapParcel.getParkinglotName());

        textViewDate = (TextView)getView().findViewById(R.id.header_text_date);
        textViewDate.setText(parkingDetailWrapParcel.getCaptureDate());

        textViewAvailable = (TextView)getView().findViewById(R.id.text_available);
        int availableCar = Integer.parseInt(parkingDetailWrapParcel.getParkingDetailParcel().getSlotSize()) -
                Integer.parseInt(parkingDetailWrapParcel.getParkingDetailParcel().getCarCount());
        String availableCarString = "Available cars : " + String.valueOf(availableCar);
        textViewAvailable.setText(availableCarString);

        textViewTotalCar = (TextView)getView().findViewById(R.id.text_total_car);
        String totalCarString = "Total cars : " +
                                parkingDetailWrapParcel.getParkingDetailParcel().getCarCount()
                                + "/" +
                                parkingDetailWrapParcel.getParkingDetailParcel().getSlotSize();
        textViewTotalCar.setText(totalCarString);



        if(savedInstanceState != null) {
            // Restore state here

        }

    }

    public void showPeakingMapDialog() {

        // manipulate picURL path
        int beginStringPathURL = parkingDetailWrapParcel.getParkingPicUrl().indexOf("drone1");
        String picURL =  "http://parkingserver.cloudapp.net:3000/public/cropped_images/" + parkingDetailWrapParcel.getParkingPicUrl().substring(beginStringPathURL);
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("PeakingMapDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = PeakingMapDialogFragment.newInstance(picURL);
        newFragment.show(ft, "PeakingMapDialog");

    }
}
