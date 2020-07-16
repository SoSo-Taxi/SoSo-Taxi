/**
 * @Author 岳兵
 * @CreateTime 2020/7/09
 * @UpdateTime 2020/7/13
 */
package com.sosotaxi.ui.home;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sosotaxi.R;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel mViewModel;

    protected static final int SELECTCITY = 0;
    protected static final int SEARCH_POI = 1;
    GeoCoder mSearch = null;
    private EditText location_edit;
    private String city;
    private String cityName = "";
    private String strAddress;

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private HomeFragment context;
    //LOCATION
    private double mLatitude;
    private double mLongtitude;
    //SENSOR
    private MyOrientationListener mMyOrientationListener;
    private float mCurrentX;

    //ICON
    private BitmapDescriptor mIconLocation;
    private LocationClient mLocationClient;
    public BDAbstractLocationListener myListener;
    private OnGetGeoCoderResultListener myGGListener;
    private LatLng mLastLocationData;
    private boolean isFirstin = true;
    private LatLng location;
    private TextView tvTitle;
    private TextView tv_location_name;
    private TextView tv_location_address;
    private RelativeLayout rl_location_detail;
    private String poiAddress;
    private String poiName="";
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //获取地图控件引用
        mMapView = (MapView)root.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SDKInitializer.initialize(getContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        this.context = this;
        initMyLocation();
        //按钮
        ImageButton locationButton = (ImageButton) getActivity().findViewById(R.id.but_Loc);
        //按钮处理
        locationButton.setOnClickListener(this);
        initTitle();
        initView();
        // 初始化搜索模块，注册搜索事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getContext(), "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                poiAddress = reverseGeoCodeResult.getAddress();
                city = reverseGeoCodeResult.getAddressDetail().city;
                strAddress = reverseGeoCodeResult.getAddress()+poiName;
                tvTitle.setText(city);
            }
        });
        location = new LatLng(mLatitude, mLongtitude);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(location));

        mMapView.showZoomControls(false);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(location)
                .zoom(16)
                .build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.locate);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(location)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public void onMapPoiClick(MapPoi arg0) {
                mBaiduMap.clear();
                LatLng point = new LatLng(39.963175, 116.400244);
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.locate);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .draggable(true)
                        .flat(true)
                        .alpha(0.5f)
                        .position(arg0.getPosition())
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                poiName = arg0.getName();
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(arg0.getPosition()));
                rl_location_detail.setVisibility(View.VISIBLE);
                tv_location_name.setVisibility(View.VISIBLE);
                tv_location_name.setText(poiName);
                tv_location_address.setText(poiAddress);
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mBaiduMap.clear();
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.locate);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(arg0)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(arg0));
                rl_location_detail.setVisibility(View.VISIBLE);
                tv_location_name.setVisibility(View.GONE);
                tv_location_address.setText(poiAddress);
            }
        });

        location_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ScheduleSearchCityPoiActivity.class);
                i.putExtra("city", city);
                startActivityForResult(i, SEARCH_POI);
            }
        });

        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        //开启方向传感器
        mMyOrientationListener.start();
    }
    @Override
    public void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        //停止方向传感器
        mMyOrientationListener.stop();
    }



    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
        mSearch.destroy();
    }
    @Override
    public void onClick(View v) {
        SDKInitializer.initialize(getContext());
        switch (v.getId()) {
            case R.id.but_Loc: {
                centerToMyLocation(mLatitude, mLongtitude);
                break;
            }
        }
    }
    private void initTitle() {
        ImageView imgBack = (ImageView) getActivity().findViewById(R.id.robin_title_left);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             getActivity().finish();
//            }
//        });

        tvTitle = (TextView) getActivity().findViewById(R.id.robin_title_center);
        tvTitle.setText("位置");
        tvTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ScheduleSelectCityActivity.class);
                startActivityForResult(i, SELECTCITY);
            }
        });

        TextView tvRight = (TextView) getActivity().findViewById(R.id.robin_title_right);

        tvRight.setText("完成");
        tvRight.setVisibility(View.GONE);
    }

    private void initView() {
        location_edit = (EditText) getView().findViewById(R.id.location_edit);
        mMapView = (MapView) getView().findViewById(R.id.bmapView);
        tv_location_name = (TextView) getView().findViewById(R.id.tv_location_name);
        tv_location_address = (TextView) getView().findViewById(R.id.tv_location_address);
        rl_location_detail = (RelativeLayout) getView().findViewById(R.id.rl_location_detail);
        Button bt_finish = (Button) getView().findViewById(R.id.bt_finish);
        bt_finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                String address = tv_location_address.getText().toString().trim();
                String name;
                if (tv_location_name.isShown()) {
                    name = tv_location_name.getText().toString().trim();
                }else {
                    name = "";
                }
                i.putExtra("location", address+name);
                getActivity().setResult(RESULT_OK, i);
                getActivity().finish();
            }
        });
    }

    /**
     * 定位
     */
    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentX).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            //设置自定义图标
            MyLocationConfiguration config = new
                    MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true, mIconLocation);
            mBaiduMap.setMyLocationConfiguration(config);
            //更新经纬度
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            //设置起点
            mLastLocationData = new LatLng(mLatitude, mLongtitude);
            if (isFirstin) {
                centerToMyLocation(location.getLatitude(), location.getLongitude());

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(getActivity(), "定位:"+location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(getActivity(), "定位:"+location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(getActivity(), "定位:"+location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(getActivity(), "定位:服务器错误", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(getActivity(), "定位:网络错误", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(getActivity(), "定位:手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
                isFirstin = false;
            }
        }
    }
    /**
     * 初始化定位
     */
    private void initMyLocation() {
        //缩放地图
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        //声明LocationClient类
        mLocationClient = new LocationClient(getActivity());
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//设置是否需要地址信息
        option.setScanSpan(1000);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        myListener = new MyLocationListener();
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //初始化图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.ic_home_location);
        initOrientation();
        //开始定位
        mLocationClient.start();
    }

    /**
     * 回到定位中心
     * @param latitude
     * @param longtitude
     */
    private void centerToMyLocation(double latitude, double longtitude) {
        mBaiduMap.clear();
        mLastLocationData = new LatLng(latitude, longtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(mLastLocationData);
        mBaiduMap.animateMapStatus(msu);
    }

    /**
     * 初始化传感器
     */
    private void initOrientation() {
        //传感器
        mMyOrientationListener = new MyOrientationListener(getContext());
        mMyOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECTCITY && resultCode == RESULT_OK) {
            //选择城市
            cityName = data.getStringExtra("cityName");
            tvTitle.setText(cityName);
            rl_location_detail.setVisibility(View.GONE);
            LatLng selectedCity = new LatLng(Double.valueOf(data.getStringExtra("lat")),Double.valueOf(data.getStringExtra("log")));
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(selectedCity)
                    .zoom(12)
                    .build();
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(selectedCity));
        } else if (requestCode == SEARCH_POI && resultCode == RESULT_OK) {
            //搜索位置
            if (data != null) {
                String searchPoiAddress = data.getStringExtra("address");
                poiName = data.getStringExtra("name");
                double latitude = data.getExtras().getDouble("latitude");
                double longitude = data.getExtras().getDouble("longitude");
                LatLng poiLocation = new LatLng(latitude, longitude);
                rl_location_detail.setVisibility(View.VISIBLE);
                tv_location_name.setVisibility(View.VISIBLE);
                tv_location_name.setText(poiName);
                tv_location_address.setText(searchPoiAddress);

                mBaiduMap.clear();
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(poiLocation)
                        .zoom(16)
                        .build();
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.locate);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(poiLocation)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(poiLocation));
            }
        }
    }
}