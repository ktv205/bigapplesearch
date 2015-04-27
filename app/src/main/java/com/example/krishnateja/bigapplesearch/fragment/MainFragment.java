package com.example.krishnateja.bigapplesearch.fragment;

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
import com.example.krishnateja.bigapplesearch.data.MainRecyclerAdapter;

/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.fragment_main_recycle_view);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MainRecyclerAdapter(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                //Toast.makeText(getActivity(),"onInterceptTouchEvent",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                Toast.makeText(getActivity(),"onTouchEvent",Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
}
