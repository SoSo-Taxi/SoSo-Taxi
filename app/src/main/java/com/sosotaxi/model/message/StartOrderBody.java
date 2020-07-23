/**
 * @Author 范承祥
 * @CreateTime 2020/7/23
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.model.message;

import com.google.gson.annotations.SerializedName;
import com.sosotaxi.model.LocationPoint;
import com.sosotaxi.model.message.BaseBody;

/**
 * 开始订单请求主体
 */
public class StartOrderBody extends BaseBody {

    /**
     * 用户ID
     */
    private Long passengerId;

    /**
     * 用户手机号
     */
    private String phoneNumber;

    /**
     * 用户Token
     */
    private String userToken;

    /**
     * 城市
     */
    private String city;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 上车点
     */
    private LocationPoint departPoint;

    /**
     * 目的地
     */
    @SerializedName("destPoint")
    private LocationPoint destinationPoint;

    /**
     * 服务类型
     */
    private Short serviceType;

    /**
     * 乘客数量
     */
    private Short passengerNum;

    /**
     * 上车点名
     */
    private String departName;

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocationPoint getDepartPoint() {
        return departPoint;
    }

    public void setDepartPoint(LocationPoint departPoint) {
        this.departPoint = departPoint;
    }

    public LocationPoint getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(LocationPoint destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public Short getServiceType() {
        return serviceType;
    }

    public void setServiceType(Short serviceType) {
        this.serviceType = serviceType;
    }

    public Short getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(Short passengerNum) {
        this.passengerNum = passengerNum;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}
