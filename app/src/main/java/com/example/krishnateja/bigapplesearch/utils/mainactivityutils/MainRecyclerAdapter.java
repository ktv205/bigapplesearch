package com.example.krishnateja.bigapplesearch.utils.mainactivityutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.krishnateja.bigapplesearch.R;
import com.example.krishnateja.bigapplesearch.activities.MTAActivity;
import com.example.krishnateja.bigapplesearch.activities.MainActivity;
import com.example.krishnateja.bigapplesearch.fragment.ViolationsDialogFragment;
import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RequestParams;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
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
    public ArrayList<RestaurantMainScreenModel> mRestaurantMainScreenModelArrayList;
    private int mMTASize;
    private int mCitiSize;
    private int mResSize;
    private int mMoreSize = 0;
    private boolean mLoadMore = false;
    private int next = 0;


    public MainRecyclerAdapter(Context context, ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                               ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList, ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList, boolean loadMore) {
        mContext = context;
        mLoadMore = loadMore;
        if (mLoadMore) {
            mMoreSize = 1;
        }
        if (mtaMainScreenModelArrayList != null) {
            mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;
            mMTASize = mMTAMainScreenModelArrayList.size();
        } else {
            mMTAMainScreenModelArrayList = new ArrayList<>();
            mMTASize = 0;
        }
        if (citiBikeMainScreenModelArrayList != null) {
            mCitiBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
            mCitiSize = mCitiBikeMainScreenModelArrayList.size();
        } else {
            mCitiSize = 0;
            mCitiBikeMainScreenModelArrayList = new ArrayList<>();
        }
        if (restaurantMainScreenModelArrayList != null) {
            mRestaurantMainScreenModelArrayList = restaurantMainScreenModelArrayList;
            mResSize = mRestaurantMainScreenModelArrayList.size();
        } else {
            mResSize = 0;
            mRestaurantMainScreenModelArrayList = new ArrayList<>();
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == AppConstants.InAppConstants.MORE_CODE) {
            Log.d(TAG, "onCreview holder more_code");
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_load_more, viewGroup, false), i);
        } else {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list, viewGroup, false), i);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        final int pos = i;
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
            viewHolder.titleLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, MTAActivity.class);
                    intent.putExtra(AppConstants.IntentExtras.MTA,mMTAMainScreenModelArrayList.get(pos));
                    mContext.startActivity(intent);
                }
            });

        } else if (type == AppConstants.InAppConstants.CITI_CODE) {
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
        } else if (type == AppConstants.InAppConstants.MORE_CODE) {
            viewHolder.loadMore.setOnClickListener(this);

        } else if (type == AppConstants.InAppConstants.RESTAURANT_CODE) {
            viewHolder.ratingBar.setVisibility(View.VISIBLE);
            viewHolder.priceTextView.setVisibility(View.VISIBLE);
            // Log.d(TAG, "fill res details");
            viewHolder.headingTextView.setText(AppConstants.InAppConstants.RESTAURANTS);
            viewHolder.titleLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.resBlue));
            viewHolder.nameTextView.setText(mRestaurantMainScreenModelArrayList.get(i - (mCitiSize + mMTASize)).getResName());
            viewHolder.distanceTextView.setText(CommonFunctions.setPrecision(mRestaurantMainScreenModelArrayList.get(i - (mCitiSize + mMTASize)).getDistance() / 1609.344) + " miles");
            ArrayList<String> cat = mRestaurantMainScreenModelArrayList.get(i - (mCitiSize + mMTASize)).getCategories();

            viewHolder.directionsTextView.setTag(mRestaurantMainScreenModelArrayList.get(i - (mCitiSize + mMTASize)).getLat() + "," + mRestaurantMainScreenModelArrayList.get(i - (mCitiSize + mMTASize)).getLng());
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < cat.size(); j++) {
                builder.append(cat.get(j) + ",");

            }

            if (builder.length() > 0) {
                builder = builder.deleteCharAt(builder.length() - 1);
            }
            viewHolder.linesCuisineTextView.setText(builder.toString());
            viewHolder.directionsTextView.setText("directions");
            double rating = mRestaurantMainScreenModelArrayList.get(i - (mCitiSize + mMTASize)).getRating();
            viewHolder.ratingBar.setRating((float) rating);
            ArrayList<String> violations = mRestaurantMainScreenModelArrayList.get(i - (mMTASize + mCitiSize)).getViolationCodes();
            builder = new StringBuilder();
            if (violations != null) {
                for (int j = 0; j < violations.size(); j++) {
                    builder.append(violations.get(j) + ",");

                }
            }

            if (builder.length() > 0) {
                builder = builder.deleteCharAt(builder.length() - 1);
                //viewHolder.priceTextView.setText(builder.toString());
                viewHolder.priceTextView.setText("click here for violations");
                viewHolder.priceTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViolationsDialogFragment violationsDialogFragment = new ViolationsDialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(AppConstants.BundleExtras.VIOLATION_CODES,
                                mRestaurantMainScreenModelArrayList.get(pos - (mCitiSize + mMTASize)).getViolationCodes());
                        bundle.putInt(AppConstants.BundleExtras.VIOLATION_SCORE, mRestaurantMainScreenModelArrayList.get(pos - (mCitiSize + mMTASize)).getViolationScore());
                        ActionBarActivity activity = (ActionBarActivity) mContext;
                        violationsDialogFragment.setArguments(bundle);
                        violationsDialogFragment.show(activity.getSupportFragmentManager(), "TAG");

                    }
                });
            } else {
                viewHolder.priceTextView.setText("no violations");
            }
            String url = mRestaurantMainScreenModelArrayList.get(i - (mMTASize + mCitiSize)).getUrl().replace("http://www.", "");
            viewHolder.websiteTextView.setText(url);
            viewHolder.phoneTextView.setText(mRestaurantMainScreenModelArrayList.get(i - (mMTASize + mCitiSize)).getPhone());


        }
        if (type != AppConstants.InAppConstants.MORE_CODE) {
            viewHolder.phoneTextView.setOnClickListener(this);
            viewHolder.websiteTextView.setOnClickListener(this);
            viewHolder.directionsTextView.setOnClickListener(this);
        }

    }

    @Override
    public int getItemCount() {
        return mMTASize + mCitiSize + mResSize + mMoreSize;
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
                Log.d(TAG, url);
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
        } else if (id == R.id.item_main_load_more_button) {
            Log.d(TAG, "laodMore");
        }


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout titleLinearLayout;
        ImageView iconDirectionImageView;
        TextView headingTextView, nameTextView,
                linesCuisineTextView, distanceTextView, directionsTextView, priceTextView, websiteTextView, phoneTextView;
        RatingBar ratingBar;
        LinearLayout loadMoreLinearLayout;
        Button loadMore;


        public ViewHolder(View itemView, int type) {
            super(itemView);
            if (type == AppConstants.InAppConstants.MORE_CODE) {
                Log.d(TAG, "more_code in view holder");
                loadMore = (Button) itemView.findViewById(R.id.item_main_load_more_button);
            } else {
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
                loadMoreLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_main_load_more_linearlayout);
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mMTASize) {
            return AppConstants.InAppConstants.MTA_CODE;
        } else if (mLoadMore && mMoreSize == 1) {
            return AppConstants.InAppConstants.MORE_CODE;
        } else if (position < mMTASize + mCitiSize) {
            return AppConstants.InAppConstants.CITI_CODE;
        } else {
            return AppConstants.InAppConstants.RESTAURANT_CODE;
        }

    }

    public void dataSetChanged(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList,
                               ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList,
                               ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList, boolean loadMore) {
        mLoadMore = loadMore;
        if (mLoadMore) {
            mMoreSize = 1;
        } else {
            mMoreSize = 0;
        }
        if (mtaMainScreenModelArrayList != null) {
            mMTAMainScreenModelArrayList = mtaMainScreenModelArrayList;
            mMTASize = mMTAMainScreenModelArrayList.size();
        } else {
            mMTAMainScreenModelArrayList = new ArrayList<>();
            mMTASize = 0;
        }
        if (citiBikeMainScreenModelArrayList != null) {
            mCitiBikeMainScreenModelArrayList = citiBikeMainScreenModelArrayList;
            mCitiSize = mCitiBikeMainScreenModelArrayList.size();
        } else {
            mCitiSize = 0;
            mCitiBikeMainScreenModelArrayList = new ArrayList<>();
        }
        if (restaurantMainScreenModelArrayList != null) {
            mRestaurantMainScreenModelArrayList = restaurantMainScreenModelArrayList;
            mResSize = mRestaurantMainScreenModelArrayList.size();
        } else {
            mResSize = 0;
            mRestaurantMainScreenModelArrayList = new ArrayList<>();
        }
        notifyDataSetChanged();

    }

    public class MoreAsyncTask extends AsyncTask<RequestParams, Void, String> {

        @Override
        protected String doInBackground(RequestParams... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
