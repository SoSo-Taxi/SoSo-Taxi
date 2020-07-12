package com.sosotaxi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.sosotaxi.R;
import com.sosotaxi.ui.userInformation.order.OrderActivity;
import com.sosotaxi.ui.userInformation.personData.PersonalDataActivity;
import com.sosotaxi.ui.userInformation.wallet.WalletActivity;

import androidx.annotation.NonNull;
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

    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawerLayout;

    /**
     * 侧边栏用户姓名和
     * 用户其他信息（确定填写内容后更改）
     */
    private TextView mUserName;
    private TextView mUserOtherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.baiduMapView);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}