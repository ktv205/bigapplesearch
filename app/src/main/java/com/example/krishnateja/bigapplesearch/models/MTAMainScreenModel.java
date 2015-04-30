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
    private String stopId;
    private ArrayList<String> routeId=new ArrayList<>();
    private double stopLatitude, stopLongitude;
    private HashMap<String, String> colorCode=new HashMap<>();
    private HashMap<String, String> routeName=new HashMap<>();
    private double distance;

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
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
        dest.writeString(stopId);
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
        stopId = in.readString();
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

    }


}

