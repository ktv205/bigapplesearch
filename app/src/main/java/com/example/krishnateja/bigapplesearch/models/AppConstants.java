package com.example.krishnateja.bigapplesearch.models;

/**
 * Created by krishnateja on 4/29/2015.
 */
public class AppConstants {
    public static class ServerVariables {
        public static final String SCHEME = "http";
        public static final String AUTHORITY = "52.25.215.16";
        public static final String PATH = "NYCSubwayAndBike";
        public static final String FILE = "bigapple.php";
        public static final String METHOD = "GET";
        public static final String GETMTAVARIABLE = "MTA";
        public static final String GETCITIVARIABLE = "citi";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String OFFSET = "offset";
        public static final String RES_AUTHORITY="52.33.173.139";
        public static final String LOCATION_PATH="location";
        public static final String ALERTS_FILE="mta_alerts.php";
        public static final String FIND_ROUTE_FILE="find_route.php";
        public static final String TO_ADD_GET_VARIABLE="to";
        public static final String FROM_ADD_GET_VARIABLE="from";

    }

    public static class IntentExtras {
        public static final String MTA = "mta_data_array_list";
        public static final String CITI = "citi_data_array_list";
        public static final String RES = "res_data_array_list";
        public static final String MTA_STOPS="mta_stop_ids";
        public static final String MTA_ROUTES="mta_routes_id";
    }

    public static class BundleExtras{
        public static final String LEFT_SELECTED="left_selected";
        public static final String FILTERS_SELECTED="filters_selected";
        public static final String MAIN_FRAGMENT="main_fragment";

        public static final String VIOLATION_CODES ="violation_codes" ;
        public static final String VIOLATION_SCORE="violation_score";
        public static final String FROM_ADDRESS = "from_address";
        public static final String TO_ADDRESS = "to_address";
    }


    public static class InAppConstants {
        public static final String MTA = "MTA New York City Transit";
        public static final String CITI = "CITI Bikes";
        public static final String RESTAURANTS = "Restaurants";
        public static final String MTAURL = "mta.info";
        public static final String MTAPHONE = "718-330-1234";
        public static final String CITIURL = "citibikenyc.com";
        public static final String CITIPHONE = "1-855-2453-311";
        public static final String CITIEMAIL = "customerservice@citibikenyc.com";
        public static final int MTA_CODE = 1;
        public static final int CITI_CODE = 2;
        public static final int RESTAURANT_CODE = 3;
        public static final int MTA_ACTIVITY_CODE=4;
        public static final int SHOW_ALL = 0;
        public static final int SHOW_MTA = 1;
        public static final int SHOW_CITI = 2;
        public static final int SHOW_RES = 3;
        public static final int DISTANCE_INCREASING = 0;
        public static final int DISTANCE_DECREASING = 1;
        public static final int RATING_ALL = 0;
        public static final int RATING_2 = 1;
        public static final int RATING_3 = 2;
        public static final int RATING_4 = 3;
        public static final int CUISINE_ALL = 0;
        public static final int CUISINE_CHINESE = 1;
        public static final int CUISINE_INDIAN = 2;
        public static final int CUISINE_AMERICAN = 3;
        public static final int CUISINE_OTHERS = 4;
        public static final int PRICE_ALL = 0;
        public static final int PRICE_1 = 1;
        public static final int PRICE_2 = 2;
        public static final int PRICE_3 = 3;
        public static final int PRICE_4 = 4;
        public static final int SHOW = 0;
        public static final int DISTANCE = 1;
        public static final int RATING = 2;
        public static final int CUISINE = 3;
        public static final int PRICE = 4;
        public static final int NEARBY_LEFT = 0;
        public static final int MTA_LEFT = 2;
        public static final int CITI_LEFT = 3;
        public static final int RESTAURANTS_LEFT = 5;
        public static final int MORE_CODE = 4;
        public static final String DISTANCE_TEXT = "Distance";
        public static final String SHOW_TEXT = "show";
        public static final String RATING_TEXT = "Rating";
        public static final String PRICE_TEXT = "Price";
        public static final String CUISINE_TEXT = "Cuisine";
        public static final int ALERTS_CODE = 5;
        public static final int FIND_ROUTE_CODE =6 ;
    }

    public final class Constants {
        public static final int SUCCESS_RESULT = 0;
        public static final int FAILURE_RESULT = 1;
        public static final String PACKAGE_NAME =
                "com.google.android.gms.location.sample.locationaddress";
        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        public static final String RESULT_DATA_KEY = PACKAGE_NAME +
                ".RESULT_DATA_KEY";
        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
                ".LOCATION_DATA_EXTRA";
    }
}
