/**
 * @Author 范承祥
 * @CreateTime 2020/7/18
 * @UpdateTime 2020/7/13
 */
package com.sosotaxi.utils;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapsdkplatform.comapi.location.CoordinateType;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.EntityListRequest;
import com.baidu.trace.api.entity.FilterCondition;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.TransportMode;
import com.sosotaxi.common.Constant;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轨迹帮手类
 */
public class TraceHelper {
    /**
     * 轨迹
     */
    private static Trace sTrace;

    /**
     * 轨迹连接器
     */
    private static LBSTraceClient sTraceClient;

    /**
     * 序列生成器
     */
	private static AtomicInteger mSequenceGenerator;

    /**
     * 设置路径
     * @param trace
     */
	public static void setTrace(Trace trace){
	    sTrace=trace;
    }

    /**
     * 设置路径连接器
     * @param traceClient
     */
    public static void setTraceClient(LBSTraceClient traceClient){
	    sTraceClient=traceClient;
    }

    /**
     * 初始化轨迹
     * @param entityName 实体名
     * @param gatherInterval 收集间隔时间
     * @param packInterval 打包间隔时间
     */
    public static void initTrace(String entityName, int gatherInterval, int packInterval){
        if(sTrace==null||sTraceClient==null){
            return;
        }
        // 设置实体名
        sTrace.setEntityName(entityName);
        // 设置定位和打包周期
        sTraceClient.setInterval(gatherInterval, packInterval);
        // 初始化序号创造器
        mSequenceGenerator = new AtomicInteger();
    }

    /**
     * 初始化轨迹
     * @param entityName 实体名
     * @param gatherInterval 收集间隔时间
     * @param packInterval 打包间隔时间
     * @param onTraceListener 轨迹监听器
     */
    public static void initTrace(String entityName, int gatherInterval, int packInterval, OnTraceListener onTraceListener){
        if(sTrace==null||sTraceClient==null){
            return;
        }
        // 设置实体名
        sTrace.setEntityName(entityName);
        // 设置定位和打包周期
        sTraceClient.setInterval(gatherInterval, packInterval);
        // 设置监听器
        sTraceClient.setOnTraceListener(onTraceListener);
		// 初始化序号创造器
		mSequenceGenerator = new AtomicInteger();
    }

    /**
     * 获取轨迹连接器实例
     * @return 轨迹连接器对象
     */
    public static LBSTraceClient getTraceClient(){
        return sTraceClient;
    }

    /**
     * 开始轨迹记录
     */
    public static void startTrace(){
        sTraceClient.startTrace(sTrace, null);
    }

    /**
     * 开始收集轨迹
     */
    public static void startGather(){
        sTraceClient.startGather(null);
    }

    /**
     * 停止轨迹记录
     */
    public static void stopTrace(){
        sTraceClient.stopTrace(sTrace, null);
    }

    /**
     * 停止收集轨迹
     */
    public static void stopGather(){
        sTraceClient.stopGather(null);
    }

    /**
     * 构建查询实体请求
     * @param entityNames 实体名列表
     * @param activeTime 活跃时间
     * @return 查询实体请求
     */
    public static EntityListRequest buildEntityRequest(List<String> entityNames, long activeTime){
        // 创建查询
        EntityListRequest entityListRequest=new EntityListRequest();
        // 设置服务号
        entityListRequest.setServiceId(Constant.SERVICE_ID);

        FilterCondition filterCondition=new FilterCondition();
        // 设置实体名
        filterCondition.setEntityNames(entityNames);
        // 设置活跃时间
        filterCondition.setActiveTime(activeTime);

        entityListRequest.setFilterCondition(filterCondition);

        return entityListRequest;
    }

    /**
     * 构建查询历史轨迹请求
     * @param entityName 实体名
     * @param startTime 开始起始
     * @param endTime 截止时间
     * @return 查询历史轨迹请求
     */
    public static HistoryTrackRequest buildHistoryTrackRequest(String entityName, long startTime, long endTime){
        HistoryTrackRequest historyTrackRequest=new HistoryTrackRequest();
        // 设置标签
        historyTrackRequest.setTag(getTag());
        // 设置服务号
        historyTrackRequest.setServiceId(Constant.SERVICE_ID);
        // 设置实体名
        historyTrackRequest.setEntityName(entityName);
        // 设置开始时间
        historyTrackRequest.setStartTime(startTime);
        // 设置结束时间
        historyTrackRequest.setEndTime(endTime);

        return historyTrackRequest;
    }

    /**
     * 构建查询最新点请求
     * @param entityName 实体名
     * @return 最新点查询请求
     */
    public static LatestPointRequest buildLatestPointRequest(String entityName){
        LatestPointRequest latestPointRequest=new LatestPointRequest();
        // 设置标签
        latestPointRequest.setTag(getTag());
        // 设置实体名
        latestPointRequest.setEntityName(entityName);
        // 设置服务号
        latestPointRequest.setServiceId(Constant.SERVICE_ID);

        return latestPointRequest;
    }

    /**
     * 获取标签
     * @return 标签
     */
	private static int getTag(){
		return mSequenceGenerator.incrementAndGet();
	}
}
