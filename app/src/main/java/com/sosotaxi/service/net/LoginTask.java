package com.sosotaxi.service.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sosotaxi.common.Constant;
import com.sosotaxi.model.User;
import com.sosotaxi.ui.login.LoginActivity;

/**
 * @Author 范承祥
 * @CreateTime 2020/7/13
 * @UpdateTime 2020/7/13
 */
public class LoginTask implements Runnable {

    private User mUser;
    private Handler mHandler;

    public LoginTask(User user, Handler handler){
        mUser=user;
        mUser.setRememberMe(true);
        mHandler=handler;
    }

    @Override
    public void run() {
        try {
            String response="";

            boolean isAuthorized= LoginNetService.login(mUser);

            // 填充数据
            Bundle bundle=new Bundle();
            bundle.putString(Constant.EXTRA_PHONE,mUser.getUserName());
            bundle.putString(Constant.EXTRA_PASSWORD,mUser.getPassword());
            bundle.putBoolean(Constant.EXTRA_IS_AUTHORIZED,isAuthorized);
            bundle.putString(Constant.EXTRA_TOKEN,mUser.getToken());
            bundle.putString(Constant.EXTRA_RESPONSE_MESSAGE,response);
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
