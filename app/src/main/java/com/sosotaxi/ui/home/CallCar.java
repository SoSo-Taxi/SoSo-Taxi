package com.sosotaxi.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.sosotaxi.R;

public class CallCar extends Activity {
    private TextView tv_start=null;
    private TextView tv_dest=null;
    private MapView mMapView =null;
    private String myLocation=null;
    private String destination=null;
    private Double destLatitude=null;
    private Double destLongitude=null;
    private Double myLatitude=null;
    private Double myLongitude=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        initTitle();
        initView();
    }
    private void initTitle() {
        ImageView imgBack = (ImageView) findViewById(R.id.robin_title_left);
        imgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });
        myLocation=getIntent().getStringExtra("mlocation");
        destination=getIntent().getStringExtra("dlocation");
        tv_start = (ClearEditTextView) findViewById(R.id.start);
        tv_start.setText(myLocation);
        tv_dest = (ClearEditTextView)findViewById(R.id.dest);
        tv_dest.setText(destination);
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
    }
}
