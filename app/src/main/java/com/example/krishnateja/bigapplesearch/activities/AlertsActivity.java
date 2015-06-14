package com.example.krishnateja.bigapplesearch.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.SubwayAlertsModel;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;
import com.example.krishnateja.bigapplesearch.utils.alertsutils.AlertsRecycleViewItemDecorator;
import com.example.krishnateja.bigapplesearch.utils.alertsutils.AlertsRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 5/25/2015.
 */
public class AlertsActivity extends ActionBarActivity implements CommonAsyncTask.ServerData, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<SubwayAlertsModel> mSubwayAlertsModelArrayList;
    private AlertsRecyclerAdapter mAlertsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        mSubwayAlertsModelArrayList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_alerts_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fillRecyclerView(mSubwayAlertsModelArrayList);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.activity_alerts_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        callAsyncTask();


    }

    private void callAsyncTask() {
        String[] paths = new String[2];
        paths[0] = AppConstants.ServerVariables.PATH;
        paths[1] = AppConstants.ServerVariables.ALERTS_FILE;
        HashMap<String, String> getVariables = new HashMap<>();
        new CommonAsyncTask(this,AppConstants.InAppConstants.ALERTS_CODE).execute(CommonFunctions.buildParams(paths, getVariables, this));
    }

    @Override
    public void getMTAData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {

    }

    @Override
    public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {

    }

    @Override
    public void getResData(ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList) {

    }

    @Override
    public void getAlertsData(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAlertsRecyclerAdapter.changeData(subwayAlertsModelArrayList);

    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    public void fillRecyclerView(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_alerts_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAlertsRecyclerAdapter = new AlertsRecyclerAdapter(subwayAlertsModelArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAlertsRecyclerAdapter);
        recyclerView.addItemDecoration(new AlertsRecycleViewItemDecorator(this, null));

    }
}
