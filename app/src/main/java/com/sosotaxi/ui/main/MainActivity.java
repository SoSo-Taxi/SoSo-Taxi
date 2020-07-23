/**
 * @Author 屠天宇
 * @CreateTime 2020/7/10
 * @UpdateTime 2020/7/22
 */

package com.sosotaxi.ui.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.sosotaxi.R;
import com.sosotaxi.common.Constant;
import com.sosotaxi.service.net.OrderClient;
import com.sosotaxi.service.net.OrderMessageReceiver;
import com.sosotaxi.service.net.OrderService;
import com.sosotaxi.ui.userInformation.order.OrderActivity;
import com.sosotaxi.ui.userInformation.personData.PersonalDataActivity;
import com.sosotaxi.ui.userInformation.setting.SettingActivity;
import com.sosotaxi.ui.userInformation.wallet.WalletActivity;
import com.sosotaxi.utils.MessageHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * 连接器
     */
    private OrderClient mDriverOrderClient;

    /**
     * WebSocket服务
     */
    private OrderService mDriverOrderService;

    /**
     * 绑定
     */
    private OrderService.DriverOrderBinder mBinder;

    /**
     * 接收器
     */
    private OrderMessageReceiver mOrderMessageReceiver;

    private MessageHelper mMessageHelper;

    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawerLayout;

    /**
     * 侧边栏用户姓名和
     * 用户其他信息（确定填写内容后更改）
     */
    private TextView mUserName;
    private TextView mUserOtherInfo;

    private boolean mIsLogin = true;

    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationIcon(R.drawable.ic_default_head_photo_v2);

        //与登录界面对接后修改
        final View headerView = navigationView.getHeaderView(0);
        mUserName = headerView.findViewById(R.id.username);
        mUserOtherInfo = headerView.findViewById(R.id.user_otherInfo);
        final ImageView headImageView = headerView.findViewById(R.id.head_imageView);

        mUserName.setText("13613616136");
        mUserOtherInfo.setText("会员状态");
        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalDataActivity.class);
                headImageView.setDrawingCacheEnabled(true);
                intent.putExtra("image",headImageView.getDrawingCache());
                intent.putExtra("phone",mUserName.getText());
                startActivityForResult(intent,200);
            }
        });
        Bundle getBundle = this.getIntent().getExtras();

        token = getBundle.getString(Constant.EXTRA_TOKEN);
        // 初始化服务并绑定
        startService();
        bindService();
        registerReceiver();

        //获取消息助手
        mMessageHelper=MessageHelper.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.bmapView);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Constant.SETTING_RESULT_CODE){
                if (data.getBooleanExtra("isQuit",false)){
                    mUserName.setText("请登录");
                    mUserOtherInfo.setText("");
                    mIsLogin = !data.getBooleanExtra("isQuit",false);
                }
            }
        }
    }
    /**
     * 开启WebSocket服务
     */
    private void startService(){
        Intent intent=new Intent(getApplicationContext(),OrderService.class);
        startService(intent);
    }

    /**
     * 绑定服务
     */
    private void bindService(){
        Intent intent = new Intent(getApplicationContext(), OrderService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver(){
        mOrderMessageReceiver=new OrderMessageReceiver();
        IntentFilter intentFilter=new IntentFilter(Constant.FILTER_CONTENT);
        registerReceiver(mOrderMessageReceiver,intentFilter);
    }
    // 服务连接监听器
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.e("aaaaaaaaaaaa",token);
            mBinder=(OrderService.DriverOrderBinder)service;

            mDriverOrderService=mBinder.getService(token);
            mDriverOrderClient=mDriverOrderService.getClient();
            Toast.makeText(getApplicationContext(),"Service已连接",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(),"Service已断开",Toast.LENGTH_SHORT).show();
        }
    };
    public OrderClient getClient(){
        return mDriverOrderClient;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 断开连接
        unbindService(serviceConnection);
        if(mOrderMessageReceiver!=null){
            unregisterReceiver(mOrderMessageReceiver);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Intent intent = null;
        switch (menuItem.getItemId()){
            case R.id.nav_order:
                intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_safety:

            case R.id.nav_wallet:
                intent = new Intent(getApplicationContext(), WalletActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_service:

            case R.id.nav_setting:
                intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, Constant.SETTING_RESULT_CODE);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}