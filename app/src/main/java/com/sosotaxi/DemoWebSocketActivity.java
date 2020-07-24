package com.sosotaxi;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.sosotaxi.common.Constant;
import com.sosotaxi.model.LocationPoint;
import com.sosotaxi.model.message.BaseMessage;
import com.sosotaxi.model.message.CheckBondedDriverGeoBody;
import com.sosotaxi.model.message.MessageType;
import com.sosotaxi.service.net.OrderClient;
import com.sosotaxi.service.net.OrderMessageReceiver;
import com.sosotaxi.service.net.OrderService;
import com.sosotaxi.utils.MessageHelper;

/**
 * @Author 范承祥
 * @CreateTime 2020/7/23
 * @UpdateTime 2020/7/23
 */
public class DemoWebSocketActivity extends AppCompatActivity {
    /**
     * 连接器
     */
    private OrderClient mDriverOrderClient;

    /**
     * WebSocket服务
     */
    private OrderService mDriverOrderService;

    /**
     * 绑定
     */
    private OrderService.DriverOrderBinder mBinder;

    /**
     * 接收器
     */
    private OrderMessageReceiver mOrderMessageReceiver;

    private MessageHelper mMessageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化服务并绑定
        startService();
        bindService();
        registerReceiver();


        //获取消息助手
        mMessageHelper=MessageHelper.getInstance();
    }

    /**
     * 开启WebSocket服务
     */
    private void startService(){
        Intent intent=new Intent(getApplicationContext(),OrderService.class);
        startService(intent);
    }

    /**
     * 绑定服务
     */
    private void bindService(){
        Intent intent = new Intent(getApplicationContext(), OrderService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver(){
        mOrderMessageReceiver=new OrderMessageReceiver();
        IntentFilter intentFilter=new IntentFilter(Constant.FILTER_CONTENT);
        registerReceiver(mOrderMessageReceiver,intentFilter);
    }

    // 服务连接监听器
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBinder=(OrderService.DriverOrderBinder)service;
            String token="";
            mDriverOrderService=mBinder.getService(token);
            mDriverOrderClient=mDriverOrderService.getClient();
            Toast.makeText(getApplicationContext(),"Service已连接",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(),"Service已断开",Toast.LENGTH_SHORT).show();
        }
    };

    public OrderClient getClient(){
        return mDriverOrderClient;
    }

    /**
     * 发送示例
     */
    public void sendDemo(){

        mMessageHelper.setClient(getClient());

        String token="";
        LocationPoint point=new LocationPoint(23.0,120.0);

        // 封装消息
        CheckBondedDriverGeoBody body=new CheckBondedDriverGeoBody();
        body.setUserToken(token);
        body.setGeoPoint(point);

        // 发送方式一
        // 构造消息
        BaseMessage message=mMessageHelper.build(MessageType.CHECK_BONDED_DRIVER_GEO_MESSAGE,body);
        // 发送消息
        mMessageHelper.send(message);

        // 发送方式二
        mMessageHelper.send(MessageType.CHECK_BONDED_DRIVER_GEO_MESSAGE,body);
    }



}
