package com.example.krishnateja.bigapplesearch.utils.mainactivityutils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.utils.CommonFunctions;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by krishnateja on 4/15/2015.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> implements View.OnClickListener {


    private static final String TAG = MainRecyclerAdapter.class.getSimpleName();
    private Context mContext;
    public ArrayList<MTAMainScreenModel> mMTAMainScreenModelArrayList;
    public ArrayList<CitiBikeMainScreenModel> mCitiBikeMainScreenModelArrayList;
    private int mMTASize;
    private int mCitiSize;
    private int mMoreSize=0;

    public MainRecyclerAdapter(Context context, ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                               ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList) {
        mContext = context;
        mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;
        mCitiBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
        mMTASize = mMTAMainScreenModelArrayList.size();
        mCitiSize = mCitiBikeMainScreenModelArrayList.size();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(i==AppConstants.InAppConstants.MORE_CODE){
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_load_more,viewGroup,false));
        }else {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int type=getItemViewType(i);
        if (type == AppConstants.InAppConstants.MTA_CODE) {
            MTAMainScreenModel mtaMainScreenModel = mMTAMainScreenModelArrayList.get(i);
            viewHolder.headingTextView.setText(AppConstants.InAppConstants.MTA);
            viewHolder.nameTextView.setText(mtaMainScreenModel.getStopName());
            viewHolder.distanceTextView.setText
                    (CommonFunctions.
                            setPrecision(mtaMainScreenModel.getDistance()) + " miles");
            viewHolder.directionsTextView.setText("directions");
            int size = mtaMainScreenModel.getRouteId().size();
            StringBuilder lines = new StringBuilder();
            for (int j = 0; j < size; j++) {
                lines.append(mtaMainScreenModel.getRouteId().get(j));
                lines.append(",");
            }
            if (size > 0) {
                lines = lines.deleteCharAt(lines.length() - 1);
            }
            viewHolder.titleLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.mtaColor));
            viewHolder.linesCuisineTextView.setText(lines.toString());
            viewHolder.websiteTextView.setText(AppConstants.InAppConstants.MTAURL);
            viewHolder.phoneTextView.setText(AppConstants.InAppConstants.MTAPHONE);
            viewHolder.directionsTextView.setTag(mtaMainScreenModel.getStopLatitude() + "," + mtaMainScreenModel.getStopLongitude());


        } else if(type==AppConstants.InAppConstants.CITI_CODE) {
            CitiBikeMainScreenModel citiBikeMainScreenModel = mCitiBikeMainScreenModelArrayList.get(i - mMTASize);
            viewHolder.titleLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.citiColor));
            viewHolder.headingTextView.setText(AppConstants.InAppConstants.CITI);
            viewHolder.nameTextView.setText(citiBikeMainScreenModel.getStationName());
            viewHolder.directionsTextView.setText("directions");
            viewHolder.distanceTextView.setText(CommonFunctions.setPrecision(citiBikeMainScreenModel.getDistance()) + " miles");
            viewHolder.phoneTextView.setText(AppConstants.InAppConstants.CITIPHONE);
            viewHolder.websiteTextView.setText(AppConstants.InAppConstants.CITIURL);
            viewHolder.linesCuisineTextView.setText(citiBikeMainScreenModel.getStatusValue() +
                    " " + citiBikeMainScreenModel.getAvailableBikes() + " bikes available");
            viewHolder.directionsTextView.setTag(citiBikeMainScreenModel.getLatitude() + "," + citiBikeMainScreenModel.getLongitude());
        }else if(type==AppConstants.InAppConstants.MORE_CODE){
            viewHolder.loadMoreLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "clicked on load more");
                }
            });

        }
        if(type!=AppConstants.InAppConstants.MORE_CODE) {
            viewHolder.phoneTextView.setOnClickListener(this);
            viewHolder.websiteTextView.setOnClickListener(this);
            viewHolder.directionsTextView.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return mMTASize + mCitiSize+mMoreSize;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView textView = null;
        textView = (TextView) v;
        if (id == R.id.item_main_list_card_phone_text) {
            if (textView != null) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + textView.getText().toString()));
                mContext.startActivity(intent);

            }
        } else if (id == R.id.item_main_list_card_website_text) {
            if (textView != null) {
                String url = "http://www." + textView.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        } else if (id == R.id.item_main_list_card_directions_text) {
            if (textView != null) {
                String latLng = textView.getTag().toString();
                if (latLng != null) {
                    String[] latLngArray = latLng.split(",");
                    double lat = Double.valueOf(latLngArray[0]);
                    double lng = Double.valueOf(latLngArray[1]);
                    String uriBegin = "geo:" + lat + "," + lng;
                    String query = lat + "," + lng;
                    String encodedQuery = Uri.encode(query);
                    String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                    Uri uri = Uri.parse(uriString);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
            }
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout titleLinearLayout;
        ImageView iconDirectionImageView;
        TextView headingTextView, nameTextView,
                linesCuisineTextView, distanceTextView, directionsTextView, priceTextView, websiteTextView, phoneTextView;
        RatingBar ratingBar;
        LinearLayout loadMoreLinearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            titleLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_main_list_card_title_linear);
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
            loadMoreLinearLayout=(LinearLayout)itemView.findViewById(R.id.item_main_load_more_linearlayout);


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mMTASize) {
            return AppConstants.InAppConstants.MTA_CODE;
        } else if(mCitiBikeMainScreenModelArrayList.size()!=0) {
            return AppConstants.InAppConstants.CITI_CODE;
        }else if(mMoreSize==1){
            return AppConstants.InAppConstants.MORE_CODE;
        }else{
            return -1;
        }

    }

    public void changeDataSet(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                              ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList,boolean mtaMore){
        mMTAMainScreenModelArrayList=mtaMainScreenModelArrayList;
        mCitiBikeMainScreenModelArrayList=citiBikeMainScreenModelArrayList;
        mMTASize=mMTAMainScreenModelArrayList.size();
        mCitiSize=mCitiBikeMainScreenModelArrayList.size();
        if(mtaMore){
          mMoreSize=1;
        }else{
            mMoreSize=0;
        }
        notifyDataSetChanged();

    }
}
