/**
 * @Author 屠天宇
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */

package com.sosotaxi.ui.userInformation.setting.userGuide;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sosotaxi.R;

import java.util.ArrayList;
import java.util.List;

public class UserGuideActivity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;

    /** TabLayout调谐器 */
    private TabLayoutMediator mMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        Toolbar toolbar = findViewById(R.id.user_guide_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTabLayout = findViewById(R.id.user_guide_tabLayout);
        mViewPager = findViewById(R.id.user_guide_viewPager);
        initTab();

    }

    private void initTab(){
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new UserFragment());
        fragments.add(new DriverFragment());

        // 标题列表
        final List<String> titles=new ArrayList<>();
        titles.add("乘客");
        titles.add("车主");

        // 添加Tab页
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)),true);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)),false);

        // 禁用预加载
        mViewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);

        mViewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(),getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        // 设置调谐器
        mMediator=new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // 设置Tab标题
                tab.setText(titles.get(position));
            }
        });

        // 链接
        mMediator.attach();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}