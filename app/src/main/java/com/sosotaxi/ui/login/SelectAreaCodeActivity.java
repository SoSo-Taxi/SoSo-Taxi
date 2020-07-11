package com.sosotaxi.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.sosotaxi.R;
import com.sosotaxi.adapter.AreaCodeRecyclerViewAdapter;
import com.sosotaxi.common.Constant;
import com.sosotaxi.common.OnRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectAreaCodeActivity extends AppCompatActivity {

    private SearchView mSearchViewAreaCode;
    private RecyclerView mRecyclerViewAreaCode;
    private AreaCodeRecyclerViewAdapter mAreaCodeRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area_code);

        Toolbar toolbar = findViewById(R.id.toolbarAreaCode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerViewAreaCode=findViewById(R.id.recyclerViewAreaCode);
        mRecyclerViewAreaCode.setLayoutManager(new LinearLayoutManager(this));

        mAreaCodeRecyclerViewAdapter=new AreaCodeRecyclerViewAdapter(Arrays.asList(getResources().getStringArray(R.array.area_codes)));

        //设置选中列表项事件
        mAreaCodeRecyclerViewAdapter.setListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view) {
                // 提取区号
                String selected=mRecyclerViewAreaCode.getChildViewHolder(view).toString();
                int position=selected.indexOf(' ',1);
                String areaCode=selected.substring(position).trim();
                Intent intent=new Intent();
                intent.putExtra(Constant.EXTRA_AREA_CODE,Integer.parseInt(areaCode));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        mRecyclerViewAreaCode.setAdapter(mAreaCodeRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_select_area_code, menu);
        MenuItem searchItem=menu.findItem(R.id.menu_search_area_code);
        mSearchViewAreaCode=(SearchView) MenuItemCompat.getActionView(searchItem);

        mSearchViewAreaCode.setQueryHint(getString(R.string.placeholder_search_area_code));

        //设置文本监听
        mSearchViewAreaCode.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                mAreaCodeRecyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}