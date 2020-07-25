/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */
package com.sosotaxi.model.message;

import android.graphics.Point;

import com.google.gson.annotations.SerializedName;
import com.sosotaxi.model.LocationPoint;

/**
 * 查询司机与乘客距离响应主体
 */
public class CheckBondedDriverGeoResponseBody extends BaseBody{
    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 消息
     */
    @SerializedName("msg")
    private String message;

    /**
     * 司机位置
     */
    private LocationPoint point;

    /**
     * 距离（以米为单位）
     */
    private double distance;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocationPoint getPoint() {
        return point;
    }

    public void setPoint(LocationPoint point) {
        this.point = point;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
