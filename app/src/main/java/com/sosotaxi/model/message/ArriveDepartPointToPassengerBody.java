/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */
package com.sosotaxi.model.message;

import com.google.gson.annotations.SerializedName;

/**
 * 到达上车点乘客收到响应主体
 */
public class ArriveDepartPointToPassengerBody extends BaseBody{
    /**
     * 信息
     */
    @SerializedName("msg")
    private String message;

    /**
     * 状态码
     */
    private int statusCode;

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
