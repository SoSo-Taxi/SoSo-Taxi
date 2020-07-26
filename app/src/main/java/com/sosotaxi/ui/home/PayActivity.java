/**
 * @Author 岳兵
 * @CreateTime 2020/7/18
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.ui.home;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sosotaxi.R;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.Order;
import com.sosotaxi.service.net.OrderClient;
import com.sosotaxi.service.net.OrderMessageReceiver;
import com.sosotaxi.service.net.OrderService;
import com.sosotaxi.utils.MessageHelper;

public class PayActivity extends AppCompatActivity {


    private TextView tv_pay;

    private PayPsdInputView payPsdInputView;

    private FrameLayout paywindow;
    private Order mOrder;

    private MessageHelper mMessageHelper;

    private ConstraintLayout paypay;



    //token
    private String token;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mOrder=getIntent().getParcelableExtra(Constant.EXTRA_ORDER);

        token=getIntent().getStringExtra("token");
        paypay=(ConstraintLayout)findViewById(R.id.paypay);
        if (paypay.getForeground()!=null){
            paypay.getForeground().setAlpha(0);
        }
        paywindow=(FrameLayout)findViewById(R.id.paywindow);
        tv_pay=(TextView)findViewById(R.id.tv_pay);

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypay.getForeground().setAlpha(127);
                paywindow.setVisibility(View.VISIBLE);
            }
        });

        payPsdInputView=(PayPsdInputView)findViewById(R.id.payview);
        payPsdInputView.setComparePassword("123456", new PayPsdInputView.onPasswordListener() {
            @Override
            public void onDifference(String oldPsd, String newPsd) {
                // TODO: 2017/5/7   和上次输入的密码不一致  做相应的业务逻辑处理
                Toast.makeText(PayActivity.this,"两次密码输入不同",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
                Toast.makeText(PayActivity.this,"密码相同"+psd,Toast.LENGTH_SHORT).show();
                // 跳转评价页面
                Intent intent= new Intent(PayActivity.this,RateActivity.class);
                intent.putExtra(Constant.EXTRA_ORDER,mOrder);
                startActivity(intent);

            }

            @Override
            public void inputFinished(String inputPsd) {

            }
        });







        initView();

    }

    private void initView(){

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
        // 断开连接

    }






}
