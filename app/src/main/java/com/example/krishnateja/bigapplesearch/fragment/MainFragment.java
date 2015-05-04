package com.example.krishnateja.bigapplesearch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.mainactivityutils.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainFragment.class.getSimpleName();
    private View mView;
    RecyclerView mRecyclerView;
    ArrayList<MTAMainScreenModel> mMtaMainScreenModelArrayList = null;
    ArrayList<CitiBikeMainScreenModel> mCitiBikeMainScreenModelArrayList = null;
    private ArrayList<RestaurantMainScreenModel> mRestaurantMainScreenModelArrayList;
    MainRecyclerAdapter mMainRecyclerAdapter;
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.main_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(AppConstants.IntentExtras.MTA) && intent.hasExtra(AppConstants.IntentExtras.CITI)) {
            mMtaMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.MTA);
            mCitiBikeMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.CITI);
            mRestaurantMainScreenModelArrayList=intent.getParcelableArrayListExtra(AppConstants.IntentExtras.RES);

        }
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_main_recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mMainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), mMtaMainScreenModelArrayList, mCitiBikeMainScreenModelArrayList,mRestaurantMainScreenModelArrayList);
        mRecyclerView.setAdapter(mMainRecyclerAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                // Toast.makeText(getActivity(), "onTouchEvent", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void changeDataSet(HashMap<Integer, Integer> filters) {
        Log.d(TAG, "changeDataSet");
        ArrayList<MTAMainScreenModel> localMTAMainScreenModelArrayList = new ArrayList<>();
        localMTAMainScreenModelArrayList.addAll(mMtaMainScreenModelArrayList);
        ArrayList<CitiBikeMainScreenModel> localCitiBikeMainScreenModelArrayList = new ArrayList<>();
        localCitiBikeMainScreenModelArrayList.addAll(mCitiBikeMainScreenModelArrayList);

        if (filters.containsKey(AppConstants.InAppConstants.SHOW)) {
            int value = filters.get(AppConstants.InAppConstants.SHOW);
            if (value == AppConstants.InAppConstants.SHOW_CITI) {
                localMTAMainScreenModelArrayList.clear();
            } else if (value == AppConstants.InAppConstants.SHOW_MTA) {
                localCitiBikeMainScreenModelArrayList.clear();
            } else if (value == AppConstants.InAppConstants.SHOW_RES) {

            }
        }
        if (filters.containsKey(AppConstants.InAppConstants.DISTANCE)) {
            int value = filters.get(AppConstants.InAppConstants.DISTANCE);
            if (value == AppConstants.InAppConstants.DISTANCE_DECREASING) {
                Collections.reverse(localMTAMainScreenModelArrayList);
                Collections.reverse(localCitiBikeMainScreenModelArrayList);
            }
        }
        mMainRecyclerAdapter.changeDataSet(localMTAMainScreenModelArrayList, localCitiBikeMainScreenModelArrayList, false);
    }

    public void dataFromMain(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                             ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {

        boolean mtaMore = false;
        if (mtaMainScreenModelArrayList != null) {
            mMtaMainScreenModelArrayList = mtaMainScreenModelArrayList;
            mtaMore = true;
        } else {
            mMtaMainScreenModelArrayList.clear();
        }
        if (citiBikeMainScreenModelArrayList != null) {
            mCitiBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
            mtaMore = false;
        } else {
            mCitiBikeMainScreenModelArrayList.clear();
        }
        mMainRecyclerAdapter.changeDataSet(mMtaMainScreenModelArrayList, mCitiBikeMainScreenModelArrayList, mtaMore);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
