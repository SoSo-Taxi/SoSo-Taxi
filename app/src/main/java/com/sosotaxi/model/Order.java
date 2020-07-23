package com.sosotaxi.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @Author 范承祥
 * @CreateTime 2020/7/21
 * @UpdateTime 2020/7/21
 */
public class Order {

    /** 订单Id */
    private Long orderId;

    /** 城市 */
    private String city;

    /** 出发地 */
    private LocationPoint departPoint;

    /** 目的地 */
    private LocationPoint destPoint;

    /** 订单创建时间 */
    private Date createTime;

    /** 用户预约的时间 */
    private Date appointedTime;

    /** 出发时间 */
    private Date departTime;

    /** 到达时间 */
    private Date arriveTime;

    /** 起点名称 */
    private String departName;

    /** 目的地名称 */
    @SerializedName("destName")
    private String destinationName;

    /** 服务类型，经济型、舒适型等 */
    private Short serviceType;

    /** 原始费用 */
    private Double cost;

    /** 积分减免 */
    private Double pointDiscount;

    /** 优惠券减免 */
    private Double couponDiscount;

    /** 订单状态 */
    private int status;

    /** 订单中，司机获得的评分 */
    private Double driverRate;

    /** 订单中，乘客获得的评分 */
    private Double passengerRate;

    /** 订单路程 */
    private Double distance;

    /** 司机Id */
    private Long driverId;

    /** 乘客Id */
    private Long passengerId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocationPoint getDepartPoint() {
        return departPoint;
    }

    public void setDepartPoint(LocationPoint departPoint) {
        this.departPoint = departPoint;
    }

    public LocationPoint getDestPoint() {
        return destPoint;
    }

    public void setDestPoint(LocationPoint destPoint) {
        this.destPoint = destPoint;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAppointedTime() {
        return appointedTime;
    }

    public void setAppointedTime(Date appointedTime) {
        this.appointedTime = appointedTime;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Short getServiceType() {
        return serviceType;
    }

    public void setServiceType(Short serviceType) {
        this.serviceType = serviceType;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPointDiscount() {
        return pointDiscount;
    }

    public void setPointDiscount(Double pointDiscount) {
        this.pointDiscount = pointDiscount;
    }

    public Double getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(Double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getDriverRate() {
        return driverRate;
    }

    public void setDriverRate(Double driverRate) {
        this.driverRate = driverRate;
    }

    public Double getPassengerRate() {
        return passengerRate;
    }

    public void setPassengerRate(Double passengerRate) {
        this.passengerRate = passengerRate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }
}
