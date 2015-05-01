package com.example.krishnateja.bigapplesearch.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.fragment.MainFragment;
import com.example.krishnateja.bigapplesearch.fragment.MapViewFragment;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RequestParams;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;
import com.example.krishnateja.bigapplesearch.utils.RecentSearchContentProvider;
import com.example.krishnateja.bigapplesearch.fragment.LeftDrawerFragment;
import com.example.krishnateja.bigapplesearch.fragment.RightDrawerFragment;
import com.example.krishnateja.bigapplesearch.utils.rightdrawerutils.RightDrawerRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements RightDrawerRecyclerAdapter.Filters,
        LeftDrawerFragment.MenuSection, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        CommonAsyncTask.ServerData {
    private LeftDrawerFragment mLeftDrawerFragment;
    private RightDrawerFragment mRightDrawerFragment;
    private MainFragment mMainFragment;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Context mApplicationContext;
    private Activity mActivityContext;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int START_FLAG = 1;
    private static final int END_FLAG = 2;
    private GoogleApiClient mGoogleApiClient;
    private int mSelection = 0;
    private int mFlag = 0;
    private static final int DONE = 2;
    private ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    private ArrayList<CitiBikeMainScreenModel> mCitiBikeMainScreenModelArrayList;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApplicationContext = getApplicationContext();
        mActivityContext = MainActivity.this;
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentSearchContentProvider.AUTHORITY, RecentSearchContentProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mLeftDrawerFragment = (LeftDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_left_drawer_fragment);
        mRightDrawerFragment = (RightDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_right_drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mLeftDrawerFragment.getDrawerLayout(drawerLayout);
        mRightDrawerFragment.getDrawerLayout(drawerLayout);
        // mMainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_container_fragment);
        setUp(drawerLayout, mToolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
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

    public void setUp(final DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(mActivityContext, drawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
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
    public void getMenuSelection(int selection) {
        mSelection = selection;
        buildGoogleApiClient();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        mMainFragment.mSwipeRefreshLayout.setRefreshing(true);
        Location location = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        if (mSelection == AppConstants.InAppConstants.NEARBY_LEFT) {

            callMTAAsyncTask(lat, lng);
            callCITIAsyncTask(lat, lng);
        } else if (mSelection == AppConstants.InAppConstants.MTA_LEFT) {
            callMTAAsyncTask(lat, lng, 0);
        } else if (mSelection == AppConstants.InAppConstants.CITI_LEFT) {
            callCITIAsyncTask(lat, lng, 0);
        } else {
            //call restaurants later
        }
        mGoogleApiClient.disconnect();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).
                        addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    public void callMTAAsyncTask(double lat, double lng) {
        RequestParams params = CommonFunctions.MTAParams(lat, lng, mActivityContext);
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.MTA_CODE).execute(params);
    }

    public void callCITIAsyncTask(double lat, double lng) {
        RequestParams params = CommonFunctions.CitiParams(lat, lng, mActivityContext);
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.CITI_CODE).execute(params);

    }

    public void callMTAAsyncTask(double lat, double lng, int offset) {
        RequestParams params = CommonFunctions.MTAParams(lat, lng, offset, mActivityContext);
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.MTA_CODE).execute(params);
    }

    public void callCITIAsyncTask(double lat, double lng, int offset) {
        RequestParams params = CommonFunctions.CitiParams(lat, lng, offset, mActivityContext);
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.CITI_CODE).execute(params);

    }

    @Override
    public void getMTAData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {

        mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;
        mFlag++;
        if (mSelection != AppConstants.InAppConstants.NEARBY_LEFT) {
            mFlag = 0;
            mMainFragment.dataFromMain(mMTAMainScreenModelArrayList, null);
            mRightDrawerFragment.filtersFromLeftSelections(mSelection);
            mMainFragment.mSwipeRefreshLayout.setRefreshing(false);
        } else if (mSelection != AppConstants.InAppConstants.RESTAURANT_CODE && mFlag == DONE) {
            mFlag = 0;
            mMainFragment.dataFromMain(mMTAMainScreenModelArrayList, mCitiBikeMainScreenModelArrayList);
            mMainFragment.mSwipeRefreshLayout.setRefreshing(false);
            mRightDrawerFragment.filtersFromLeftSelections(mSelection);
        }

    }

    @Override
    public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {
        mCitiBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
        mFlag++;
        if (mSelection != AppConstants.InAppConstants.NEARBY_LEFT) {
            mFlag = 0;
            mMainFragment.mSwipeRefreshLayout.setRefreshing(false);
            mMainFragment.dataFromMain(null, mCitiBikeMainScreenModelArrayList);
            mRightDrawerFragment.filtersFromLeftSelections(mSelection);

        } else if (mSelection != AppConstants.InAppConstants.RESTAURANT_CODE && mFlag == DONE) {
            mMainFragment.mSwipeRefreshLayout.setRefreshing(false);
            mFlag = 0;
            mMainFragment.dataFromMain(mMTAMainScreenModelArrayList, mCitiBikeMainScreenModelArrayList);
            mRightDrawerFragment.filtersFromLeftSelections(mSelection);
        }

    }

    @Override
    public void getFilters(HashMap<Integer, Integer> filters) {

        mMainFragment.changeDataSet(filters);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
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
