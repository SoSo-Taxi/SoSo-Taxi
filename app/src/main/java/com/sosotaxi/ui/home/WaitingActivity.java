/**
 * @Author 岳兵
 * @CreateTime 2020/7/18
 * @UpdateTime 2020/7/24
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.sosotaxi.model.message.BaseMessage;
import com.sosotaxi.model.message.CheckBondedDriverGeoBody;
import com.sosotaxi.model.message.CheckBondedDriverGeoResponseBody;
import com.sosotaxi.model.message.MessageType;
import com.sosotaxi.model.message.OrderResultBody;
import com.sosotaxi.model.message.StartOrderResponseBody;
import com.sosotaxi.service.net.OrderClient;
import com.sosotaxi.service.net.OrderMessageReceiver;
import com.sosotaxi.service.net.OrderService;
import com.sosotaxi.service.net.QueryLatestPointTask;
import com.sosotaxi.ui.overlay.DrivingRouteOverlay;
import com.sosotaxi.utils.MessageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class WaitingActivity extends Activity {

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


    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private RoutePlanSearch mSearch;

    //mylocation
    private Double myLatitude;
    private Double myLongitude;
    private LocationPoint myLocation;
    //textview
    private TextView tv_getCarPlace;
    private TextView license;
    private TextView carInfo;
    private TextView driverInfo;
    private TextView tv_rate;
    private TextView waitingState;

    //token
    private String token;

    //startPlace
    private String startPlace;

    //licensePlate
    private String licensePlate;

    //carinfo
    private String carBrand;
    private String carColor;

    //driver
    private String driverName;
    private double rate;

    //driver location
    private LocationPoint driverLocation;


    //driver distance
    private double driverDistance;

    private String aaa;

    private Order mOrder;

    private DriverCarInfo mDriver;

    ConstraintLayout barrierBlank;

    private QueryLatestPointTask mQueryLatestPointTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        token = getIntent().getStringExtra("token");
        myLatitude = getIntent().getDoubleExtra("myLatitude", 1.0);
        myLongitude = getIntent().getDoubleExtra("myLongitude", 1.0);
        mOrder = getIntent().getParcelableExtra(Constant.EXTRA_ORDER);


        // 初始化服务并绑定
        startService();
        bindService();
        registerReceiver();


        //获取消息助手
        mMessageHelper = MessageHelper.getInstance();

        initView();
    }

    private void initView() {

        mMapView = (MapView) findViewById(R.id.bmapView);
        barrierBlank=(ConstraintLayout)findViewById(R.id.barrierblank);
        tv_getCarPlace = (TextView) findViewById(R.id.getCarPlace);
        startPlace = getIntent().getStringExtra("startPoint");
        tv_getCarPlace.setText("请前往" + startPlace + "上车。若您改变行程，可在10分钟内免费取消。近期车辆较少，请尽量不取消，戴好口罩。");
        license = (TextView) findViewById(R.id.license_waiting);
        carInfo = (TextView) findViewById(R.id.carInfo);
        driverInfo = (TextView) findViewById(R.id.driverInfo);
        tv_rate = (TextView) findViewById(R.id.rate);
        mBaiduMap = mMapView.getMap();
        waitingState=(TextView)findViewById(R.id.waitingstate);
        waitingState.setText("正在为您搜索附近的车辆");

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
    private void startService() {
        Intent intent = new Intent(getApplicationContext(), OrderService.class);
        startService(intent);
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent intent = new Intent(getApplicationContext(), OrderService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver() {
        mOrderMessageReceiver = new OrderMessageReceiver();
        IntentFilter intentFilter = new IntentFilter(Constant.FILTER_CONTENT);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //解析消息
                String json = intent.getStringExtra(Constant.EXTRA_RESPONSE_MESSAGE);
                Gson gson = new Gson();
                BaseMessage message = gson.fromJson(json, BaseMessage.class);
                Log.d("MESSAGE", json);
                if (message.getType() == MessageType.ORDER_RESULT_MESSAGE) {
                    try {
                        JSONObject object = new JSONObject(json);
                        String bodyString = object.getString("body");
                        OrderResultBody body = gson.fromJson(bodyString, OrderResultBody.class);
                        mDriver = body.getDriverCarInfo();
                        licensePlate = mDriver.getLicensePlate();
                        license.setText(licensePlate);
                        carBrand = mDriver.getCarBrand();
                        carColor = mDriver.getCarColor();
                        carInfo.setText(carBrand+"CRV"+ "·" + carColor+"色");

                        rate = mDriver.getRate();
                        aaa="快车司机正努力赶来，请避开人群等候";
                        waitingState.setText(aaa);
                        barrierBlank.setVisibility(View.INVISIBLE);

                        // 查询司机最新位置
                        queryDriverLatestPoint();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (message.getType() == MessageType.CHECK_BONDED_DRIVER_GEO_RESPONSE) {
                    try {
                        JSONObject object = new JSONObject(json);
                        String bodyString = object.getString("body");
                        CheckBondedDriverGeoResponseBody body = gson.fromJson(bodyString,CheckBondedDriverGeoResponseBody.class);
                        driverLocation = body.getPoint();
                        driverDistance = body.getDistance();
                        // 路径规划
                        initRoutePlan();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (message.getType() == MessageType.ARRIVE_DEPART_POINT_TO_PASSENGER) {
                    aaa="快车司机已经到达上车点";
                    waitingState.setText(aaa);


                } else if (message.getType() == MessageType.PICK_UP_PASSENGER_MESSAGE_TO_PASSENGER) {
                    Intent routeIntent = new Intent(WaitingActivity.this, RouteActivity.class);
                    routeIntent.putExtra("token", token);
                    routeIntent.putExtra("licensePlate",licensePlate);

                    routeIntent.putExtra(Constant.EXTRA_ORDER,mOrder);
                    routeIntent.putExtra(Constant.EXTRA_DRIVER,mDriver);
                    startActivity(routeIntent);
                } else if(message.getType()== MessageType.START_ORDER_RESPONSE){
                    try {
                        // 获取订单ID
                        JSONObject object = new JSONObject(json);
                        String bodyString = object.getString("body");
                        StartOrderResponseBody body=gson.fromJson(bodyString,StartOrderResponseBody.class);
                        mOrder.setOrderId(body.getOrderId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, intentFilter);
    }

    // 服务连接监听器
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBinder = (OrderService.DriverOrderBinder) service;

            mDriverOrderService = mBinder.getService(token);
            mDriverOrderClient = mDriverOrderService.getClient();
            Toast.makeText(getApplicationContext(), "Service已连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(), "Service已断开", Toast.LENGTH_SHORT).show();
        }
    };

    public OrderClient getClient() {
        return mDriverOrderClient;
    }

    /**
     * 查询司机位置
     */
    public void queryDriverLatestPoint() {

        mMessageHelper.setClient(getClient());

        myLocation = new LocationPoint(myLatitude, myLongitude);

        // 封装消息
        CheckBondedDriverGeoBody body = new CheckBondedDriverGeoBody();
        body.setUserToken(token);
        body.setPoint(myLocation);

        // 构造消息
        BaseMessage message = mMessageHelper.build(MessageType.CHECK_BONDED_DRIVER_GEO_MESSAGE, body);

        // 初始化查询位置任务
        mQueryLatestPointTask=new QueryLatestPointTask(Constant.TIME_INTERVAL,mMessageHelper,message);

        mMessageHelper.send(message);

        // 开始任务
//        new Thread(mQueryLatestPointTask).start();


        Log.e("stop","stop");
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
                overlay.addToMap(true);
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
        PlanNode startNode = PlanNode.withLocation(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude()));
        PlanNode endNode = PlanNode.withLocation(new LatLng(mOrder.getDepartPoint().getLatitude(), mOrder.getDepartPoint().getLongitude()));
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(startNode)
                .to(endNode));
    }
}
