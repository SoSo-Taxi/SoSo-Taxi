/**
 * @Author 岳兵
 * @CreateTime 2020/7/20
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.InfoWindow;
import com.sosotaxi.R;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.Order;
import com.sosotaxi.service.net.RateForDriverTask;
import com.sosotaxi.ui.main.MainActivity;


public class RateActivity extends Activity {

    private Order mOrder;

    private RatingBar ratingBar;
    private double ratingForDriver;

    private TextView tv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        initView();

        mOrder=getIntent().getParcelableExtra(Constant.EXTRA_ORDER);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取订单ID
                long orderId=mOrder.getOrderId();
                // 评价司机
                new Thread(new RateForDriverTask(orderId,ratingForDriver,handler)).start();
            }
        });
    }

    private void initView(){
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingForDriver=(double)rating;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理

    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    }

    /**
     * UI线程更新处理器
     */
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            // 提示异常信息
            if(bundle.getString(Constant.EXTRA_ERROR)!=null){
                Toast.makeText(getApplicationContext(), bundle.getString(Constant.EXTRA_ERROR), Toast.LENGTH_SHORT).show();
                return false;
            }

            boolean isSuccessful = bundle.getBoolean(Constant.EXTRA_IS_SUCCESSFUL);
            String message=bundle.getString(Constant.EXTRA_RESPONSE_MESSAGE);

            if(isSuccessful){
                // 提示评分成功
                Toast.makeText(getApplicationContext(), "评价成功", Toast.LENGTH_SHORT).show();

                // 跳转首页
                Intent intent=new Intent(RateActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                // 提示评分失败并显示原因
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });
}
