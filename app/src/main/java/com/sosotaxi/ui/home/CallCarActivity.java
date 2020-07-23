/**
 * @Author 岳兵
 * @CreateTime 2020/7/17
 * @UpdateTime 2020/7/19
 */


package com.sosotaxi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.sosotaxi.R;
import com.sosotaxi.ui.overlay.DrivingRouteOverlay;

import java.util.ArrayList;
import java.util.List;

public class CallCarActivity extends AppCompatActivity {

    private RoutePlanSearch mSearch;

    private BaiduMap mBaiduMap;

    private Button mcall = null;

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

    /** TabLayout调谐器 */
    private TabLayoutMediator mMediator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        initTitle();
        initView();
        initData();
        initRoutePlan();
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
                Intent intent=new Intent(CallCarActivity.this,WaitingActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData(){
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
