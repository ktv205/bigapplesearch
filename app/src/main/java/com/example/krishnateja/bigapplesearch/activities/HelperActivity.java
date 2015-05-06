package com.example.krishnateja.bigapplesearch.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RequestParams;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

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
    private static final int DONE = 3;
    private ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    private ArrayList<CitiBikeMainScreenModel> mCITIBikeMainScreenModelArrayList;
    private ArrayList<RestaurantMainScreenModel> mRestaurantMainScreenModelArrayList;

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
        Location location = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        callMTAAsyncTask(lat, lng);
        callCITIAsyncTask(lat, lng);
        callResAsyncTask(lat, lng);
    }

    private void callResAsyncTask(double lat, double lng) {
       RequestParams params= CommonFunctions.restaurantParams(lat, lng, -1, mActivityContext);
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.RESTAURANT_CODE).execute(params);

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
            mFlag=0;
        }
    }

    @Override
    public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {
        mCITIBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
        mFlag++;
        if (mFlag == DONE) {
            sendUserToMainScreen();
            mFlag=0;
        }

    }

    @Override
    public void getResData(ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList) {
         mRestaurantMainScreenModelArrayList=restaurantMainScreenModelArrayList;
        if(mRestaurantMainScreenModelArrayList!=null){
           // Log.d(TAG,"mRestaurantMainScreenModelArrayList->"+mRestaurantMainScreenModelArrayList.size());
        }else{
           // Log.d(TAG,"mRestaurantMainScreenModelArrayList is null");
        }
        mFlag++;
        if(mFlag==DONE){
            sendUserToMainScreen();
            mFlag=0;
        }

    }

    public void callMTAAsyncTask(double lat, double lng) {
        RequestParams params=CommonFunctions.MTAParams(lat,lng,mActivityContext);
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.MTA_CODE).execute(params);
    }

    public void callCITIAsyncTask(double lat, double lng) {
        RequestParams params=CommonFunctions.CitiParams(lat,lng,mActivityContext);
        new CommonAsyncTask(mActivityContext, AppConstants.InAppConstants.CITI_CODE).execute(params);

    }

    public void sendUserToMainScreen() {
        Intent intent = new Intent(mActivityContext, MainActivity.class);
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.MTA, mMTAMainScreenModelArrayList);
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.CITI, mCITIBikeMainScreenModelArrayList);
        intent.putParcelableArrayListExtra(AppConstants.IntentExtras.RES,mRestaurantMainScreenModelArrayList);
        startActivity(intent);
        finish();

    }


}
