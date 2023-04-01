package com.example.greenleef;

import android.os.Parcel;
import android.os.Parcelable;

public class DataLocation implements Parcelable {

    protected DataLocation(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<DataLocation> CREATOR = new Creator<DataLocation>() {
        @Override
        public DataLocation createFromParcel(Parcel in) {
            return new DataLocation(in);
        }

        @Override
        public DataLocation[] newArray(int size) {
            return new DataLocation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    private double lat;
    private double lng;

    public DataLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
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
}
