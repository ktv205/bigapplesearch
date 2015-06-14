package com.example.krishnateja.bigapplesearch.utils;

import android.util.Log;

import com.example.krishnateja.bigapplesearch.models.CitiBikeMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.MTAMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.RestaurantMainScreenModel;
import com.example.krishnateja.bigapplesearch.models.SubwayAlertsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
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
    public static final String RESULTS = "results";
    public static final String CATEGORIES = "categories";
    public static final String DISPLAY_PHONE = "display_phone";
    public static final String IMAGE = "image_url";
    public static final String CLOSED = "is_closed";
    public static final String LOCATION = "location";
    public static final String address = "address";
    public static final String CITY = "city";
    public static final String COORDINATE = "coordinate";
    public static final String DISPLAY_ADDRESS = "display_address";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String RATING = "rating";
    public static final String URL = "url";
    public static final String VIOLATION = "violation_codes";
    public static final String VIOLATION_SCORE = "violation_score";
    public static final String DIRECTION_ID = "direction_id";
    public static final String ROUTE_LONG_NAME = "route_long_name";
    public static final String ROUTE_SHORT_NAME = "route_short_name";
    public static final String ROUTE_TEXT_COLOR = "route_text_color";
    public static final String ROUTE_DESC = "route_desc";
    public static final String TRIP_HEADSIGN = "trip_headsign";
    public static final String ARRIVAL_TIME = "arrival_time";
    public static final String LINE_NAME = "line";
    public static final String LINE_STATUS = "status";
    public static final String LINE_TEXT = "text";
    public static final String LINE_DATE = "date";
    public static final String LINE_TIME = "time";


    public static ArrayList<MTAMainScreenModel> parseMTAMainScreenJSON(String jsonString) {
        ArrayList<MTAMainScreenModel> mainScreenModels = new ArrayList<>();

        try {

            JSONArray mainArray = new JSONArray(jsonString);
            for (int i = 0; i < mainArray.length(); i++) {
                JSONObject stopsObject = mainArray.getJSONObject(i);
                MTAMainScreenModel mtaMainScreenModel = new MTAMainScreenModel();
                mtaMainScreenModel.setDistance(stopsObject.getDouble(DISTANCE));
                ArrayList<String> stopIds = new ArrayList<>();
                JSONArray stopIdArray = stopsObject.getJSONArray(STOP_ID);
                for (int j = 0; j < stopIdArray.length(); j++) {
                    stopIds.add(stopIdArray.getJSONObject(j).getString(STOP_ID));
                }
                mtaMainScreenModel.setStopId(stopIds);
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
            return citiBikeMainScreenModelArrayList;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<RestaurantMainScreenModel> parseRestaurantMainScreen(String jsonString) {
        ArrayList<RestaurantMainScreenModel> restaurantMainScreenModelArrayList = new ArrayList<>();
        try {
            JSONObject mainObject = new JSONObject(jsonString);
            JSONArray resultsArray = mainObject.getJSONArray(RESULTS);
            for (int i = 0; i < resultsArray.length(); i++) {
                RestaurantMainScreenModel restaurantMainScreenModel = new RestaurantMainScreenModel();
                JSONObject resultObject = resultsArray.getJSONObject(i);
                JSONArray catArray = resultObject.getJSONArray(CATEGORIES);
                ArrayList<String> cat = new ArrayList<>();
                for (int j = 0; j < catArray.length(); j++) {
                    JSONArray innercatArray = catArray.getJSONArray(j);
                    cat.add(innercatArray.getString(0));
                }
                restaurantMainScreenModel.setCategories(cat);
                restaurantMainScreenModel.setPhone(resultObject.getString(DISPLAY_PHONE));
                JSONObject locationObject = resultObject.getJSONObject(LOCATION);
                restaurantMainScreenModel.setAddress(locationObject.getJSONArray(DISPLAY_ADDRESS).get(0).toString());

                JSONObject coordObject = locationObject.getJSONObject(COORDINATE);
                restaurantMainScreenModel.setLat(coordObject.getDouble(LATITUDE));
                restaurantMainScreenModel.setLng(coordObject.getDouble(LONGITUDE));
                restaurantMainScreenModel.setDistance(resultObject.getDouble(DISTANCE));
                restaurantMainScreenModel.setResName(resultObject.getString(NAME));
                restaurantMainScreenModel.setUrl(resultObject.getString(URL));
                restaurantMainScreenModel.setRating(resultObject.getDouble(RATING));
                JSONArray violationJsonArray = resultObject.getJSONArray(VIOLATION);
                ArrayList<String> violation = new ArrayList<>();
                for (int j = 0; j < violationJsonArray.length(); j++) {
                    violation.add(violationJsonArray.getString(j));

                }
                restaurantMainScreenModel.setViolationScore(resultObject.getInt(VIOLATION_SCORE));
                restaurantMainScreenModel.setCategories(cat);
                restaurantMainScreenModel.setViolationCodes(violation);
                restaurantMainScreenModel.setIsClosed(resultObject.getBoolean(CLOSED));
                restaurantMainScreenModelArrayList.add(restaurantMainScreenModel);


            }
            return restaurantMainScreenModelArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static ArrayList<MTAMainScreenModel> parseMTAActivityData(String s) {
        ArrayList<MTAMainScreenModel> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MTAMainScreenModel mtaMainScreenModel = new MTAMainScreenModel();
                mtaMainScreenModel.setOneArrivalTime(jsonObject.getString(ARRIVAL_TIME));
                mtaMainScreenModel.setOneRouteColor(jsonObject.getString(ROUTE_COLOR));
                mtaMainScreenModel.setOneStopName(jsonObject.getString(STOP_NAME));
                mtaMainScreenModel.setOneRouteDesc(jsonObject.getString(ROUTE_DESC));
                mtaMainScreenModel.setOneRouteLongName(jsonObject.getString(ROUTE_LONG_NAME));
                mtaMainScreenModel.setOneRouteShortName(jsonObject.getString(ROUTE_SHORT_NAME));
                mtaMainScreenModel.setOneRouteTextColor(jsonObject.getString(ROUTE_TEXT_COLOR));
                mtaMainScreenModel.setOneStopId(jsonObject.getString(STOP_ID));
                mtaMainScreenModel.setOneTripHeadSign(jsonObject.getString(TRIP_HEADSIGN));
                arrayList.add(mtaMainScreenModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<SubwayAlertsModel> parseAlerts(String s) {
        ArrayList<SubwayAlertsModel> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                SubwayAlertsModel subwayAlertsModel = new SubwayAlertsModel();
                subwayAlertsModel.setLineName(object.getString(LINE_NAME));
                subwayAlertsModel.setLineText(object.getString(LINE_TEXT));
                subwayAlertsModel.setLineDate(object.getString(LINE_DATE));
                subwayAlertsModel.setLineStatus(object.getString(LINE_STATUS));
                subwayAlertsModel.setLineTime(object.getString(LINE_TIME));
                arrayList.add(subwayAlertsModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;


    }

}
