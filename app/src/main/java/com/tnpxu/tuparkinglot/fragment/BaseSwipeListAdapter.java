package com.tnpxu.tuparkinglot.fragment;

/**
 * Created by tnpxu on 4/21/16 AD.
 */
import android.widget.BaseAdapter;

/**
 * Created by Abner on 15/11/20.
 * Email nimengbo@gmail.com
 * github https://github.com/nimengbo
 */
abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position){
        return true;
    }


}
