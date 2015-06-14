package com.example.krishnateja.bigapplesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krishnateja on 5/25/2015.
 */
public class SubwayAlertsModel implements Parcelable {

    String lineName, lineStatus, lineText, lineDate, lineTime;

    public SubwayAlertsModel(Parcel source) {
        lineName = source.readString();
        lineStatus = source.readString();
        lineText = source.readString();
        lineDate = source.readString();
        lineTime = source.readString();
    }

    public SubwayAlertsModel() {

    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(String lineStatus) {
        this.lineStatus = lineStatus;
    }

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public String getLineDate() {
        return lineDate;
    }

    public void setLineDate(String lineDate) {
        this.lineDate = lineDate;
    }

    public String getLineTime() {
        return lineTime;
    }

    public void setLineTime(String lineTime) {
        this.lineTime = lineTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lineName);
        dest.writeString(lineStatus);
        dest.writeString(lineText);
        dest.writeString(lineDate);
        dest.writeString(lineTime);
    }

    public static final Parcelable.Creator<SubwayAlertsModel> CREATOR = new Parcelable.Creator<SubwayAlertsModel>() {

        @Override
        public SubwayAlertsModel createFromParcel(Parcel source) {
            return new SubwayAlertsModel(source);
        }

        @Override
        public SubwayAlertsModel[] newArray(int size) {
            return new SubwayAlertsModel[size];
        }
    };

}
