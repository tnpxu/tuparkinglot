package com.tnpxu.tuparkinglot.fragment;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tnpxu.tuparkinglot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tnpxu on 4/20/16 AD.
 */
public class BookmarkFragment extends Fragment {

    private List<BookmarkObject> mBookmarkList;
    private AppAdapter mAdapter;
    private SwipeMenuListView mListView;

    public static BookmarkFragment newInstance(Bundle myData) {

        BookmarkFragment fragment = new BookmarkFragment();

        Bundle args = new Bundle();
        //args.put.....

        //set to argument
        fragment.setArguments(myData);
        return fragment;

    }

    public BookmarkFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            // Restore state here

        }

        initListSwipe();

    }

    //initiate listview
    public void initListSwipe() {

        mBookmarkList = new ArrayList<>();
        BookmarkObject testItem1 = new BookmarkObject("RedFlag", "Test1 Parking");
        BookmarkObject testItem2 = new BookmarkObject("YellowFlag", "Test2 Parking");
        BookmarkObject testItem3 = new BookmarkObject("GreenFlag", "Tes3t Parking");
        mBookmarkList.add(testItem1);
        mBookmarkList.add(testItem2);
        mBookmarkList.add(testItem3);

        mListView = (SwipeMenuListView) getActivity().findViewById(R.id.listView);

        mAdapter = new AppAdapter();
        mListView.setAdapter(mAdapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getActivity());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(68));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_removeitem);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                BookmarkObject item = mBookmarkList.get(position);
                switch (index) {
                    case 0:
                        // delete
                        delete(item);
                        mBookmarkList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;


                }
                return false;
            }
        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getActivity(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }

        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                BookmarkObject getPostionClickObj = mBookmarkList.get(position);

                Toast.makeText(getActivity(), getPostionClickObj.getParkingName() + " long click", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_icon_bookmark, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_left) {
//            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
//            return true;
//        }
//        if (id == R.id.action_right) {
//            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void delete(BookmarkObject item) {

    }

    private void open(BookmarkObject item) {

    }

    class AppAdapter extends BaseSwipListAdapter {

        @Override
        public int getCount() {
            return mBookmarkList.size();
        }

        @Override
        public BookmarkObject getItem(int position) {
            return mBookmarkList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(),
                        R.layout.item_bookmark, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            BookmarkObject item = getItem(position);

            //find what icon and text in bookmarklist

            switch (item.getIconFlag()) {
                case "RedFlag":
                    holder.iv_icon.setImageResource(R.mipmap.ic_flagred);
                    break;
                case "YellowFlag":
                    holder.iv_icon.setImageResource(R.mipmap.ic_flagyellow);
                    break;
                case "GreenFlag":
                    holder.iv_icon.setImageResource(R.mipmap.ic_flaggreen);
                    break;
                default:
                    holder.iv_icon.setImageResource(R.mipmap.ic_flagred);
                    break;
            }

            holder.tv_name.setText(item.getParkingName());
//            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "icon click", Toast.LENGTH_SHORT).show();
//                }
//            });
//            holder.tv_name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "name click", Toast.LENGTH_SHORT).show();
//                }
//            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.bookmarkIcon);
                tv_name = (TextView) view.findViewById(R.id.bookmarkText);
                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipEnableByPosition(int position) {
            if (position % 2 == 0) {
                return false;
            }
            return true;
        }
    }

}
