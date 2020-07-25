/**
 * @Author 范承祥
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.service.net;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.api.track.OnTrackListener;
import com.sosotaxi.model.message.BaseMessage;
import com.sosotaxi.utils.MessageHelper;

/**
 * 查询最新位置任务
 */
public class QueryLatestPointTask implements Runnable {

    /** 查询时间间隔 */
    private long mTimeInterval;

    /**
     * 退出标志位
     */
    private boolean mIsExit;

    /**
     * 消息帮手
     */
    private MessageHelper mMessageHelper;

    private BaseMessage mMessage;

    public QueryLatestPointTask(long timeInterval, MessageHelper messageHelper, BaseMessage message){
        mTimeInterval=timeInterval;
        mMessageHelper=messageHelper;
        mMessage=message;
        mIsExit=false;
    }

    @Override
    public void run() {
        while (mIsExit==false){
            try {
                Thread.sleep(mTimeInterval);
                mMessageHelper.send(mMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean isExit() {
        return mIsExit;
    }

    public void setIsExit(boolean isExit) {
        this.mIsExit = isExit;
    }
}
