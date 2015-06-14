package com.example.krishnateja.bigapplesearch.utils.alertsutils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.SubwayAlertsModel;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/25/2015.
 */
public class AlertsRecyclerAdapter extends RecyclerView.Adapter<AlertsRecyclerAdapter.ViewHolder> {

    private ArrayList<SubwayAlertsModel> mSubwayAlertsModelArrayList;

    public AlertsRecyclerAdapter(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList) {
        mSubwayAlertsModelArrayList = subwayAlertsModelArrayList;
    }

    @Override
    public AlertsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alerts_recycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(AlertsRecyclerAdapter.ViewHolder holder, int position) {
        holder.date.setText(mSubwayAlertsModelArrayList.get(position).getLineDate());
        holder.status.setText(mSubwayAlertsModelArrayList.get(position).getLineStatus());
        holder.name.setText(mSubwayAlertsModelArrayList.get(position).getLineName());
        holder.time.setText(mSubwayAlertsModelArrayList.get(position).getLineTime());
        holder.text.setText(mSubwayAlertsModelArrayList.get(position).getLineText());

    }

    @Override
    public int getItemCount() {
        return mSubwayAlertsModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, status, text, date, time;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_line_name);
            status = (TextView) itemView.findViewById(R.id.item_line_status);
            text = (TextView) itemView.findViewById(R.id.item_line_text);
            date = (TextView) itemView.findViewById(R.id.item_line_date);
            time = (TextView) itemView.findViewById(R.id.item_line_time);
        }
    }

    public void changeData(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList){
        mSubwayAlertsModelArrayList=subwayAlertsModelArrayList;
        notifyDataSetChanged();
    }
}
