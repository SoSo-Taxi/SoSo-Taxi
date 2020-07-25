package com.sosotaxi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 范承祥
 * @CreateTime 2020/7/21
 * @UpdateTime 2020/7/21
 */
public class Order implements Parcelable {

    /** 订单Id */
    private Long orderId;

    /** 城市 */
    private String city;

    /** 出发地 */
    private LocationPoint departPoint;

    /** 目的地 */
    @SerializedName("destPoint")
    private LocationPoint destinationPoint;

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

    /**
     * 乘客手机号
     */
    private transient String passengerPhoneNumber;

    public Order(){}

    protected Order(Parcel in) {
        if (in.readByte() == 0) {
            orderId = null;
        } else {
            orderId = in.readLong();
        }
        city = in.readString();
        departPoint = in.readParcelable(LocationPoint.class.getClassLoader());
        destinationPoint = in.readParcelable(LocationPoint.class.getClassLoader());
        if(in.readByte()==0){
            createTime=null;
        }else{
            createTime=new Date(in.readLong());
        }
        if(in.readByte()==0){
            appointedTime=null;
        }else{
            appointedTime=new Date(in.readLong());
        }
        if(in.readByte()==0){
            departTime=null;
        }else{
            departTime=new Date(in.readLong());
        }
        if(in.readByte()==0){
            arriveTime=null;
        }else{
            arriveTime=new Date(in.readLong());
        }
        departName = in.readString();
        destinationName = in.readString();
        int tmpServiceType = in.readInt();
        serviceType = tmpServiceType != Integer.MAX_VALUE ? (short) tmpServiceType : null;
        if (in.readByte() == 0) {
            cost = null;
        } else {
            cost = in.readDouble();
        }
        if (in.readByte() == 0) {
            pointDiscount = null;
        } else {
            pointDiscount = in.readDouble();
        }
        if (in.readByte() == 0) {
            couponDiscount = null;
        } else {
            couponDiscount = in.readDouble();
        }
        status = in.readInt();
        if (in.readByte() == 0) {
            driverRate = null;
        } else {
            driverRate = in.readDouble();
        }
        if (in.readByte() == 0) {
            passengerRate = null;
        } else {
            passengerRate = in.readDouble();
        }
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readDouble();
        }
        if (in.readByte() == 0) {
            driverId = null;
        } else {
            driverId = in.readLong();
        }
        if (in.readByte() == 0) {
            passengerId = null;
        } else {
            passengerId = in.readLong();
        }
        passengerPhoneNumber = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getPassengerPhoneNumber() {
        return passengerPhoneNumber;
    }

    public void setPassengerPhoneNumber(String passengerPhoneNumber) {
        this.passengerPhoneNumber = passengerPhoneNumber;
    }

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

    public LocationPoint getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(LocationPoint destPoint) {
        this.destinationPoint = destPoint;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (orderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(orderId);
        }
        dest.writeString(city);
        dest.writeParcelable(departPoint, flags);
        dest.writeParcelable(destinationPoint, flags);
        if(createTime==null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeLong(createTime.getTime());
        }
        if(appointedTime==null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeLong(appointedTime.getTime());
        }
        if(departTime==null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeLong(departTime.getTime());
        }
        if(arriveTime==null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeLong(arriveTime.getTime());
        }
        dest.writeString(departName);
        dest.writeString(destinationName);
        dest.writeInt(serviceType != null ? (int) serviceType : Integer.MAX_VALUE);
        if (cost == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(cost);
        }
        if (pointDiscount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(pointDiscount);
        }
        if (couponDiscount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(couponDiscount);
        }
        dest.writeInt(status);
        if (driverRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(driverRate);
        }
        if (passengerRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(passengerRate);
        }
        if (distance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(distance);
        }
        if (driverId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(driverId);
        }
        if (passengerId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(passengerId);
        }
        dest.writeString(passengerPhoneNumber);

    }
}
