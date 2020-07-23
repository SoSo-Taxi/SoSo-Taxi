/**
 * @Author 范承祥
 * @CreateTime 2020/7/19
 * @UpdateTime 2020/7/19
 */
package com.sosotaxi.service.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sosotaxi.common.Constant;

/**
 * 消息接收器
 */
public class OrderMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 可自定义处理逻辑
        Toast.makeText(context,intent.getStringExtra(Constant.EXTRA_RESPONSE_MESSAGE), Toast.LENGTH_LONG).show();
    }
}
