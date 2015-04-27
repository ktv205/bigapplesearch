package com.example.krishnateja.bigapplesearch.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.bigapplesearch.R;

import java.util.ArrayList;


/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    public ArrayList<String> data=new ArrayList<String>();
    private Context mContext;
    public MainRecyclerAdapter(Context context){
        mContext=context;
        for(int i=0;i<20;i++) {
            data.add("hello");
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
           //setAnimation(viewHolder.view,i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
        }
    }
//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position < data.size()-1)
//        {
//            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
//            viewToAnimate.startAnimation(animation);
//        }
//    }
}
