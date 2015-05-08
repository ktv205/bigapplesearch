package com.example.krishnateja.bigapplesearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.utils.rightdrawerutils.RightDrawerItemDecorator;
import com.example.krishnateja.bigapplesearch.utils.rightdrawerutils.RightDrawerRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by krishnateja on 4/26/2015.
 */
public class RightDrawerFragment extends Fragment {
    private static final String TAG = RightDrawerFragment.class.getSimpleName();
    private int mSelection = 0;

    private View mView;
    private DrawerLayout mDrawerLayout;
    private RightDrawerRecyclerAdapter mRightDrawerRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mSelection = savedInstanceState.getInt(AppConstants.BundleExtras.LEFT_SELECTED);
        }
        mView = inflater.inflate(R.layout.fragment_right_drawer, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) mView.findViewById(R.id.fragment_right_drawer_done_button);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_right_drawer_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new RightDrawerItemDecorator(getActivity(), null));
        recyclerView.setLayoutManager(layoutManager);
        mRightDrawerRecyclerAdapter = new RightDrawerRecyclerAdapter(getActivity(),
                loadTextData(), loadSpinnerData(), filtersToShow(), button, mDrawerLayout);
        if (savedInstanceState != null) {
            HashMap<String, Integer> hashMap = (HashMap) savedInstanceState.getSerializable(AppConstants.BundleExtras.FILTERS_SELECTED);
            mRightDrawerRecyclerAdapter.setmSpinnerSelection(hashMap);
        } else {
            mRightDrawerRecyclerAdapter.setmSpinnerSelection(new HashMap<String, Integer>());
        }


        recyclerView.setAdapter(mRightDrawerRecyclerAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(AppConstants.BundleExtras.LEFT_SELECTED, mSelection);
        outState.putSerializable(AppConstants.BundleExtras.FILTERS_SELECTED, mRightDrawerRecyclerAdapter.getmSpinnerSelection());
        super.onSaveInstanceState(outState);
    }

    public void getDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }


    public ArrayList<String> loadTextData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(AppConstants.InAppConstants.SHOW_TEXT);
        data.add(AppConstants.InAppConstants.DISTANCE_TEXT);
        data.add(AppConstants.InAppConstants.RATING_TEXT);
        data.add(AppConstants.InAppConstants.CUISINE_TEXT);
        return data;
    }

    public ArrayList<Integer> loadSpinnerData() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(R.array.sections);
        data.add(R.array.distance);
        data.add(R.array.rating);
        data.add(R.array.cuisine);
        return data;
    }

    public void filtersFromLeftSelections(int selection) {
        mSelection = selection;
        mRightDrawerRecyclerAdapter.setmSpinnerSelection(new HashMap<String, Integer>());
        mRightDrawerRecyclerAdapter.changeDataSet(loadSpinnerData(), loadTextData(), filtersToShow());

    }

    public HashMap<Integer, Boolean> filtersToShow() {
        HashMap<Integer, Boolean> hashMap = new HashMap<>();
        hashMap.put(AppConstants.InAppConstants.SHOW, true);
        hashMap.put(AppConstants.InAppConstants.DISTANCE, true);
        hashMap.put(AppConstants.InAppConstants.RATING, true);
        hashMap.put(AppConstants.InAppConstants.CUISINE, true);
        if (mSelection == AppConstants.InAppConstants.NEARBY_LEFT) {

        } else if (mSelection == AppConstants.InAppConstants.CITI_LEFT || mSelection == AppConstants.InAppConstants.MTA_LEFT) {
            Log.d(TAG, "here in MTA");
            hashMap.put(AppConstants.InAppConstants.DISTANCE, true);
            hashMap.put(AppConstants.InAppConstants.RATING, false);
            hashMap.put(AppConstants.InAppConstants.CUISINE, false);
            hashMap.put(AppConstants.InAppConstants.SHOW, false);
        } else {
            hashMap.put(AppConstants.InAppConstants.SHOW, false);
        }
        return hashMap;

    }


}
