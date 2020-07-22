/**
 * @Author 屠天宇
 * @CreateTime 2020/7/8
 * @UpdateTime 2020/7/22
 */

package com.sosotaxi.ui.userInformation.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.sosotaxi.R;

public class OrderActivity extends AppCompatActivity {

    private Toolbar mOrderToolbar;
    private RecyclerView mOrderItemRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mOrderToolbar = findViewById(R.id.order_toolbar);
        setSupportActionBar(mOrderToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOrderItemRecyclerView = findViewById(R.id.orderitem_recycleview);
        mOrderItemRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mOrderItemRecyclerView.setAdapter(new OrderItemRecycleViewAdapter(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        if (item.getItemId() == R.id.order_toolbar_menu_check_time){
            return false;
        }
        if (item.getItemId() == R.id.order_toolbar_menu_right_now){
            return false;
        }
        return false;
    }

    //返回父界面方法二（速度过快 不太自然）
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}