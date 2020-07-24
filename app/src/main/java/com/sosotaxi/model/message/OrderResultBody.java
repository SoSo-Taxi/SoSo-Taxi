package com.sosotaxi.model.message;

import com.google.gson.annotations.SerializedName;
import com.sosotaxi.model.Driver;
import com.sosotaxi.model.DriverCarInfo;

public class OrderResultBody extends BaseBody {
    /**
     * carinfo
     */
    private DriverCarInfo driverCarInfo;

    /**
     * 消息
     */
    @SerializedName("msg")
    private String message;

    /**
     * 状体码
     */
    private int statusCode;

    public DriverCarInfo getDriverCarInfo() {
        return driverCarInfo;
    }

    public void setDriverCarInfo(DriverCarInfo driverCarInfo) {
        this.driverCarInfo = driverCarInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
