/**
 * @Author 范承祥
 * @CreateTime 2020/7/21
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.model;

/**
 * 订单状态
 */
public enum OrderStatus {
    /**
     * 未开始 0
     */
    NOT_START,

    /**
     * 已接单 1
     */
    RECEIVE,

    /**
     * 到达上车点 2
     */
    ARRIVE_STARTING_POINT,

    /**
     * 接到乘客 3
     */
    PICK_UP_PASSENGER,

    /**
     * 到达目的地 4
     */
    ARRIVE_DESTINATION,

    /**
     * 已支付 5
     */
    PAID;

    @Override
    public String toString(){
        return String.valueOf(this.ordinal());
    }
}
