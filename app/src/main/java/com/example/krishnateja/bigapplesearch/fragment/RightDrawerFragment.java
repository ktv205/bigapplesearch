package com.example.krishnateja.bigapplesearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.utils.rightdrawerutils.RightDrawerItemDecorator;
import com.example.krishnateja.bigapplesearch.utils.rightdrawerutils.RightDrawerRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by krishnateja on 4/26/2015.
 */
public class RightDrawerFragment extends Fragment {
    private static final String TAG = RightDrawerFragment.class.getSimpleName();

    private View mView;
    private DrawerLayout mDrawerLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_right_drawer, container, false);
        return mView;
    }

    public void getDrawerLayout(DrawerLayout drawerLayout){

        mDrawerLayout=drawerLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button=(Button)mView.findViewById(R.id.fragment_right_drawer_done_button);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_right_drawer_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new RightDrawerItemDecorator(getActivity(), null));
        recyclerView.setLayoutManager(layoutManager);
        RightDrawerRecyclerAdapter rightDrawerRecyclerAdapter=new RightDrawerRecyclerAdapter(getActivity(),
                loadTextData(),loadSpinnerData(),button,mDrawerLayout);
        recyclerView.setAdapter(rightDrawerRecyclerAdapter);
    }

    public ArrayList<String> loadTextData(){
        ArrayList<String> data=new ArrayList<>();
        data.add("Show");
        data.add("Distance");
        data.add("Rating");
        data.add("Cuisine");
        data.add("price");
        return data;
    }
    public ArrayList<Integer> loadSpinnerData(){
        ArrayList<Integer> data=new ArrayList<>();
        data.add(R.array.sections);
        data.add(R.array.distance);
        data.add(R.array.rating);
        data.add(R.array.cuisine);
        data.add(R.array.price);
        return data;
    }


}
