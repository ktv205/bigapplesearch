package com.example.krishnateja.bigapplesearch.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/29/2015.
 */

public class HelperActivity extends ActionBarActivity implements CommonAsyncTask.ServerData,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static final String TAG = HelperActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private Context mApplicationContext;
    private Activity mActivityContext;
    private int mFlag = 0;
    private static final String MTA = "mta";
    private static final String CITI = "citiBikes";
    private static final String RESTAURANTS = "restaurants";
    private static final int DONE = 2;
    private ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    private ArrayList<CitiBikeMainScreenModel> mCITIBikeMainScreenModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        mApplicationContext = getApplicationContext();
        mActivityContext = HelperActivity.this;
        startGoogleApiClient();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");
        Location location = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        callMTAAsyncTask(lat, lng);
        callCITIAsyncTask(lat, lng);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "connectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "connectionFailed");

    }


    public void startGoogleApiClient() {
        Log.d(TAG, "startGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).
                        addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void getMTAData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {
        mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;
        mFlag++;
        if (mFlag == DONE) {
            sendUserToMainScreen();
        }
    }

    @Override
    public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {
        mCITIBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
        Log.d(TAG, mCITIBikeMainScreenModelArrayList.size() + " size of citi");
        mFlag++;
        if (mFlag == DONE) {
            sendUserToMainScreen();
        }

    }

    public void callMTAAsyncTask(double lat, double lng) {
        HashMap<String, String> getVariables = new HashMap<>();
        getVariables.put(AppConstants.ServerVariables.GETMTAVARIABLE,
                AppConstants.ServerVariables.GETMTAVARIABLE);
        getVariables.put(AppConstants.ServerVariables.LAT,
                String.valueOf(lat));
        getVariables.put(AppConstants.ServerVariables.LNG,
                String.valueOf(lng));
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.MTA_CODE).execute(
                CommonFunctions.buildParams(new String[]{AppConstants.ServerVariables.PATH,
                        AppConstants.ServerVariables.FILE}, getVariables, mActivityContext));
    }

    public void callCITIAsyncTask(double lat, double lng) {
        HashMap<String, String> getVariables = new HashMap<>();
        getVariables.put(AppConstants.ServerVariables.GETCITIVARIABLE,
                AppConstants.ServerVariables.GETCITIVARIABLE);
        getVariables.put(AppConstants.ServerVariables.LAT,
                String.valueOf(lat));
        getVariables.put(AppConstants.ServerVariables.LNG,
                String.valueOf(lng));
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.CITI_CODE).execute(
                CommonFunctions.buildParams(new String[]{AppConstants.ServerVariables.PATH,
                        AppConstants.ServerVariables.FILE}, getVariables, mActivityContext));

    }

    public void sendUserToMainScreen() {
        Log.d(TAG, "sendUserToMainScreen");
        Intent intent = new Intent(mActivityContext, MainActivity.class);
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.MTA, mMTAMainScreenModelArrayList);
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.CITI, mCITIBikeMainScreenModelArrayList);
        startActivity(intent);

    }


}
