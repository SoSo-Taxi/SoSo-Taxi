/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */
package com.sosotaxi.model.message;

import com.sosotaxi.model.Order;

/**
 * 到达目的地乘客响应主体
 */
public class ArriveDestPointMessageToPassengerBody extends BaseBody {

    /**
     * 订单
     */
    private Order order;

    /**
     * 基础费用
     */
    private Double basicCost;

    /**
     * 过路费
     */
    private Double freewayCost;

    /**
     * 停车费
     */
    private Double parkingCost;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getBasicCost() {
        return basicCost;
    }

    public void setBasicCost(Double basicCost) {
        this.basicCost = basicCost;
    }

    public Double getFreewayCost() {
        return freewayCost;
    }

    public void setFreewayCost(Double freewayCost) {
        this.freewayCost = freewayCost;
    }

    public Double getParkingCost() {
        return parkingCost;
    }

    public void setParkingCost(Double parkingCost) {
        this.parkingCost = parkingCost;
    }
}
