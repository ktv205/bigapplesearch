package com.example.krishnateja.bigapplesearch.fragment;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.mainactivityutils.MainRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/7/2015.
 */
public class SearchFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG =SearchFragment.class.getSimpleName() ;
    private SearchFragmentInstance mSearchFragmentInstance;
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mSelection = AppConstants.InAppConstants.NEARBY_LEFT;
    public ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    public ArrayList<CitiBikeMainScreenModel> mCitiBikeMainScreenModelArrayList;
    public ArrayList<RestaurantMainScreenModel> mRestaurantMainScreenModelArrayList;

    private MainRecyclerAdapter mMainRecyclerAdapter;

    @Override
    public void onRefresh() {

    }


    public interface SearchFragmentInstance{
         void getInstance(SearchFragment searchFragment);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mSearchFragmentInstance=(SearchFragmentInstance)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_main,container,false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.fragment_main_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_main_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), mMTAMainScreenModelArrayList,
                mCitiBikeMainScreenModelArrayList, mRestaurantMainScreenModelArrayList, false);
        recyclerView.setAdapter(mMainRecyclerAdapter);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchFragmentInstance.getInstance(this);
    }

    public void getResultsFromSearch(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                                ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList,
                                ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Log.d(TAG, "in getResults");
       mMainRecyclerAdapter.dataSetChanged(mtaMainScreenModelArrayList, citiBikeMainScreenModelArrayList, restaurantMainScreenModelArrayList, false);

    }
}
