package com.sosotaxi.application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.sosotaxi.common.Constant;
import com.sosotaxi.utils.TraceHelper;

/**
 * 用于百度地图SDK初始化
 */
public class MapApplication extends Application {
    /**
     * 轨迹
     */
    private Trace mTrace;

    /**
     * 轨迹连接器
     */
    private LBSTraceClient mTraceClient;

    /**
     * 上下文
     */
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=getApplicationContext();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);

        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        // 初始化鹰眼轨迹
        initTrace();
    }

    /**
     * 初始化鹰眼轨迹
     */
    private void initTrace(){
        mTrace = new Trace(Constant.SERVICE_ID,"");
        mTraceClient = new LBSTraceClient(mContext);
        TraceHelper.setTrace(mTrace);
        TraceHelper.setTraceClient(mTraceClient);
    }
}
