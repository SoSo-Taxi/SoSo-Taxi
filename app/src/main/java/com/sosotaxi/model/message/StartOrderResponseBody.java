/**
 * @Author 范承祥
 * @CreateTime 2020/7/26
 * @UpdateTime 2020/7/26
 */
package com.sosotaxi.model.message;

import com.google.gson.annotations.SerializedName;

/**
 * 开始订单响应主体
 */
public class StartOrderResponseBody extends BaseBody {
    /**
     * 订单ID
     */
    public long orderId;

    /**
     * 状态码
     */
    public int statusCode;

    /**
     * 消息
     */
    @SerializedName("msg")
    public String message;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

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
}
