package com.example.krishnateja.bigapplesearch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.SubwayAlertsModel;
import com.example.krishnateja.bigapplesearch.utils.alertsutils.AlertsRecycleViewItemDecorator;
import com.example.krishnateja.bigapplesearch.utils.alertsutils.AlertsRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/27/2015.
 */
public class RightMTADrawerFragment extends Fragment {
    View mView;
    private AlertsRecyclerAdapter mAlertsRecyclerAdapter;
    ArrayList<SubwayAlertsModel> mSubwayAlertsModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_right_mta_drawer, container, false);
        mSubwayAlertsModelArrayList=new ArrayList<>();
        fillRecyclerView(mSubwayAlertsModelArrayList,mView);
        return mView;
    }

    public void fillRecyclerView(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList,View view) {
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.fragment_right_mta_drawer_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAlertsRecyclerAdapter = new AlertsRecyclerAdapter(subwayAlertsModelArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAlertsRecyclerAdapter);
        recyclerView.addItemDecoration(new AlertsRecycleViewItemDecorator(getActivity(), null));
    }

    public void sendAlertsToRecyclerView(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList){
       mAlertsRecyclerAdapter.changeData(subwayAlertsModelArrayList);
    }
}
