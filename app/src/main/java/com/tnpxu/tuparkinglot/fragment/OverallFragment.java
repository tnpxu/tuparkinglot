package com.tnpxu.tuparkinglot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;
import com.tnpxu.tuparkinglot.R;

/**
 * Created by tnpxu on 3/29/16 AD.
 */
public class OverallFragment extends Fragment {

    public static OverallFragment newInstance() {

        OverallFragment fragment = new OverallFragment();
        Bundle args = new Bundle();
        //args.put.....

        //set to argument
        fragment.setArguments(args);
        return fragment;
    }

    public OverallFragment () {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_overall, container, false);


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if(savedInstanceState != null) {
            // Restore state here

        }

    }
}
