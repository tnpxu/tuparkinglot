package com.tnpxu.tuparkinglot.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tnpxu.tuparkinglot.R;

/**
 * Created by tnpxu on 5/17/16 AD.
 */
public class PeakingMapDialogFragment extends DialogFragment {

    public ImageView imageViewGetMap;
    public String picURL;

    public PeakingMapDialogFragment() {

    }

    public static PeakingMapDialogFragment newInstance(String url) {

        PeakingMapDialogFragment frag = new PeakingMapDialogFragment();
        Bundle args = new Bundle();
        args.putString("picurl",url);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        picURL = getArguments().getString("picurl");
        getArguments().remove("picurl");

        return inflater.inflate(R.layout.dialog_fragment_peakingmap, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewGetMap = (ImageView) view.findViewById(R.id.image_map_view);

        Glide.with(getActivity()).load(picURL).into(imageViewGetMap);

//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


}
