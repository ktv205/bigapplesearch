package com.example.krishnateja.bigapplesearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.utils.leftdrawerutils.LeftDrawerItemDecoration;
import com.example.krishnateja.bigapplesearch.utils.leftdrawerutils.LeftDrawerRecyclerAdapter;
import com.example.krishnateja.bigapplesearch.utils.leftdrawerutils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/8/2015.
 */
public class LeftDrawerFragment extends Fragment {
    private final static String TAG = LeftDrawerFragment.class.getSimpleName();
    LeftDrawerRecyclerAdapter mLeftDrawerRecyclerAdapter;
    private DrawerLayout mDrawerLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_drawer, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_left_drawer_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        String[] headings={"Near by","Transport","Subway","City bikes","Food","Restaurants"};
        ArrayList<String> data=leftDrawerData(headings);
        HashMap<Integer,Integer> sections=leftDrawerSections(headings);
        mLeftDrawerRecyclerAdapter = new LeftDrawerRecyclerAdapter(getActivity(),data,sections);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mLeftDrawerRecyclerAdapter);
        recyclerView.addItemDecoration(new LeftDrawerItemDecoration(getActivity(), null));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for(int i=0;i<recyclerView.getChildCount();i++){
                    recyclerView.getChildAt(i).setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                    mDrawerLayout.closeDrawer(GravityCompat.START);

                }

                view.setBackgroundColor(getActivity().getResources().getColor(R.color.selectColor));


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return view;
    }

    public ArrayList<String> leftDrawerData(String[] headings){
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<headings.length;i++){
            list.add(headings[i]);
        }
        return list;

    }

    public HashMap<Integer,Integer> leftDrawerSections(String[] headings){
        HashMap<Integer, Integer> sections=new HashMap<>();
        for(int i=0;i<headings.length;i++)
            if (i == 1 || i == 4) {
                sections.put(i, 2);
            } else {
                sections.put(i, 1);
            }
        return sections;
    }

    public void getDrawerLayout(DrawerLayout drawerLayout){
           mDrawerLayout=drawerLayout;
    }



}
