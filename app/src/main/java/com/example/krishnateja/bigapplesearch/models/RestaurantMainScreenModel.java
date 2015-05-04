package com.example.krishnateja.bigapplesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Student on 5/3/2015.
 */
public class RestaurantMainScreenModel implements Parcelable {
    String resName;
    String address;
    double lat;
    double lng;
    double distance;
    String phone;
    boolean isClosed;
    double rating;
    ArrayList<String> categories;
    ArrayList<String> violationCodes;
    String url;
    int violationScore;

    public int getViolationScore() {
        return violationScore;
    }

    public void setViolationScore(int violationScore) {
        this.violationScore = violationScore;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getViolationCodes() {
        return violationCodes;
    }

    public void setViolationCodes(ArrayList<String> violationCodes) {
        this.violationCodes = violationCodes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RestaurantMainScreenModel(Parcel source) {
        resName=source.readString();
        address=source.readString();
        lat=source.readDouble();
        lng=source.readDouble();
        distance=source.readDouble();
        phone=source.readString();
        isClosed=source.readByte()!=0;
        rating=source.readDouble();
        categories=(ArrayList<String>)source.readSerializable();
        violationCodes=(ArrayList<String>)source.readSerializable();
        url=source.readString();
        violationScore=source.readInt();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<RestaurantMainScreenModel> CREATOR = new Parcelable.Creator<RestaurantMainScreenModel>() {

        @Override
        public RestaurantMainScreenModel createFromParcel(Parcel source) {
            return new RestaurantMainScreenModel(source);
        }

        @Override
        public RestaurantMainScreenModel[] newArray(int size) {
            return new RestaurantMainScreenModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resName);
        dest.writeString(address);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeDouble(distance);
        dest.writeString(phone);
        dest.writeByte((byte) (isClosed ? 1 : 0));
        dest.writeDouble(rating);
        dest.writeSerializable(categories);
        dest.writeSerializable(violationCodes);
        dest.writeString(url);
        dest.writeInt(violationScore);

    }

    public RestaurantMainScreenModel(){

    }
}