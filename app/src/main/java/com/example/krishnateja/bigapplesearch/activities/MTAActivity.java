package com.example.krishnateja.bigapplesearch.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.fragment.MapViewFragment;
import com.example.krishnateja.bigapplesearch.fragment.RightMTADrawerFragment;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.SubwayAlertsModel;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;
import com.example.krishnateja.bigapplesearch.utils.mtaactivityutils.MTAActivityRecyclerAdapter;
import com.example.krishnateja.bigapplesearch.utils.mtaactivityutils.MTAActivityRecyclerViewItemDecorator;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MTAActivity extends ActionBarActivity implements CommonAsyncTask.ServerData, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mCountServerCalls = 0;
    private int mTotalCount;
    private ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    private MTAActivityRecyclerAdapter mMTAActivityRecyclerAdapter;
    double mLat, mLng;
    String mStopName;
    private String TAG = MTAActivity.class.getSimpleName();
    private RightMTADrawerFragment mRightMTADrawerFragment;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mta);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mta_drawer_layout);
        mRightMTADrawerFragment = (RightMTADrawerFragment) getSupportFragmentManager().findFragmentById(R.id.mta_right_drawer_fragment);
        if (savedInstanceState == null) {
            mMTAMainScreenModelArrayList = new ArrayList<>();
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_mta_refresh);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            Intent intent = getIntent();
            MTAMainScreenModel mtaMainScreenModel = intent.getParcelableExtra(AppConstants.IntentExtras.MTA);
            mLat = mtaMainScreenModel.getStopLatitude();
            mLng = mtaMainScreenModel.getStopLongitude();
            mStopName = mtaMainScreenModel.getStopName();
            fillRecyclerView(mMTAMainScreenModelArrayList);
            getDataFromServer(mtaMainScreenModel);
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
            getLineAlert(mtaMainScreenModel.getOneRouteShortName());
        } else {
            mMTAMainScreenModelArrayList = savedInstanceState.getParcelableArrayList(AppConstants.IntentExtras.MTA);
            fillRecyclerView(mMTAMainScreenModelArrayList);
            mLat = savedInstanceState.getDouble("lat");
            mLng = savedInstanceState.getDouble("lng");
            mStopName = savedInstanceState.getString("stopName");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.mta_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setUp(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.activity_mta_map);
        GoogleMap map = mapFragment.getMap();
        map.addMarker(new MarkerOptions()
                .position(
                        new LatLng(mLat,
                                mLng))
                .title(mStopName)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLng), 16));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "here");
            //NavUtils.navigateUpFromSameTask(this);
        }else if(item.getItemId()==R.id.alerts){
            if(mDrawerLayout.isDrawerOpen(GravityCompat.END)){
                mDrawerLayout.closeDrawer(GravityCompat.END);
            }else{
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDataFromServer(MTAMainScreenModel mtaMainScreenModel) {
        ArrayList<String> routes = mtaMainScreenModel.getRouteId();
        ArrayList<String> stopIds = mtaMainScreenModel.getStopId();
        mTotalCount = routes.size() * stopIds.size();
        for (String route : routes) {
            for (String stopId : stopIds) {
                mCountServerCalls++;
                String[] paths = new String[2];
                paths[0] = "NYCSubwayAndBike";
                paths[1] = "bigapple.php";
                HashMap<String, String> getVariables = new HashMap<>();
                getVariables.put("stop_id", "\"" + stopId + "\"");
                getVariables.put("route_id", "\"" + route + "\"");
                new CommonAsyncTask(this, AppConstants.InAppConstants.MTA_ACTIVITY_CODE).execute(CommonFunctions.buildParams(paths, getVariables, this));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(AppConstants.IntentExtras.MTA, mMTAMainScreenModelArrayList);
        outState.putDouble("lat", mLat);
        outState.putDouble("lng", mLng);
        outState.putString("stopName", mStopName);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void getMTAData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {
        Toast.makeText(this, "here in mta activity", Toast.LENGTH_SHORT).show();
        mMTAMainScreenModelArrayList.addAll(mtaMainScreenModelArrayList);
        if (mCountServerCalls == mTotalCount) {
            mSwipeRefreshLayout.setRefreshing(false);
            mMTAActivityRecyclerAdapter.changeData(mMTAMainScreenModelArrayList);

        }

    }

    @Override
    public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {

    }

    @Override
    public void getResData(ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList) {

    }

    @Override
    public void getAlertsData(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList) {
        mRightMTADrawerFragment.sendAlertsToRecyclerView(subwayAlertsModelArrayList);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    public void fillRecyclerView(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_mta_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mMTAActivityRecyclerAdapter = new MTAActivityRecyclerAdapter(mtaMainScreenModelArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMTAActivityRecyclerAdapter);
        recyclerView.addItemDecoration(new MTAActivityRecyclerViewItemDecorator(this, null));

    }

    public void getLineAlert(String routeName) {
        String[] paths = new String[2];
        paths[0] = AppConstants.ServerVariables.PATH;
        paths[1] = AppConstants.ServerVariables.ALERTS_FILE;
        HashMap<String, String> getVariables = new HashMap<>();
        getVariables.put("line", routeName);
        new CommonAsyncTask(this, AppConstants.InAppConstants.ALERTS_CODE).execute(CommonFunctions.buildParams(paths, getVariables, this));
    }

//    public void setUp(Toolbar toolbar) {
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MTAActivity.this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//
//            }
//        };
//
//
//        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
//    }
}
