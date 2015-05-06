package com.example.krishnateja.bigapplesearch.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RequestParams;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.CommonAsyncTask;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;
import com.example.krishnateja.bigapplesearch.utils.mainactivityutils.MainRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = MainFragment.class.getSimpleName();
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GoogleApiClient mGoogleApiClient;
    private int mSelection = AppConstants.InAppConstants.NEARBY_LEFT;
    public ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    public ArrayList<CitiBikeMainScreenModel> mCitiBikeMainScreenModelArrayList;
    public ArrayList<RestaurantMainScreenModel> mRestaurantMainScreenModelArrayList;
    private static int DONE = 3;
    private int mFlag = 0;
    private MainRecyclerAdapter mMainRecyclerAdapter;
    private MainFragmentInstance mMainFragmentInstance;
    private HashMap<String, Integer> mSpinnerSelections;
    private ToMapFragment mToMapFragment;


    public interface MainFragmentInstance {
        public void sendInstance(MainFragment mainFragment);

    }

    public interface ToMapFragment{
        public void sendDataToMapFragment(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                                          ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList,
                                          ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainFragmentInstance = (MainFragmentInstance) activity;
        mToMapFragment=(ToMapFragment)activity;
        mMainFragmentInstance.sendInstance(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.fragment_main_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (savedInstanceState != null) {
            mMTAMainScreenModelArrayList = savedInstanceState.getParcelableArrayList(AppConstants.IntentExtras.MTA);
            mCitiBikeMainScreenModelArrayList = savedInstanceState.getParcelableArrayList(AppConstants.IntentExtras.CITI);
            mRestaurantMainScreenModelArrayList = savedInstanceState.getParcelableArrayList(AppConstants.IntentExtras.RES);
            Log.d(TAG, "in saved instance");
            if (mRestaurantMainScreenModelArrayList != null) {
                Log.d(TAG, "mRestaurnat list not null size->" + mRestaurantMainScreenModelArrayList.size());

            }
            mMainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), new ArrayList<MTAMainScreenModel>(),
                    new ArrayList<CitiBikeMainScreenModel>(), new ArrayList<RestaurantMainScreenModel>());

        } else {
            mMainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), mMTAMainScreenModelArrayList,
                    mCitiBikeMainScreenModelArrayList, mRestaurantMainScreenModelArrayList);
        }
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_main_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mMainRecyclerAdapter);
        mSwipeRefreshLayout.setRefreshing(true);

        if (savedInstanceState == null) {
            buildGoogleApiClient();
        } else {
            getSpinnerSelections((HashMap) savedInstanceState.getSerializable(AppConstants.BundleExtras.FILTERS_SELECTED));
        }
        return mView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(AppConstants.IntentExtras.MTA, mMTAMainScreenModelArrayList);
        outState.putParcelableArrayList(AppConstants.IntentExtras.CITI, mCitiBikeMainScreenModelArrayList);
        outState.putParcelableArrayList(AppConstants.IntentExtras.RES, mRestaurantMainScreenModelArrayList);
        outState.putSerializable(AppConstants.BundleExtras.FILTERS_SELECTED, mSpinnerSelections);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        mGoogleApiClient.disconnect();
        if (mSelection == AppConstants.InAppConstants.NEARBY_LEFT) {
            callMTAAsyncTask(lat, lng, -1);
            callCITIAsyncTask(lat, lng, -1);
            callResAsyncTask(lat, lng, -1);
        } else if (mSelection == AppConstants.InAppConstants.MTA_LEFT) {
            callMTAAsyncTask(lat, lng, 0);
        } else if (mSelection == AppConstants.InAppConstants.CITI_LEFT) {
            callCITIAsyncTask(lat, lng, 0);
        } else {
            callResAsyncTask(lat, lng, 0);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API).
                        addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    public void callMTAAsyncTask(double lat, double lng, int offset) {
        RequestParams params = CommonFunctions.MTAParams(lat, lng, offset, getActivity());
        new CommonAsyncTask(getActivity(), AppConstants.InAppConstants.MTA_CODE).execute(params);
    }

    public void callCITIAsyncTask(double lat, double lng, int offset) {
        RequestParams params = CommonFunctions.CitiParams(lat, lng, offset, getActivity());
        new CommonAsyncTask(getActivity(), AppConstants.InAppConstants.CITI_CODE).execute(params);

    }

    public void callResAsyncTask(double lat, double lng, int offset) {
        RequestParams params = CommonFunctions.restaurantParams(lat, lng, offset, getActivity());
        new CommonAsyncTask(getActivity(), AppConstants.InAppConstants.RESTAURANT_CODE).execute(params);
    }


    public void getMTAData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {
        mFlag++;
        mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;
        if (mSelection == AppConstants.InAppConstants.NEARBY_LEFT) {
            fillRecycleAdapter();
        } else {
            fillRecycleAdapter();

        }

    }


    public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {
        mFlag++;
        mCitiBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
        if (mSelection == AppConstants.InAppConstants.NEARBY_LEFT) {
            mSwipeRefreshLayout.setRefreshing(false);
            fillRecycleAdapter();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            fillRecycleAdapter();
        }

    }


    public void getResData(ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList) {
        mFlag++;
        mRestaurantMainScreenModelArrayList = restaurantMainScreenModelArrayList;
        if (mSelection == AppConstants.InAppConstants.NEARBY_LEFT) {
            fillRecycleAdapter();
        } else {
            fillRecycleAdapter();
        }

    }

    public void fillRecycleAdapter() {
        mFlag = 0;
        mSwipeRefreshLayout.setRefreshing(false);
        mMainRecyclerAdapter.dataSetChanged(mMTAMainScreenModelArrayList,
                mCitiBikeMainScreenModelArrayList, mRestaurantMainScreenModelArrayList);
        mToMapFragment.sendDataToMapFragment(mMTAMainScreenModelArrayList,
                mCitiBikeMainScreenModelArrayList, mRestaurantMainScreenModelArrayList);
    }

    public void getMenuSelection(int selection) {
        Log.d(TAG, "it gets triggered getMenuSelection");
        mSelection = selection;
        mMTAMainScreenModelArrayList = null;
        mCitiBikeMainScreenModelArrayList = null;
        mRestaurantMainScreenModelArrayList = null;
        mSwipeRefreshLayout.setRefreshing(true);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        } else {
            buildGoogleApiClient();
        }

    }

    public void getSpinnerSelections(HashMap<String, Integer> hashMap) {
        if (mSpinnerSelections != null) {
            mSpinnerSelections = null;
        }
        mSpinnerSelections = new HashMap<>();
        mSpinnerSelections.putAll(hashMap);
        ArrayList<MTAMainScreenModel> localMtaMainScreenModelArrayList = new ArrayList<>();
        if (mMTAMainScreenModelArrayList != null) {
            localMtaMainScreenModelArrayList.addAll(mMTAMainScreenModelArrayList);
        }

        ArrayList<CitiBikeMainScreenModel> localCitiBikeMainScreenModelArrayList = new ArrayList<>();
        if (mCitiBikeMainScreenModelArrayList != null) {
            localCitiBikeMainScreenModelArrayList.addAll(mCitiBikeMainScreenModelArrayList);
        }
        ArrayList<RestaurantMainScreenModel> localRestaurantMainScreenModelArrayList = new ArrayList<>();
        if (mRestaurantMainScreenModelArrayList != null) {
            localRestaurantMainScreenModelArrayList.addAll(mRestaurantMainScreenModelArrayList);
        }
        if (hashMap != null) {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                if (entry.getKey().equals(AppConstants.InAppConstants.SHOW_TEXT)) {
                    if (entry.getValue() == AppConstants.InAppConstants.SHOW_CITI) {
                        localMtaMainScreenModelArrayList.clear();
                        localRestaurantMainScreenModelArrayList.clear();
                    } else if (entry.getValue() == AppConstants.InAppConstants.SHOW_MTA) {
                        localCitiBikeMainScreenModelArrayList.clear();
                        localRestaurantMainScreenModelArrayList.clear();
                    } else if (entry.getValue() == AppConstants.InAppConstants.SHOW_RES) {
                        localCitiBikeMainScreenModelArrayList.clear();
                        localMtaMainScreenModelArrayList.clear();
                    }
                }
                if (entry.getKey().equals(AppConstants.InAppConstants.DISTANCE_TEXT)) {
                    if (entry.getValue() == AppConstants.InAppConstants.DISTANCE_DECREASING) {
                        Collections.reverse(localMtaMainScreenModelArrayList);
                        Collections.reverse(localCitiBikeMainScreenModelArrayList);
                        Collections.reverse(localRestaurantMainScreenModelArrayList);
                    }
                }
            }
        }
        mMainRecyclerAdapter.dataSetChanged(localMtaMainScreenModelArrayList, localCitiBikeMainScreenModelArrayList, localRestaurantMainScreenModelArrayList);
        mToMapFragment.sendDataToMapFragment(localMtaMainScreenModelArrayList, localCitiBikeMainScreenModelArrayList, localRestaurantMainScreenModelArrayList);
    }


}
