package com.example.krishnateja.bigapplesearch.models;

/**
 * Created by krishnateja on 4/29/2015.
 */
public class AppConstants {
    public static class ServerVariables {
        public static final String SCHEME = "http";
        public static final String AUTHORITY = "52.4.108.84";
        public static final String PATH = "bigapple";
        public static final String FILE = "bigapple.php";
        public static final String METHOD = "GET";
        public static final String GETMTAVARIABLE = "MTA";
        public static final String GETCITIVARIABLE = "citi";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String OFFSET="offset";
    }

    public static class IntentExtras {
        public static final String MTA = "mta_data_array_list";
        public static final String CITI = "citi_data_array_list";
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
        public static final int SHOW=0;
        public static final int DISTANCE=1;
        public static final int RATING=2;
        public static final int CUISINE=3;
        public static final int PRICE=4;
        public static final int NEARBY_LEFT=0;
        public static final int MTA_LEFT=2;
        public static final int CITI_LEFT=3;
        public static final int RESTAURANTS_LEFT=5;


    }
}
