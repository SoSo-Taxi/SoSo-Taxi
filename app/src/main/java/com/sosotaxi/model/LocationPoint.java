/**
 * @Author 范承祥
 * @CreateTime 2020/7/21
 * @UpdateTime 2020/7/21
 */
package com.sosotaxi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.trace.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * 位置
 */
public class LocationPoint implements Parcelable {
    /**
     * 纬度
     */
    @SerializedName("lat")
    private double latitude;

    /**
     * 经度
     */
    @SerializedName("lng")
    private double longitude;

    public LocationPoint(){}

    public LocationPoint(LatLng location){
        this.latitude=location.getLatitude();
        this.longitude=location.getLongitude();
    }

    public LocationPoint(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    protected LocationPoint(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LocationPoint> CREATOR = new Creator<LocationPoint>() {
        @Override
        public LocationPoint createFromParcel(Parcel in) {
            return new LocationPoint(in);
        }

        @Override
        public LocationPoint[] newArray(int size) {
            return new LocationPoint[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
