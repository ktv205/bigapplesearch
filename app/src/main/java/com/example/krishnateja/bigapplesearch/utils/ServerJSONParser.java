package com.example.krishnateja.bigapplesearch.utils;

import android.util.Log;

import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/29/2015.
 */
public class ServerJSONParser {
    private static final String STOP_ID = "stop_id";
    private static final String STOP_NAME = "stop_name";
    private static final String STOP_LAT = "stop_lat";
    private static final String STOP_LON = "stop_lon";
    private static final String DISTANCE = "distance";
    private static final String ROUTES = "routes";
    private static final String ROUTE_ID = "route_id";
    private static final String ROUTE_NAME = "route_short_name";
    private static final String ROUTE_COLOR = "route_color";
    private static final String TAG = ServerJSONParser.class.getSimpleName();
    private static final String ID = "id";
    private static final String STATION_NAME = "stationName";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String AVAILABLE_DOCKS = "availableDocks";
    private static final String TOTAL_DOCKS = "totalDocks";
    private static final String STATUS_KEY = "statusKey";
    private static final String STATUS_VALUE = "statusValue";
    private static final String AVAILABLE_BIKES = "availableBikes";
    private static final String ADDRESS = "stAddress1";


    public static ArrayList<MTAMainScreenModel> parseMTAMainScreenJSON(String jsonString) {
        ArrayList<MTAMainScreenModel> mainScreenModels = new ArrayList<>();
        Log.d(TAG, "parseMTAMainScreenJSON");
        try {
            Log.d(TAG, "in not null of try");
            JSONArray mainArray = new JSONArray(jsonString);
            for (int i = 0; i < mainArray.length(); i++) {
                JSONObject stopsObject = mainArray.getJSONObject(i);
                MTAMainScreenModel mtaMainScreenModel = new MTAMainScreenModel();
                mtaMainScreenModel.setDistance(stopsObject.getDouble(DISTANCE));
                mtaMainScreenModel.setStopId(stopsObject.getString(STOP_ID));
                mtaMainScreenModel.setStopLatitude(stopsObject.getDouble(STOP_LAT));
                mtaMainScreenModel.setStopLongitude(stopsObject.getDouble(STOP_LON));
                mtaMainScreenModel.setStopName(stopsObject.getString(STOP_NAME));
                JSONArray routesArray = stopsObject.getJSONArray(ROUTES);
                HashMap<String, String> routeName = new HashMap<>();
                HashMap<String, String> routeColor = new HashMap<>();
                ArrayList<String> routeId = new ArrayList<>();
                for (int j = 0; j < routesArray.length(); j++) {
                    JSONObject routeObject = routesArray.getJSONObject(j);
                    routeId.add(routeObject.getString(ROUTE_ID));
                    routeName.put(routeId.get(j), routeObject.getString(ROUTE_NAME));
                    routeColor.put(routeId.get(j), routeObject.getString(ROUTE_COLOR));

                }
                mtaMainScreenModel.setRouteId(routeId);
                mtaMainScreenModel.setRouteName(routeName);
                mtaMainScreenModel.setColorCode(routeColor);
                mainScreenModels.add(mtaMainScreenModel);
            }
            return mainScreenModels;
        } catch (JSONException e) {
            Log.d(TAG, "in null of catch");
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<CitiBikeMainScreenModel> parseCitiBikeMainScreen(String jsonString) {

        ArrayList<CitiBikeMainScreenModel> citiBikeMainScreenModelArrayList = new ArrayList<>();
        try {
            JSONArray bikeArray = new JSONArray(jsonString);
            for (int i = 0; i < bikeArray.length(); i++) {
                CitiBikeMainScreenModel citiBikeMainScreenModel = new CitiBikeMainScreenModel();
                JSONObject bikeObject = bikeArray.getJSONObject(i);
                citiBikeMainScreenModel.setDistance(bikeObject.getDouble(DISTANCE));
                citiBikeMainScreenModel.setId(bikeObject.getInt(ID));
                citiBikeMainScreenModel.setAvailableBikes(bikeObject.getInt(AVAILABLE_BIKES));
                citiBikeMainScreenModel.setAvailableDocks(bikeObject.getInt(AVAILABLE_DOCKS));
                citiBikeMainScreenModel.setLatitude(bikeObject.getDouble(LATITUDE));
                citiBikeMainScreenModel.setLongitude(bikeObject.getDouble(LONGITUDE));
                citiBikeMainScreenModel.setStAddress1(bikeObject.getString(ADDRESS));
                citiBikeMainScreenModel.setStationName(bikeObject.getString(STATION_NAME));
                citiBikeMainScreenModel.setStatusKey(bikeObject.getInt(STATUS_KEY));
                citiBikeMainScreenModel.setStatusValue(bikeObject.getString(STATUS_VALUE));
                citiBikeMainScreenModel.setTotalDocks(bikeObject.getInt(TOTAL_DOCKS));
                citiBikeMainScreenModelArrayList.add(citiBikeMainScreenModel);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
