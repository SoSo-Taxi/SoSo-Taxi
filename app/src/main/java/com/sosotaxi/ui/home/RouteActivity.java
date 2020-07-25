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


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.google.gson.Gson;
import com.sosotaxi.R;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.DriverCarInfo;
import com.sosotaxi.model.LocationPoint;
import com.sosotaxi.model.Order;
import com.sosotaxi.model.message.ArriveDepartPointToPassengerBody;
import com.sosotaxi.model.message.BaseMessage;
import com.sosotaxi.model.message.CheckBondedDriverGeoBody;
import com.sosotaxi.model.message.CheckBondedDriverGeoResponseBody;
import com.sosotaxi.model.message.MessageType;
import com.sosotaxi.model.message.OrderResultBody;
import com.sosotaxi.service.net.OrderClient;
import com.sosotaxi.service.net.OrderMessageReceiver;
import com.sosotaxi.service.net.OrderService;
import com.sosotaxi.service.net.QueryLatestPointTask;
import com.sosotaxi.ui.overlay.DrivingRouteOverlay;
import com.sosotaxi.ui.userInformation.wallet.PaymentSettingActivity;
import com.sosotaxi.utils.MessageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


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

    private BaiduMap mBaiduMap;

    private RoutePlanSearch mSearch;

    //token
    private String token;

    //driver location
    private LocationPoint driverLocation;

    private Order mOrder;

    private DriverCarInfo mDriver;

    private QueryLatestPointTask mQueryLatestPointTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        token=getIntent().getStringExtra("token");
        mOrder=getIntent().getParcelableExtra(Constant.EXTRA_ORDER);
        mDriver=getIntent().getParcelableExtra(Constant.EXTRA_DRIVER);

        // 初始化服务并绑定
        startService();
        bindService();
        registerReceiver();


        //获取消息助手
        mMessageHelper=MessageHelper.getInstance();


        initView();

        //路径规划
        initRoutePlan();

        // 查询司机实时位置
        queryDriverLatestPoint();
    }

    private void initView(){

        mMapView = (MapView)findViewById(R.id.bmapView);
        mBaiduMap=mMapView.getMap();

        // 获取路径规划对象
        mSearch = RoutePlanSearch.newInstance();

        // 设置路径规划结果监听器
        mSearch.setOnGetRoutePlanResultListener(onGetRoutePlanResultListener);
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
        try{
            // 断开连接
            unbindService(serviceConnection);
            unregisterReceiver(mOrderMessageReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(mQueryLatestPointTask.isExit()==false){
            // 停止查询司机位置
            mQueryLatestPointTask.setIsExit(true);
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
                    try {
                        JSONObject object=new JSONObject(json);
                        String bodyString=object.getString("body");
                        ArriveDepartPointToPassengerBody body=gson.fromJson(bodyString,ArriveDepartPointToPassengerBody.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent orderIntent = new Intent(RouteActivity.this, PayActivity.class);
                    intent.putExtra(Constant.EXTRA_ORDER,mOrder);
                    intent.putExtra(Constant.EXTRA_DRIVER,mDriver);
                    startActivity(orderIntent);
                }else if(message.getType() == MessageType.CHECK_BONDED_DRIVER_GEO_RESPONSE){
                    try {
                        JSONObject object = new JSONObject(json);
                        String bodyString = object.getString("body");
                        CheckBondedDriverGeoResponseBody body = gson.fromJson(bodyString,CheckBondedDriverGeoResponseBody.class);
                        driverLocation = body.getPoint();
                        // 路径规划
                        initRoutePlan();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
     * 查询司机位置
     */
    public void queryDriverLatestPoint() {

        mMessageHelper.setClient(getClient());

        // 封装消息
        CheckBondedDriverGeoBody body = new CheckBondedDriverGeoBody();
        body.setUserToken(token);
        body.setGeoPoint(driverLocation);

        // 构造消息
        BaseMessage message = mMessageHelper.build(MessageType.CHECK_BONDED_DRIVER_GEO_MESSAGE, body);

        // 初始化查询位置任务
        mQueryLatestPointTask=new QueryLatestPointTask(Constant.TIME_INTERVAL,mMessageHelper,message);

        // 开始任务
        new Thread(mQueryLatestPointTask).start();
    }

    /**
     * 路线规划结果返回监听类
     */
    OnGetRoutePlanResultListener onGetRoutePlanResultListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            //创建DrivingRouteOverlay实例
            DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
            // 清除原有路线
            overlay.removeFromMap();
            List<DrivingRouteLine> routes = drivingRouteResult.getRouteLines();
            if (routes != null && routes.size() > 0) {
                //获取路径规划数据
                //为DrivingRouteOverlay实例设置数据
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                //在地图上绘制路线
                overlay.addToMap(false);
                overlay.zoomToSpanPaddingBounds(200, 200, 200, 300);
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };

    /**
     * 初始化路径规划
     */
    private void initRoutePlan() {
        Toast.makeText(getApplicationContext(), "开始路径规划", Toast.LENGTH_SHORT).show();

        PlanNode startNode = PlanNode.withLocation(new LatLng(mOrder.getDepartPoint().getLatitude(),mOrder.getDepartPoint().getLongitude()));
        PlanNode endNode = PlanNode.withLocation(new LatLng(mOrder.getDestinationPoint().getLatitude(),mOrder.getDestinationPoint().getLongitude()));
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(startNode)
                .to(endNode));
    }

}
