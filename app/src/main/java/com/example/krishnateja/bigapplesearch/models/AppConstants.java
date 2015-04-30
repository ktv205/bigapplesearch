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
        public static final String GETCITIVARIABLE="citi";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
    }

    public static class IntentExtras {
        public static final String MTA = "mta_data_array_list";
        public static final String CITI="citi_data_array_list";
    }

    public static class InAppConstants {
        public static final String MTA = "MTA New York City Transit";
        public static final String CITY = "CITY Bikes";
        public static final String RESTAURANTS = "Restaurants";
        public static final String MTAURL = "http://www.mta.info";
        public static final String MTAPHONE = "718-330-1234";
        public static final String CITYURL = "https://www.citibikenyc.com/";
        public static final String CITYPHONE = "1-855-2453-311";
        public static final String CITYEMAIL = "customerservice@citibikenyc.com";
        public static final int MTA_CODE = 1;
        public static final int CITI_CODE = 2;
        public static final int RESTAURANT_CODE = 3;
    }
}
