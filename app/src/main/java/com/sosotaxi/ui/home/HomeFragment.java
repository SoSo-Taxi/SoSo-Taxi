/**
 * @Author 岳兵
 * @CreateTime 2020/7/09
 * @UpdateTime 2020/7/19
 */
package com.sosotaxi.ui.home;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bigkoo.pickerview.TimePickerView;
import com.sosotaxi.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel mViewModel;

    protected static final int SELECTCITY = 0;
    protected static final int SEARCH_POI = 1;
    GeoCoder mSearch = null;
    private EditText location_edit;

    //String
    private String city;
    private String cityName = "";
    private String strAddress;
    private String poiAddress;
    private String myPoiPlace;
    private String poiName="";
    private String rName="";


    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private HomeFragment context;
    //LOCATION
    private double mLatitude;
    private double mLongitude;
    private double dLatitude;
    private double dLongtitude;
    private double rLatitude;
    private double rLongitude;
    String myLocation= new String();
    private LatLng location;
    private LatLng mLastLocationData;
    //SENSOR
    private MyOrientationListener mMyOrientationListener;
    private float mCurrentX;

    //ICON
    private BitmapDescriptor mIconLocation;
    private LocationClient mLocationClient;
    public BDAbstractLocationListener myListener;
    private OnGetGeoCoderResultListener myGGListener;

    //DISTANCE
    private double rDistance;

    //SIMBOL
    private int rSimbol=0;

    private boolean isFirstin = true;


    //Textview
    private TextView tvTitle;
    private TextView tv_home_start;
    private TextView tv_location_address;
    private TextView tv_book_car;
    private RelativeLayout rl_location_detail;
    private Button tvCall;

    //TimePicker
    TimePickerView pvTime;

    //url
    private static final String RECOMMEND_LOCAL_URL="http://api.map.baidu.com/parking/search?location=";
    private static final String RECOMMEND_AK_URL="&coordtype=bd09ll&ak=";
    private static final String AK="RmmZVO6jFDooPymSqVdIeRUNpNgAMAza";
    private String SN = "";

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
        initMyLocation();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SDKInitializer.initialize(getContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);

        this.context = this;

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
                String cityDisplay =  "当前定位："+city;

                tvTitle.setText(cityDisplay);

                strAddress = reverseGeoCodeResult.getAddress();

            }
        });
        location = new LatLng(mLatitude, mLongitude);
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
                        .fromResource(R.drawable.ic_home_marker);
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


                tv_location_address.setText(poiAddress);
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mBaiduMap.clear();
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_home_marker);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(arg0)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(arg0));
                rl_location_detail.setVisibility(View.VISIBLE);
                tv_location_address.setText(poiAddress);
            }
        });

        location_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String destination = location_edit.getText().toString();
                Intent i = new Intent(getContext(), ScheduleSearchCityPoiActivity.class);
                i.putExtra("destination",destination);
                i.putExtra("city", city);
                startActivityForResult(i, SEARCH_POI);
            }
        });


        try {
            getRecommend();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        finally {

        }
        book();

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
                centerToMyLocation(mLatitude, mLongitude);
                break;
            }
        }
    }
    private void initTitle() {
//        ImageView imgBack = (ImageView) getActivity().findViewById(R.id.robin_title_left);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             getActivity().finish();
//            }
//        });

        tvTitle = (TextView) getActivity().findViewById(R.id.city_selected);
        tvTitle.setText("位置");
        tvTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ScheduleSelectCityActivity.class);
                startActivityForResult(i, SELECTCITY);
            }
        });

    }

    //预约功能
    private  void book(){
        tv_book_car=(TextView)getActivity().findViewById(R.id.bookCar);
        tv_book_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show(tv_book_car);
            }
        });

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar cal=Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int hour= cal.get(Calendar.HOUR);
        int min= cal.get(Calendar.MINUTE);
        startDate.set(2020, month,day,hour,min);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 12, 31,24,00);
        //时间选择器
        pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                TextView btn = (TextView) v;
                btn.setText(getTimes(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();




    }

    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initView() {
        location_edit = (EditText) getView().findViewById(R.id.location_edit);
        mMapView = (MapView) getView().findViewById(R.id.bmapView);
        tv_home_start = (TextView) getView().findViewById(R.id.tv_home_start);
        tv_location_address = (TextView) getView().findViewById(R.id.tv_location_address);
        rl_location_detail = (RelativeLayout) getView().findViewById(R.id.rl_location_detail);
        tvCall = (Button)getView().findViewById(R.id.bt_finish) ;
        tvCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CallCarActivity.class);
//                String address = tv_location_address.getText().toString().trim();
//                String name;
//
//                if (tv_home_start.isShown()) {
//                    name = tv_home_start.getText().toString().trim();
//                }else {
//                    name = "";
//                }
                i.putExtra("dlocation", poiName);
                i.putExtra("mlocation",rName);
                i.putExtra("dLatitude",dLatitude);
                i.putExtra("dLongitude",dLongtitude);
                i.putExtra("mLatitude",rLatitude);
                i.putExtra("mLongitude", rLongitude);
                startActivity(i);
            }
        });
    }

    /**
     * 推荐上车点
     */
    private void getRecommend() throws UnsupportedEncodingException, NoSuchAlgorithmException {


        SN = "33:62:4C:54:CA:F7:5F:C2:15:5D:C7:EC:E5:CD:9A:9D:78:08:0A:9D;com.sosotaxi";

        myLocation= ""+mLongitude +","+mLatitude;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myLocation= ""+mLongitude +","+mLatitude;
                String urlTest = RECOMMEND_LOCAL_URL+myLocation+RECOMMEND_AK_URL+AK+"&radius=450"+"&mcode="+SN;
                Log.i("urltest",urlTest);
                try{
                    URL url = new URL(urlTest);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int responsecode = conn.getResponseCode();
                    String code = ""+responsecode;
                    Log.e("请求响应编号",code);
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    StringBuffer buffer = new StringBuffer();
                    String temp = null;
                    while((temp=bufferedReader.readLine()) != null){
                        buffer.append(temp);
                    }
                    bufferedReader.close();
                    reader.close();
                    inputStream.close();
                    Log.e("MAIN", buffer.toString());
                    String recommendStops=buffer.toString();
                    JSONObject jsonObject = new JSONObject(recommendStops);
                    JSONArray rStops = new JSONArray();
                    rStops= jsonObject.getJSONArray("recommendStops");
                    JSONObject bestStops = rStops.getJSONObject(0);
                    rName=bestStops.getString("name");
                    rDistance=bestStops.getDouble("distance");
                    rLatitude=bestStops.getDouble("bd09ll_y");
                    rLongitude=bestStops.getDouble("bd09ll_x");






                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();


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
            mLongitude = location.getLongitude();
            String rSimbolString=""+rSimbol;
            Log.e("zahuishi",rSimbolString);
            if(rSimbol==0){
                tv_home_start.setText("您将在"+rName+"上车");
                Log.e("???",rName);

                //添加marker
                LatLng rLocation = new LatLng(rLatitude,rLongitude);

                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_re_pin);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(rLocation)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);



                TextView tv_info = new TextView(getContext());
                tv_info.setBackgroundResource(R.drawable.marker5);

                tv_info.setGravity(Gravity.CENTER);

                tv_info.setPadding(10,10,10,10);
                Drawable drawable = getResources().getDrawable(R.drawable.ic_locate6);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_info.setPadding(40,10,40,10);

                tv_info.setCompoundDrawables(drawable,null,null,null);
                tv_location_address.setText(rName);


                tv_info.setText("  "+rDistance+"m  "+rName+"  ");

                //构造InfoWindow
                //point 描述的位置点
                //-100 InfoWindow相对于point在y轴的偏移量
                InfoWindow mInfoWindow = new InfoWindow(tv_info,rLocation, -100);

                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(rLocation));




                //使InfoWindow生效
                mBaiduMap.showInfoWindow(mInfoWindow);
                if(rName!=""){
                rSimbol=1;
                }
            }
            myPoiPlace=location.getAddrStr();


            //设置起点
            mLastLocationData = new LatLng(mLatitude, mLongitude);
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
            String cityDisplay = new String();
            cityDisplay = "当前定位："+cityName;

            tvTitle.setText(cityDisplay);

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
                location_edit.setText(poiName);
                dLatitude = latitude;
                dLongtitude = longitude;
                LatLng poiLocation = new LatLng(latitude, longitude);


//                SpannableStringBuilder spannable = new SpannableStringBuilder(startingPoint);
//
//                int index=startingPoint.length();
//                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4500")),3,3+index
//                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                rl_location_detail.setVisibility(View.VISIBLE);


                tv_location_address.setText(searchPoiAddress);
                tvCall.setVisibility(View.VISIBLE);

                mBaiduMap.clear();
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(poiLocation)
                        .zoom(16)
                        .build();
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_home_marker);
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