package com.example.krishnateja.bigapplesearch.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.fragment.MapViewFragment;
import com.example.krishnateja.bigapplesearch.fragment.SearchFragment;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.tabs.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/7/2015.
 */
public class SearchActivity extends ActionBarActivity implements SearchFragment.SearchFragmentInstance, MapViewFragment.MapViewFragmentInstance {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private SearchFragment mSearchFragment;
    private MapViewFragment mMapViewFragment;
    public ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    public ArrayList<CitiBikeMainScreenModel> mCitiBikeMainScreenModelArrayList;
    public ArrayList<RestaurantMainScreenModel> mRestaurantMainScreenModelArrayList;
    public String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.activity_search_sliding_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            mRestaurantMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.RES);
            mMTAMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.MTA);
            mCitiBikeMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.CITI);


        }
    }

    @Override
    public void getMapViewFragmentInstance(MapViewFragment mapViewFragment) {
        Log.d(TAG, "here in getMapView framgent");
        mMapViewFragment = mapViewFragment;
        searchTroughArrayLists(mQuery);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
               searchTroughArrayLists(intent.getStringExtra(SearchManager.QUERY));
        }

    }

    @Override
    public void getInstance(SearchFragment searchFragment) {
        mSearchFragment = searchFragment;
        Log.d(TAG,"here in searchFramante intstace");
        if(mSearchFragment==null){
            Log.d(TAG,"mSearchFragment is nullin getInstace");
        }else{
            Log.d(TAG,"mSearxjFragmnt is not nullin getIntance");
            searchTroughArrayLists(mQuery);
        }

    }

    public void searchTroughArrayLists(String query) {
        ArrayList<MTAMainScreenModel> localMTAMainScreenModelArrayList = new ArrayList<>();
        ArrayList<CitiBikeMainScreenModel> localCitiScreenModelArrayList = new ArrayList<>();
        ArrayList<RestaurantMainScreenModel> localRestaurantMainScreenModelArrayList = new ArrayList<>();
        if (mMTAMainScreenModelArrayList != null) {
            for (MTAMainScreenModel mtaMainScreenModel : mMTAMainScreenModelArrayList) {
                if (mtaMainScreenModel.getStopName().toLowerCase().contains(query.toLowerCase())) {
                    localMTAMainScreenModelArrayList.add(mtaMainScreenModel);
                } else if (AppConstants.InAppConstants.MTAURL.toLowerCase().contains(query.toLowerCase())) {
                    localMTAMainScreenModelArrayList.add(mtaMainScreenModel);
                } else {
                    ArrayList<String> lineNames = mtaMainScreenModel.getRouteId();
                    for (String route : lineNames) {
                        if (route.toLowerCase().contains(query.toLowerCase())) {
                            localMTAMainScreenModelArrayList.add(mtaMainScreenModel);
                            break;
                        }
                    }
                }
            }
        }
        if (mCitiBikeMainScreenModelArrayList != null) {
            for (CitiBikeMainScreenModel citiBikeMainScreenModel : mCitiBikeMainScreenModelArrayList) {
                if (citiBikeMainScreenModel.getStAddress1().toLowerCase().contains(query.toLowerCase())) {
                    localCitiScreenModelArrayList.add(citiBikeMainScreenModel);
                } else if (citiBikeMainScreenModel.getStationName().toLowerCase().contains(query.toLowerCase())) {
                    localCitiScreenModelArrayList.add(citiBikeMainScreenModel);
                } else if (AppConstants.InAppConstants.CITIURL.toLowerCase().contains(query.toLowerCase())) {
                    localCitiScreenModelArrayList.add(citiBikeMainScreenModel);
                }
            }
        }

        if (mRestaurantMainScreenModelArrayList != null) {
            for (RestaurantMainScreenModel restaurantMainScreenModel : mRestaurantMainScreenModelArrayList) {
                if (restaurantMainScreenModel.getAddress().toLowerCase().contains(query.toLowerCase())) {
                    localRestaurantMainScreenModelArrayList.add(restaurantMainScreenModel);
                } else if (restaurantMainScreenModel.getResName().toLowerCase().contains(query.toLowerCase())) {
                    localRestaurantMainScreenModelArrayList.add(restaurantMainScreenModel);
                } else if (restaurantMainScreenModel.getUrl().toLowerCase().contains(query.toLowerCase())) {
                    localRestaurantMainScreenModelArrayList.add(restaurantMainScreenModel);
                } else {
                    ArrayList<String> cats = restaurantMainScreenModel.getCategories();
                    for (String cat : cats) {
                        if (cat.toLowerCase().contains(query.toLowerCase())) {
                            localRestaurantMainScreenModelArrayList.add(restaurantMainScreenModel);
                        }
                    }
                }
            }
        }
        if (localCitiScreenModelArrayList.size() + localMTAMainScreenModelArrayList.size()
                + localRestaurantMainScreenModelArrayList.size() == 0) {
            Toast.makeText(SearchActivity.this, "sorry no results for the search term", Toast.LENGTH_LONG).show();

        }
        if(mSearchFragment!=null) {
            mSearchFragment.getResultsFromSearch(localMTAMainScreenModelArrayList,
                    localCitiScreenModelArrayList, localRestaurantMainScreenModelArrayList);
        }else{
            Log.d(TAG,"mSearchFragment is null");
        }
        if(mMapViewFragment!=null) {
            mMapViewFragment.getDataFromMain(localMTAMainScreenModelArrayList,
                    localCitiScreenModelArrayList,
                    localRestaurantMainScreenModelArrayList);
            Log.d(TAG, "mMapViewFragment is null");
        }


    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            Log.d(TAG, "in screenslide");
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Log.d(TAG, "in returning MainFragment");
                mSearchFragment = new SearchFragment();
                return mSearchFragment;
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
