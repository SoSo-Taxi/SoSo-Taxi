/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */
package com.sosotaxi.model.message;

import com.sosotaxi.model.LocationPoint;

/**
 * 获取司机与乘客距离请求主体
 */
public class CheckBondedDriverGeoBody extends BaseBody{
    /**
     * 乘客Token
     */
    private String userToken;

    /**
     * 乘客位置
     */
    private LocationPoint point;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public LocationPoint getPoint() {
        return point;
    }

    public void setPoint(LocationPoint geoPoint) {
        this.point = geoPoint;
    }
}
