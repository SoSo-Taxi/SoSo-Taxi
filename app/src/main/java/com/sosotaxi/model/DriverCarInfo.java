package com.sosotaxi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DriverCarInfo implements Parcelable {


    /** 司机id */
    private long driverId;

    /** 司机姓名 */
    private String driverName;

    /* tel */
    private String phoneNumber;

    /* 司机头像 */
    private String avatarpath;


    /** 车品牌 */

    private String carBrand;

    /** 车型 */
    private String carModel;

    /** 车辆颜色 */
    private String carColor;

    /** 车牌号码 */
    private String licensePlate;

    /* service type */
    private short serviceType;

    /* rate */
    private double rate;

    /* orderNum */
    private long orderNum;

    public DriverCarInfo(){}

    protected DriverCarInfo(Parcel in) {
        driverId = in.readLong();
        driverName = in.readString();
        phoneNumber = in.readString();
        avatarpath = in.readString();
        carBrand = in.readString();
        carModel = in.readString();
        carColor = in.readString();
        licensePlate = in.readString();
        serviceType = (short) in.readInt();
        rate = in.readDouble();
        orderNum = in.readLong();
    }

    public static final Creator<DriverCarInfo> CREATOR = new Creator<DriverCarInfo>() {
        @Override
        public DriverCarInfo createFromParcel(Parcel in) {
            return new DriverCarInfo(in);
        }

        @Override
        public DriverCarInfo[] newArray(int size) {
            return new DriverCarInfo[size];
        }
    };

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarpath() {
        return avatarpath;
    }

    public void setAvatarpath(String avatarpath) {
        this.avatarpath = avatarpath;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public short getServiceType() {
        return serviceType;
    }

    public void setServiceType(short serviceType) {
        this.serviceType = serviceType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(driverId);
        dest.writeString(driverName);
        dest.writeString(phoneNumber);
        dest.writeString(avatarpath);
        dest.writeString(carBrand);
        dest.writeString(carModel);
        dest.writeString(carColor);
        dest.writeString(licensePlate);
        dest.writeInt((int) serviceType);
        dest.writeDouble(rate);
        dest.writeLong(orderNum);
    }
}
