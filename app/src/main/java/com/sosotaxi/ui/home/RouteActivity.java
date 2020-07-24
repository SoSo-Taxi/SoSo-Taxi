/**
 * @Author 岳兵
 * @CreateTime 2020/7/18
 * @UpdateTime 2020/7/19
 */
package com.sosotaxi.ui.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.baidu.mapapi.map.MapView;
import com.google.gson.Gson;
import com.sosotaxi.R;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.LocationPoint;
import com.sosotaxi.model.message.BaseMessage;
import com.sosotaxi.model.message.CheckBondedDriverGeoBody;
import com.sosotaxi.model.message.MessageType;
import com.sosotaxi.service.net.OrderClient;
import com.sosotaxi.service.net.OrderMessageReceiver;
import com.sosotaxi.service.net.OrderService;
import com.sosotaxi.ui.userInformation.wallet.PaymentSettingActivity;
import com.sosotaxi.utils.MessageHelper;


public class RouteActivity extends Activity {
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


    private MapView mMapView =null;

    //token
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        token=getIntent().getStringExtra("token");

        // 初始化服务并绑定
        startService();
        bindService();
        registerReceiver();


        //获取消息助手
        mMessageHelper=MessageHelper.getInstance();


        initView();

    }

    private void initView(){

        mMapView = (MapView)findViewById(R.id.bmapView);




    }



    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        // 断开连接
        unbindService(serviceConnection);
        if(mOrderMessageReceiver!=null){
            unregisterReceiver(mOrderMessageReceiver);
        }
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
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String json = intent.getStringExtra(Constant.EXTRA_RESPONSE_MESSAGE);
                Gson gson=new Gson();
                BaseMessage message=gson.fromJson(json,BaseMessage.class);
                Log.d("MESSAGE",json);
                if(message.getType()==MessageType.ARRIVE_DEPART_POINT_TO_PASSENGER){
                    Intent orderIntent = new Intent(RouteActivity.this, PayActivity.class);
                    startActivity(orderIntent);
                }
            }
        }, intentFilter);
    }

    // 服务连接监听器
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBinder=(OrderService.DriverOrderBinder)service;

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
