/**
 * @Author 岳兵
 * @CreateTime 2020/7/17
 * @UpdateTime 2020/7/19
 */


package com.sosotaxi.ui.home;

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

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.sosotaxi.R;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.LocationPoint;
import com.sosotaxi.model.Order;
import com.sosotaxi.model.message.BaseMessage;
import com.sosotaxi.model.message.MessageType;
import com.sosotaxi.model.message.StartOrderBody;
import com.sosotaxi.model.message.StartOrderResponseBody;
import com.sosotaxi.service.net.OrderClient;
import com.sosotaxi.service.net.OrderService;
import com.sosotaxi.ui.overlay.DrivingRouteOverlay;
import com.sosotaxi.utils.MessageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CallCarActivity extends AppCompatActivity {

    private RoutePlanSearch mSearch;

    private BaiduMap mBaiduMap;

    private Button mcall = null;

    //Textview
    private TextView tv_start=null;
    private TextView tv_dest=null;


    private MapView mMapView =null;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private String myLocation=null;
    private String destination=null;
    private Double destLatitude=null;
    private Double destLongitude=null;
    private Double myLatitude=null;
    private Double myLongitude=null;

    private String cityName;

    private String token;



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
    private BroadcastReceiver mBroadcastReceiver;

    private MessageHelper mMessageHelper;

    private Order mOrder;


    /** TabLayout调谐器 */
    private TabLayoutMediator mMediator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        initTitle();
        initView();
        initData();

        // 路径规划
        initRoutePlan();

        // 初始化服务并绑定
        startService();
        bindService();
        registerReceiver();


        //获取消息助手
        mMessageHelper=MessageHelper.getInstance();

    }
    private void initTitle() {
//        ImageView imgBack = (ImageView) findViewById(R.id.robin_title_left);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//
//        });

        myLocation=getIntent().getStringExtra("mlocation");
        destination=getIntent().getStringExtra("dlocation");
        tv_start = (TextView) findViewById(R.id.start);
        tv_start.setText(myLocation);
        tv_dest = (TextView)findViewById(R.id.dest);
        tv_dest.setText(destination);


    }
    private void initView(){

        mMapView = (MapView)findViewById(R.id.bmapView);
        mTabLayout=findViewById(R.id.tabLayoutCall);
        mViewPager=findViewById(R.id.viewPagerCall);

        // 初始化Tab
        initTab();

        // 获取百度地图对象
        mBaiduMap = mMapView.getMap();

        // 获取路径规划对象
        mSearch = RoutePlanSearch.newInstance();

        // 设置路径规划结果监听器
        mSearch.setOnGetRoutePlanResultListener(onGetRoutePlanResultListener);

        mcall = (Button)findViewById(R.id.call);
        mcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void initData(){
        cityName=getIntent().getStringExtra("cityName");
        token=getIntent().getStringExtra("token");
        myLongitude=getIntent().getDoubleExtra("mLongitude",1.0);
        myLatitude=getIntent().getDoubleExtra("mLatitude",1.0);
        destLongitude=getIntent().getDoubleExtra("dLongitude",1.0);
        destLatitude=getIntent().getDoubleExtra("dLatitude",1.0);
    }

    private void initTab(){
        // Tab页列表
        final List<Fragment> fragments=new ArrayList<>();
        fragments.add(new CallEconomicTabFragment());
        fragments.add(new CallComfortTabFragment());
        fragments.add(new CallBothTabFragment());

        // 标题列表
        final List<String> titles=new ArrayList<>();
        titles.add("经济型");
        titles.add("舒适型");
        titles.add("同时呼叫");

        // 添加Tab页
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)),true);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)),false);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)),false);

        // 禁用预加载
        mViewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        // 设置适配器
        mViewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(),getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        // 设置调谐器
        mMediator=new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // 设置Tab标题
                tab.setText(titles.get(position));
            }
        });

        // 链接
        mMediator.attach();
    }

    /**
     * 初始化路径规划
     */
    private void initRoutePlan() {
        Toast.makeText(getApplicationContext(), "开始路径规划", Toast.LENGTH_SHORT).show();
        PlanNode startNode = PlanNode.withLocation(new LatLng(myLatitude,myLongitude));
        PlanNode endNode = PlanNode.withLocation(new LatLng(destLatitude,destLongitude));
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(startNode)
                .to(endNode));
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
        try{
            // 断开连接
            unbindService(serviceConnection);
            unregisterReceiver(mBroadcastReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage(){
        LocationPoint departpoint = new LocationPoint(myLatitude,myLongitude);

        LocationPoint destpoint= new LocationPoint(destLatitude,destLongitude);
        Short chosen =(short)mTabLayout.getSelectedTabPosition();
        Log.e("chosentab",""+chosen);



        Long passengerId = Long.valueOf(23);
        //开始封装
        StartOrderBody body = new StartOrderBody();
        body.setUserToken(token);
        body.setCity(cityName);
        body.setPassengerId(passengerId);
        body.setUserName("8613683333113");
        body.setPhoneNumber("8613683333113");
        body.setDepartPoint(departpoint);
        body.setDestinationPoint(destpoint);
        body.setServiceType(chosen);
        body.setPassengerNum((short)1);
        body.setDepartName(myLocation);
        body.setDestName(destination);

        // 创建订单
        mOrder=new Order();
        mOrder.setDepartPoint(departpoint);
        mOrder.setDestinationPoint(destpoint);
        mOrder.setDepartName(myLocation);
        mOrder.setDestinationName(destination);

        // 发送方式一
        // 构造消息
        mMessageHelper.setClient(getClient());
        BaseMessage message=mMessageHelper.build(MessageType.START_ORDER_MESSAGE,body);
        // 发送消息
        mMessageHelper.send(message);

    }
    /**
     * 开启WebSocket服务
     */
    private void startService(){
        Intent intent=new Intent(getApplicationContext(), OrderService.class);
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
        mBroadcastReceiver =new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                //解析消息
                String json = intent.getStringExtra(Constant.EXTRA_RESPONSE_MESSAGE);
                Gson gson = new Gson();
                BaseMessage message = gson.fromJson(json, BaseMessage.class);
                Log.d("MESSAGE", json);
                if(message.getType()== MessageType.START_ORDER_RESPONSE){
                    try {
                        // 获取订单ID
                        JSONObject object = new JSONObject(json);
                        String bodyString = object.getString("body");
                        StartOrderResponseBody body=gson.fromJson(bodyString,StartOrderResponseBody.class);
                        mOrder.setOrderId(body.getOrderId());

                        // 跳转等待界面
                        Intent waitingIntent=new Intent(CallCarActivity.this,WaitingActivity.class);
                        waitingIntent.putExtra("startPoint",myLocation);
                        waitingIntent.putExtra("token",token);
                        waitingIntent.putExtra("myLatitude",myLatitude);
                        waitingIntent.putExtra("myLongitude",myLongitude);
                        waitingIntent.putExtra(Constant.EXTRA_ORDER,mOrder);
                        startActivity(waitingIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        };
        IntentFilter intentFilter=new IntentFilter(Constant.FILTER_CONTENT);
        registerReceiver(mBroadcastReceiver,intentFilter);
    }
    // 服务连接监听器
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.e("aaaaaaaaaaaa",token+"aaa");
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
}
