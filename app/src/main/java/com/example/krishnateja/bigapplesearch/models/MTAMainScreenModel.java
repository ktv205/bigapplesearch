package com.example.krishnateja.bigapplesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by krishnateja on 4/29/2015.
 */
public class MTAMainScreenModel implements Parcelable {

    private String stopName;
    private ArrayList<String> stopId;
    private ArrayList<String> routeId=new ArrayList<>();
    private double stopLatitude, stopLongitude;
    private HashMap<String, String> colorCode=new HashMap<>();
    private HashMap<String, String> routeName=new HashMap<>();
    private double distance;
    private String oneStopId;
    private String oneRouteLongName;
    private String oneRouteShortName;
    private String oneStopName;
    private String oneRouteColor;
    private String oneRouteTextColor;
    private String oneRouteDesc;
    private String oneTripHeadSign;
    private String oneArrivalTime;


    public String getOneStopId() {
        return oneStopId;
    }

    public void setOneStopId(String oneStopId) {
        this.oneStopId = oneStopId;
    }

    public String getOneRouteLongName() {
        return oneRouteLongName;
    }

    public void setOneRouteLongName(String oneRouteLongName) {
        this.oneRouteLongName = oneRouteLongName;
    }

    public String getOneRouteShortName() {
        return oneRouteShortName;
    }

    public void setOneRouteShortName(String oneRouteShortName) {
        this.oneRouteShortName = oneRouteShortName;
    }

    public String getOneStopName() {
        return oneStopName;
    }

    public void setOneStopName(String oneStopName) {
        this.oneStopName = oneStopName;
    }

    public String getOneRouteColor() {
        return oneRouteColor;
    }

    public void setOneRouteColor(String oneRouteColor) {
        this.oneRouteColor = oneRouteColor;
    }

    public String getOneRouteTextColor() {
        return oneRouteTextColor;
    }

    public void setOneRouteTextColor(String oneRouteTextColor) {
        this.oneRouteTextColor = oneRouteTextColor;
    }

    public String getOneRouteDesc() {
        return oneRouteDesc;
    }

    public void setOneRouteDesc(String oneRouteDesc) {
        this.oneRouteDesc = oneRouteDesc;
    }

    public String getOneTripHeadSign() {
        return oneTripHeadSign;
    }

    public void setOneTripHeadSign(String oneTripHeadSign) {
        this.oneTripHeadSign = oneTripHeadSign;
    }

    public String getOneArrivalTime() {
        return oneArrivalTime;
    }

    public void setOneArrivalTime(String oneArrivalTime) {
        this.oneArrivalTime = oneArrivalTime;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public ArrayList<String> getStopId() {
        return stopId;
    }

    public void setStopId(ArrayList<String> stopId) {
        this.stopId = stopId;
    }

    public ArrayList<String> getRouteId() {
        return routeId;
    }

    public void setRouteId(ArrayList<String> routeId) {
        this.routeId = routeId;
    }

    public double getStopLatitude() {
        return stopLatitude;
    }

    public void setStopLatitude(double stopLatitude) {
        this.stopLatitude = stopLatitude;
    }

    public double getStopLongitude() {
        return stopLongitude;
    }

    public void setStopLongitude(double stopLongitude) {
        this.stopLongitude = stopLongitude;
    }

    public HashMap<String, String> getColorCode() {
        return colorCode;
    }

    public void setColorCode(HashMap<String, String> colorCode) {
        this.colorCode = colorCode;
    }

    public HashMap<String, String> getRouteName() {
        return routeName;
    }

    public void setRouteName(HashMap<String, String> routeName) {
        this.routeName = routeName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public MTAMainScreenModel(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stopName);
        dest.writeSerializable(stopId);
        dest.writeDouble(stopLatitude);
        dest.writeDouble(stopLongitude);
        dest.writeDouble(distance);
        dest.writeSerializable(routeId);
        dest.writeInt(routeName.size());
        for (HashMap.Entry<String, String> entry : routeName.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(colorCode.size());
        for (HashMap.Entry<String, String> entry : colorCode.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(oneStopId);
        dest.writeString(oneStopName);
        dest.writeString(oneRouteShortName);
        dest.writeString(oneRouteLongName);
        dest.writeString(oneRouteDesc);
        dest.writeString(oneRouteColor);
        dest.writeString(oneRouteTextColor);
        dest.writeString(oneArrivalTime);
        dest.writeString(oneTripHeadSign);
    }

    public static final Parcelable.Creator<MTAMainScreenModel> CREATOR = new Parcelable.Creator<MTAMainScreenModel>() {

        @Override
        public MTAMainScreenModel createFromParcel(Parcel source) {
            return new MTAMainScreenModel(source);
        }

        @Override
        public MTAMainScreenModel[] newArray(int size) {
            return new MTAMainScreenModel[size];
        }
    };

    public MTAMainScreenModel(Parcel in) {
        stopName = in.readString();
        stopId = (ArrayList<String>) in.readSerializable();;
        stopLatitude = in.readDouble();
        stopLongitude = in.readDouble();
        distance = in.readDouble();
        routeId = (ArrayList<String>) in.readSerializable();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            routeName.put(key, value);
        }
        size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            colorCode.put(key, value);
        }
        oneStopId=in.readString();
        oneStopName=in.readString();
        oneRouteShortName=in.readString();
        oneRouteLongName=in.readString();
        oneRouteDesc=in.readString();
        oneRouteColor=in.readString();
        oneRouteTextColor=in.readString();
        oneArrivalTime=in.readString();
        oneTripHeadSign=in.readString();


    }


}

