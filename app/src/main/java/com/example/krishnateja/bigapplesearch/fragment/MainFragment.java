package com.example.krishnateja.bigapplesearch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.mainactivityutils.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainFragment extends Fragment {

    private View mView;
    RecyclerView mRecyclerView;
    ArrayList<MTAMainScreenModel> mMtaMainScreenModelArrayList = null;
    ArrayList<CitiBikeMainScreenModel> mCitiBikeMainScreenModelArrayList = null;
    MainRecyclerAdapter mMainRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(AppConstants.IntentExtras.MTA) && intent.hasExtra(AppConstants.IntentExtras.CITI)) {
            mMtaMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.MTA);
            mCitiBikeMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.CITI);
        }
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_main_recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mMainRecyclerAdapter = new MainRecyclerAdapter(getActivity(), mMtaMainScreenModelArrayList, mCitiBikeMainScreenModelArrayList);
        mRecyclerView.setAdapter(mMainRecyclerAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                Toast.makeText(getActivity(), "onTouchEvent", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void changeDataSet(int[] filters) {
        ArrayList<MTAMainScreenModel> localMTAMainScreenModelArrayList = new ArrayList<>();
       localMTAMainScreenModelArrayList.addAll(mMtaMainScreenModelArrayList);
        ArrayList<CitiBikeMainScreenModel> localCitiBikeMainScreenModelArrayList = new ArrayList<>();
        localCitiBikeMainScreenModelArrayList.addAll(mCitiBikeMainScreenModelArrayList);
        if (filters[AppConstants.InAppConstants.SHOW] == AppConstants.InAppConstants.SHOW_ALL) {
            if (filters[AppConstants.InAppConstants.DISTANCE] == AppConstants.InAppConstants.DISTANCE_DECREASING) {
                Collections.reverse(localMTAMainScreenModelArrayList);
                Collections.reverse(localCitiBikeMainScreenModelArrayList);
            }
        } else if (filters[AppConstants.InAppConstants.SHOW] == AppConstants.InAppConstants.SHOW_MTA) {
            localCitiBikeMainScreenModelArrayList.clear();
            if (filters[AppConstants.InAppConstants.DISTANCE] == AppConstants.InAppConstants.DISTANCE_DECREASING) {
                Collections.reverse(localMTAMainScreenModelArrayList);
            }
        } else if (filters[AppConstants.InAppConstants.SHOW] == AppConstants.InAppConstants.SHOW_CITI) {
            localMTAMainScreenModelArrayList.clear();
            if (filters[AppConstants.InAppConstants.DISTANCE] == AppConstants.InAppConstants.DISTANCE_DECREASING) {
                Collections.reverse(localCitiBikeMainScreenModelArrayList);
            }
        } else {

        }
        mMainRecyclerAdapter.changeDataSet(localMTAMainScreenModelArrayList, localCitiBikeMainScreenModelArrayList);
    }
}
