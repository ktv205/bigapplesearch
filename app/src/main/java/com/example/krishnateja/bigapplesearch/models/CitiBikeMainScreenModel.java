package com.example.krishnateja.bigapplesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krishnateja on 4/30/2015.
 */
public class CitiBikeMainScreenModel implements Parcelable {
    private int id;
    private String stationName;
    private int totalDocks;
    private double latitude, longitude;
    private String statusValue;
    private int statusKey, availableBikes;
    private String stAddress1;
    private double distance;
    private int availableDocks;


    public int getAvailableDocks() {
        return availableDocks;
    }

    public void setAvailableDocks(int availableDocks) {
        this.availableDocks = availableDocks;
    }

    public CitiBikeMainScreenModel() {

    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getTotalDocks() {
        return totalDocks;
    }

    public void setTotalDocks(int totalDocks) {
        this.totalDocks = totalDocks;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(int statusKey) {
        this.statusKey = statusKey;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
    }

    public String getStAddress1() {
        return stAddress1;
    }

    public void setStAddress1(String stAddress1) {
        this.stAddress1 = stAddress1;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(stationName);
        dest.writeInt(totalDocks);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(statusValue);
        dest.writeInt(statusKey);
        dest.writeInt(availableBikes);
        dest.writeString(stAddress1);
        dest.writeDouble(distance);
        dest.writeInt(availableDocks);
    }

    public static final Parcelable.Creator<CitiBikeMainScreenModel> CREATOR = new Parcelable.Creator<CitiBikeMainScreenModel>() {

        @Override
        public CitiBikeMainScreenModel createFromParcel(Parcel source) {
            return new CitiBikeMainScreenModel(source);
        }

        @Override
        public CitiBikeMainScreenModel[] newArray(int size) {
            return new CitiBikeMainScreenModel[size];
        }
    };

    public CitiBikeMainScreenModel(Parcel in) {
        id = in.readInt();
        stationName = in.readString();
        totalDocks = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        statusValue = in.readString();
        statusKey = in.readInt();
        availableBikes = in.readInt();
        stAddress1 = in.readString();
        distance = in.readDouble();
        availableDocks=in.readInt();
    }
}
