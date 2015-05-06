package com.example.krishnateja.bigapplesearch.utils.leftdrawerutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.krishnateja.bigapplesearch.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/8/2015.
 */
public class LeftDrawerRecyclerAdapter extends RecyclerView.Adapter<LeftDrawerRecyclerAdapter.ViewHolder> {
    private ArrayList<String> mData;
    private HashMap<Integer, Integer> mSections;
    private static final int HEADING = 2;
    private static final int SELECTION = 1;
    private Context mContext;
    private int mCurrentSelection;


    public LeftDrawerRecyclerAdapter(Context context, ArrayList<String> data, HashMap<Integer, Integer> sections,int currentSelection) {
        mData = data;
        mSections = sections;
        mContext = context;
        mCurrentSelection=currentSelection;

    }

    public static final String TAG = LeftDrawerRecyclerAdapter.class.getSimpleName();

    @Override
    public LeftDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        if (i == HEADING) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_section, viewGroup, false);
            return new ViewHolder(view, HEADING);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_left_drawer_list, viewGroup, false);
            return new ViewHolder(view, SELECTION);
        }


    }


    @Override
    public void onBindViewHolder(final LeftDrawerRecyclerAdapter.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type == HEADING) {
            viewHolder.headingTextView.setText(mData.get(i));
        } else {
            viewHolder.selectionTextView.setText(mData.get(i));
            if (i == 2) {
                viewHolder.selectionImageView.setImageResource(R.drawable.rsz_mta);
            } else if (i == 0) {
                viewHolder.selectionImageView.setImageResource(R.drawable.locationicon);
            } else if (i == 3) {
                viewHolder.selectionImageView.setImageResource(R.drawable.rsz_citybike);
            } else if (i == 5) {
                viewHolder.selectionImageView.setImageResource(R.drawable.rsz_restaurants);
            }
            if(i==mCurrentSelection){
                viewHolder.linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.selectColor));
            }

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView selectionTextView;
        ImageView selectionImageView;
        TextView headingTextView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            if (type == HEADING) {
                headingTextView = (TextView) itemView.findViewById(R.id.item_list_section);
            } else {
                selectionTextView = (TextView) itemView.findViewById(R.id.list_item_textview);
                selectionImageView = (ImageView) itemView.findViewById(R.id.list_item_imageview);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.item_list_linear);
            }
        }

    }


    @Override
    public int getItemViewType(int position) {
        return mSections.get(position);
    }
}
