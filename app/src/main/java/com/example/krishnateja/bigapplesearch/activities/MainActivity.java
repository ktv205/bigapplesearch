package com.example.krishnateja.bigapplesearch.activities;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.fragment.MainFragment;
import com.example.krishnateja.bigapplesearch.fragment.MapViewFragment;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.fragment.LeftDrawerFragment;
import com.example.krishnateja.bigapplesearch.fragment.RightDrawerFragment;
import com.example.krishnateja.bigapplesearch.utils.rightdrawerutils.RightDrawerRecyclerAdapter;
import com.example.krishnateja.bigapplesearch.utils.tabs.SlidingTabLayout;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements
        LeftDrawerFragment.MenuSection, CommonAsyncTask.ServerData, MainFragment.MainFragmentInstance,
        RightDrawerRecyclerAdapter.SpinnerSelectionInterface,
        MapViewFragment.MapViewFragmentInstance, MainFragment.ToMapFragment {
    private LeftDrawerFragment mLeftDrawerFragment;
    private RightDrawerFragment mRightDrawerFragment;
    private static final int START_FLAG = 1;
    private static final int END_FLAG = 2;
    private DrawerLayout mDrawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    public MainFragment mMainFragment;
    public MapViewFragment mMapViewFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mLeftDrawerFragment = (LeftDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_left_drawer_fragment);
        mRightDrawerFragment = (RightDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_right_drawer_fragment);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mLeftDrawerFragment.getDrawerLayout(mDrawerLayout);
        mRightDrawerFragment.getDrawerLayout(mDrawerLayout);
        setUp(toolbar);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.activity_main_sliding_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.search).getActionView();
        ComponentName cn = new ComponentName(this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        searchView.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void startActivity(Intent intent) {
        Log.d(TAG, "start activity");
        //Log.d(TAG,"size of MTA data->"+mMainFragment.mMTAMainScreenModelArrayList.size()+"<-size()");
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.MTA, mMainFragment.mMTAMainScreenModelArrayList);
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.CITI, mMainFragment.mCitiBikeMainScreenModelArrayList);
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.RES,mMainFragment.mRestaurantMainScreenModelArrayList);
        super.startActivity(intent);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            openOrClose(START_FLAG);
        } else if (id == R.id.action_right_nav) {
            openOrClose(END_FLAG);
        }
        return true;
    }

    @Override
    public void getMenuSelection(int selection) {
        Log.d(TAG, "getMenuSelection->" + selection);
        if (mMainFragment != null) {
            mMainFragment.getMenuSelection(selection);
        }
        mRightDrawerFragment.filtersFromLeftSelections(selection);

    }


    public void setUp(Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };


        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void openOrClose(int flag) {
        if (flag == START_FLAG) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        } else {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        }
    }

    @Override
    public void getMTAData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {
        mMainFragment.getMTAData(mtaMainScreenModelArrayList);
    }

    @Override
    public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {
        mMainFragment.getCITIData(citiBikeMainScreenModelArrayList);
    }

    @Override
    public void getResData(ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList) {
        mMainFragment.getResData(restaurantMainScreenModelArrayList);
    }

    @Override
    public void sendInstance(MainFragment mainFragment) {
        mMainFragment = mainFragment;
    }

    @Override
    public void getSpinnerSelection(HashMap<String, Integer> spinnerSelection) {
        mMainFragment.getSpinnerSelections(spinnerSelection);

    }

    @Override
    public void getMapViewFragmentInstance(MapViewFragment mapViewFragment) {
        Log.d(TAG, "getting mapviewfragment");
        mMapViewFragment = mapViewFragment;

    }

    @Override
    public void sendDataToMapFragment(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList, ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList, ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList) {
        if (mMapViewFragment != null) {
            mMapViewFragment.getDataFromMain(mtaMainScreenModelArrayList,
                    citiBikeMainScreenModelArrayList, restaurantMainScreenModelArrayList);
        } else {
            Log.d(TAG, "mMapViewFragment is null");
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            Log.d(TAG, "in screenslide");
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Log.d(TAG, "in returning MainFragment");
                mMainFragment = new MainFragment();
                return mMainFragment;
            } else {
                return new MapViewFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "List";
            } else {
                return "Map";
            }
        }
    }


}
