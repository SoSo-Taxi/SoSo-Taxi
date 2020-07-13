/**
 * @Author 范承祥
 * @CreateTime 2020/7/13
 * @UpdateTime 2020/7/13
 */
package com.sosotaxi.service.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sosotaxi.common.Constant;
import org.json.JSONException;

import java.io.IOException;

/**
 * 查询手机号是否已注册任务
 */
public class IsRegisteredTask implements Runnable {

    private String mUsername;
    private Handler mHandler;

    public IsRegisteredTask(String username, Handler handler){
        mUsername=username;
        mHandler=handler;
    }

    @Override
    public void run() {

        try {
            // 查询该手机号是否已注册
            boolean isRegistered= LoginNetService.isRegistered(mUsername);

            // 填充数据
            Bundle bundle=new Bundle();
            bundle.putString(Constant.EXTRA_PHONE,mUsername);
            bundle.putBoolean(Constant.EXTRA_IS_REGISTERED,isRegistered);
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
