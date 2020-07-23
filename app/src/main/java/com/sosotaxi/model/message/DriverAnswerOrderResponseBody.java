/**
 * @Author 范承祥
 * @CreateTime 2020/7/23
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.model.message;

import com.google.gson.annotations.SerializedName;
import com.sosotaxi.model.Driver;
import com.sosotaxi.model.Order;

/**
 * 司机响应订单响应主体
 */
public class DriverAnswerOrderResponseBody extends BaseBody{
    /**
     * 司机
     */
    private Driver driver;

    /**
     * 消息
     */
    @SerializedName("msg")
    private String message;

    /**
     * 状体码
     */
    private int statusCode;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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

