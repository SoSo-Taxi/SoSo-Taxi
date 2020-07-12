/**
 * @Author 岳兵
 * @CreateTime 2020/7/09
 * @UpdateTime 2020/7/10
 */
package com.sosotaxi.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientationListener implements SensorEventListener
{
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mSensor;
    private float mlastX;

    public MyOrientationListener(Context context)
    {
        this.mContext = context;
    }

    public void start()
    {
        mSensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null)
        {
            // 获得方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }

        if (mSensor != null)
        {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop()
    {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
        {
            float x = event.values[SensorManager.DATA_X];

            if (Math.abs(x - mlastX) > 1.0)
            {
                if (mOnOrientationListener != null)
                {
                    mOnOrientationListener.onOrientationChanged(x);
                }
            }

            mlastX = x;

        }
    }

    private OnOrientationListener mOnOrientationListener;

    public void setOnOrientationListener(
            OnOrientationListener mOnOrientationListener)
    {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    public interface OnOrientationListener
    {
        void onOrientationChanged(float x);
    }

}
