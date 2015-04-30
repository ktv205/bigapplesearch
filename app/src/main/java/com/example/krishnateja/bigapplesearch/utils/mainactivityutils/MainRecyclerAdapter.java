package com.example.krishnateja.bigapplesearch.utils.mainactivityutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;

import java.util.ArrayList;


/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;

    public MainRecyclerAdapter(Context context, ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList) {
        mContext = context;
        mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.iconCardImageView.setImageResource(R.drawable.rsz_mta);
        viewHolder.headingTextView.setText(AppConstants.InAppConstants.MTA);
        viewHolder.nameTextView.setText(mMTAMainScreenModelArrayList.get(i).getStopName());
        viewHolder.distanceTextView.setText
                (CommonFunctions.
                        setPrecision(mMTAMainScreenModelArrayList.get(i).getDistance()) + " miles");
        viewHolder.directionsTextView.setText("directions");
        viewHolder.directionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        int size = mMTAMainScreenModelArrayList.get(i).getRouteId().size();
        StringBuilder lines = new StringBuilder();
        for (int j = 0; j < size; j++) {
            lines.append(mMTAMainScreenModelArrayList.get(i).getRouteId().get(j));
            lines.append(",");
        }
        viewHolder.linesCuisineTextView.setText(lines.toString());
        viewHolder.websiteTextView.setText(AppConstants.InAppConstants.MTAURL);
        viewHolder.websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.phoneTextView.setText(AppConstants.InAppConstants.MTAPHONE);
        viewHolder.phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mMTAMainScreenModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iconCardImageView, iconDirectionImageView;
        TextView headingTextView, nameTextView,
                linesCuisineTextView, distanceTextView, directionsTextView, priceTextView, websiteTextView, phoneTextView;
        RatingBar ratingBar;


        public ViewHolder(View itemView) {
            super(itemView);
            iconCardImageView = (ImageView) itemView.findViewById(R.id.item_main_list_card_icon_image);
            iconDirectionImageView = (ImageView) itemView.findViewById(R.id.item_main_list_card_directions_icon);
            headingTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_title_text);
            nameTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_name_text);
            linesCuisineTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_cusine_lines_text);
            distanceTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_distance_text);
            directionsTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_directions_text);
            websiteTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_website_text);
            phoneTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_phone_text);
            priceTextView = (TextView) itemView.findViewById(R.id.item_main_list_card_price_text);
            ratingBar = (RatingBar) itemView.findViewById(R.id.item_main_list_card_rating);


        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
