/**
 * @Author 范承祥
 * @CreateTime 2020/7/25
 * @UpdateTime 2020/7/25
 */
package com.sosotaxi.service.net;

import android.os.Bundle;
import android.os.Handler;

import android.os.Message;
import android.util.Pair;

import com.sosotaxi.common.Constant;

/**
 * 评价司机任务
 */
public class RateForDriverTask implements Runnable{
    /**
     * 订单ID
     */
    private long mOrderId;

    /**
     * 评分
     */
    private double mRate;

    /**
     * 处理器
     */
    private Handler mHandler;

    public RateForDriverTask(long orderId,double rate,Handler handler){
        mOrderId=orderId;
        mRate=rate;
        mHandler=handler;
    }

    @Override
    public void run() {
        try {
            // 评价司机
            Pair<Boolean,String> result= OrderNetService.rateForDriver(mOrderId,mRate);

            Boolean isSuccessful=result.first;
            String responseMessage=result.second;

            // 填充数据
            Bundle bundle=new Bundle();
            bundle.putBoolean(Constant.EXTRA_IS_SUCCESSFUL,isSuccessful);
            bundle.putString(Constant.EXTRA_RESPONSE_MESSAGE,responseMessage);
            Message message=new Message();
            message.setData(bundle);

            // 发送控制器信息
            mHandler.sendMessage(message);

        } catch (Exception e) {

            // 填充数据
            Bundle bundle=new Bundle();
            bundle.putString(Constant.EXTRA_ERROR,e.getMessage());
            Message message=new Message();
            message.setData(bundle);

            // 发送控制器信息
            mHandler.sendMessage(message);
        }
    }
}
