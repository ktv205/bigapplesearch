package com.example.krishnateja.bigapplesearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.data.LeftDrawerItemDecoration;
import com.example.krishnateja.bigapplesearch.data.LeftDrawerRecyclerAdapter;
import com.example.krishnateja.bigapplesearch.data.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/8/2015.
 */
public class LeftDrawerFragment extends Fragment {
    private final static String TAG = LeftDrawerFragment.class.getSimpleName();
    LeftDrawerRecyclerAdapter mLeftDrawerRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_drawer, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_left_drawer_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mLeftDrawerRecyclerAdapter = new LeftDrawerRecyclerAdapter(getActivity(),null);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mLeftDrawerRecyclerAdapter);
        recyclerView.addItemDecoration(new LeftDrawerItemDecoration(getActivity(), null));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return view;
    }



}
