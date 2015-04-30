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
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.mainactivityutils.MainRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainFragment extends Fragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList = null;
        if (intent != null && intent.hasExtra(AppConstants.IntentExtras.MTA)) {
            mtaMainScreenModelArrayList = intent.getParcelableArrayListExtra(AppConstants.IntentExtras.MTA);
        }
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_main_recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MainRecyclerAdapter(getActivity(), mtaMainScreenModelArrayList));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
}
