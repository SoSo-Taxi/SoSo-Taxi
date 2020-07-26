/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */
package com.sosotaxi.model.message;

/**
 * 消息类型
 */
public enum MessageType {
    /**
     * 鉴权请求
     */
    AUTH_REQUEST("AUTH_REQUEST"),

    /**
     * 鉴权响应
     */
    AUTH_RESPONSE("AUTH_RESPONSE"),

    /**
     * 错误响应
     */
    ERROR_RESPONSE("ERROR_RESPONSE"),

    /**
     * 司机更新自身
     */
    UPDATE_REQUEST("UPDATE_REQUEST"),

    /**
     * 司机收到回应
     */
    UPDATE_RESPONSE("UPDATE_RESPONSE"),

    /**
     * 乘客发送打车
     */
    START_ORDER_MESSAGE("START_ORDER_MESSAGE"),

    /**
     * 乘客打车响应
     */
    START_ORDER_RESPONSE("START_ORDER_RESPONSE"),

    /**
     * 请求打车
     */
    ASK_FOR_DRIVER_MESSAGE("ASK_FOR_DRIVER_MESSAGE"),

    /**
     * 获取所有司机位置
     */
    DRIVER_ANSWER_ORDER_MESSAGE("DRIVER_ANSWER_ORDER_MESSAGE"),

    /**
     * 乘客收到订单结果
     */
    ORDER_RESULT_MESSAGE("ORDER_RESULT_MESSAGE"),

    /**
     * 获取所有司机位置
     */
    GET_ALL_DRIVER_MESSAGE("GET_ALL_DRIVER_MESSAGE"),

    /**
     * 获取所有司机位置响应
     */
    GET_ALL_DRIVER_RESPONSE("GET_ALL_DRIVER_RESPONSE"),

    /**
     * 获取订单司机与乘客距离
     */
    CHECK_BONDED_DRIVER_GEO_MESSAGE("CHECK_BONDED_DRIVER_GEO_MESSAGE"),

    /**
     * 获取司机与乘客距离响应
     */
    CHECK_BONDED_DRIVER_GEO_RESPONSE("CHECK_BONDED_DRIVER_GEO_RESPONSE"),

    /**
     * 查询接单司机
     */
    DISPATCH_DRIVER_MESSAGE("DISPATCH_DRIVER_MESSAGE"),

    /**
     * 司机到达上车点
     */
    ARRIVE_DEPART_POINT_MESSAGE("ARRIVE_DEPART_POINT_MESSAGE"),

    /**
     * 司机到达上车点响应
     */
    ARRIVE_DEPART_POINT_RESPONSE("ARRIVE_DEPART_POINT_RESPONSE"),

    /**
     * 司机已到达上车点乘客响应
     */
    ARRIVE_DEPART_POINT_TO_MESSAGE("ARRIVE_DEPART_POINT_TO_MESSAGE"),

    /**
     * 司机接到乘客
     */
    PICK_UP_PASSENGER_MESSAGE("PICK_UP_PASSENGER_MESSAGE"),

    /**
     * 接到乘客
     */
    PICK_UP_PASSENGER_MESSAGE_RESPONSE("PICK_UP_PASSENGER_MESSAGE_RESPONSE"),

    /**
     * 接到乘客响应
     */
    PICK_UP_PASSENGER_MESSAGE_TO_PASSENGER("PICK_UP_PASSENGER_MESSAGE_TO_PASSENGER"),

    /**
     * 到达目的地
     */
    ARRIVE_DEST_POINT_MESSAGE("ARRIVE_DEST_POINT_MESSAGE"),

    /**
     * 到达目的地乘客响应
     */
    ARRIVE_DEST_POINT_MESSAGE_TO_PASSENGER("ARRIVE_DEST_POINT_MESSAGE_TO_PASSENGER"),

    /**
     * 完成订单
     */
    FINISH_ORDER_REQUEST("FINISH_ORDER_REQUEST"),

    /**
     * 完成订单响应
     */
    FINISH_ORDER_RESPONSE("FINISH_ORDER_RESPONSE");


    /**
     * 别名
     */
    private String mName;

    MessageType(String name){
        mName=name;
    }

    @Override
    public String toString(){
        return mName;
    }
}
