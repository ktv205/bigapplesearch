package com.example.krishnateja.bigapplesearch.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.krishnateja.bigapplesearch.models.AppConstants;
import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RequestParams;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.SubwayAlertsModel;

import java.util.ArrayList;

/**
 * Created by krishnateja on 4/29/2015.
 */
public class CommonAsyncTask extends AsyncTask<RequestParams, Void, String> {

    private static final String TAG = CommonAsyncTask.class.getSimpleName();

    public interface ServerData {
        public void getMTAData(ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList);

        public void getCITIData(ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList);

        public void getResData(ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList);

        public void getAlertsData(ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList);

        //public void getFindRouteData(ArrayList<Object> someObject);
    }

    private Context mContext;
    private ServerData mServerData;
    private int mCode;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public CommonAsyncTask(Context context, int code) {
        mContext = context;
        mCode = code;
        try {
            mServerData = (ServerData) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + " must implement" + ServerData.class.getSimpleName());
        }

    }

    @Override
    protected String doInBackground(RequestParams... params) {
        return HttpManager.sendUserData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            if (mCode == AppConstants.InAppConstants.MTA_CODE) {
                ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList = ServerJSONParser.parseMTAMainScreenJSON(s);
                mServerData.getMTAData(mtaMainScreenModelArrayList);
            } else if (mCode == AppConstants.InAppConstants.CITI_CODE) {
                ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList;
                citiBikeMainScreenModelArrayList = ServerJSONParser.parseCitiBikeMainScreen(s);
                mServerData.getCITIData(citiBikeMainScreenModelArrayList);
            }else if(mCode==AppConstants.InAppConstants.RESTAURANT_CODE){
                ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList=ServerJSONParser.parseRestaurantMainScreen(s);
                mServerData.getResData(restaurantMainScreenModelArrayList);
            }else if(mCode==AppConstants.InAppConstants.MTA_ACTIVITY_CODE){
                ArrayList<MTAMainScreenModel> mtaMainScreenModelArrayList=ServerJSONParser.parseMTAActivityData(s);
                mServerData.getMTAData(mtaMainScreenModelArrayList);
            }else if(mCode==AppConstants.InAppConstants.ALERTS_CODE){
                ArrayList<SubwayAlertsModel> subwayAlertsModelArrayList=ServerJSONParser.parseAlerts(s);
                mServerData.getAlertsData(subwayAlertsModelArrayList);
            }else if(mCode==AppConstants.InAppConstants.FIND_ROUTE_CODE){
                Log.d(TAG, "hrere in find_route_code");
                //DoSomething here
            }



        }

    }
}
