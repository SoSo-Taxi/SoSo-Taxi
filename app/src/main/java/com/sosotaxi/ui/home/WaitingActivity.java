/**
 * @Author 岳兵
 * @CreateTime 2020/7/18
 * @UpdateTime 2020/7/19
 */
package com.sosotaxi.ui.home;

import android.app.Activity;
import android.os.Bundle;


import com.baidu.mapapi.map.MapView;
import com.sosotaxi.R;


public class WaitingActivity extends Activity {


    private MapView mMapView =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_waiting);


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
    }




}
