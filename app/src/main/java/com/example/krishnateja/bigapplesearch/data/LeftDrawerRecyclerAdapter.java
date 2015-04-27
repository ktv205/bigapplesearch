package com.example.krishnateja.bigapplesearch.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.krishnateja.bigapplesearch.R;

import java.util.ArrayList;

/**
 * Created by krishnateja on 4/8/2015.
 */
public class LeftDrawerRecyclerAdapter extends RecyclerView.Adapter<LeftDrawerRecyclerAdapter.ViewHolder> {


    public LeftDrawerRecyclerAdapter(Context context,ArrayList<String> mainData){

    }
    public static final String TAG = LeftDrawerRecyclerAdapter.class.getSimpleName();

    @Override
    public LeftDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(final LeftDrawerRecyclerAdapter.ViewHolder viewHolder, int i) {



    }

    @Override
    public int getItemCount() {
       return 0;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

    @Override
    public int getItemViewType(int position) {
       return -1;
    }
}
