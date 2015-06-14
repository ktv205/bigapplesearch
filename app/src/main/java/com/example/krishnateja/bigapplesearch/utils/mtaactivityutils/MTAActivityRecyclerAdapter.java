package com.example.krishnateja.bigapplesearch.utils.mtaactivityutils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/23/2015.
 */
public class MTAActivityRecyclerAdapter extends RecyclerView.Adapter<MTAActivityRecyclerAdapter.ViewHolder> {

    private ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;

    public MTAActivityRecyclerAdapter() {

    }

    public MTAActivityRecyclerAdapter(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {
        mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;

    }

    @Override
    public MTAActivityRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mta_activity_recycler_view, parent, false), 0);
    }

    @Override
    public void onBindViewHolder(MTAActivityRecyclerAdapter.ViewHolder holder, int position) {
        holder.routeNameTextView.setText(mMTAMainScreenModelArrayList.get(position).getOneRouteShortName());
        holder.routeDescTextView.setText(mMTAMainScreenModelArrayList.get(position).getOneRouteDesc());
        holder.routeDirectionTextView.setText("towards " + mMTAMainScreenModelArrayList.get(position).getOneTripHeadSign());
        holder.arrivalTimeTextView.setText(mMTAMainScreenModelArrayList.get(position).getOneArrivalTime());
    }

    @Override
    public int getItemCount() {
        return mMTAMainScreenModelArrayList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView routeNameTextView, routeDirectionTextView, routeDescTextView, arrivalTimeTextView;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            routeNameTextView = (TextView) itemView.findViewById(R.id.route_text_view);
            routeDirectionTextView = (TextView) itemView.findViewById(R.id.direction_text_view);
            routeDescTextView = (TextView) itemView.findViewById(R.id.desc_text_view);
            arrivalTimeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
        }
    }

    public void changeData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList){
        mMTAMainScreenModelArrayList=mtaMainScreenModelArrayList;
        notifyDataSetChanged();
    }
}
