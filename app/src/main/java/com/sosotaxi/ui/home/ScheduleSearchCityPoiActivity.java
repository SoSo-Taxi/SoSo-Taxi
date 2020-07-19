/**
 * @Author 岳兵
 * @CreateTime 2020/7/10
 * @UpdateTime 2020/7/16
 */
package com.sosotaxi.ui.home;


import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;





import com.sosotaxi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleSearchCityPoiActivity extends Activity implements
        OnGetPoiSearchResultListener{

    private XListView poiListView;
    private ClearEditTextView mSearchEditText;
    private PoiSearch mPoiSearch = null;
    private int load_Index = 0;
    private List<PoiInfo> poiList;
    private ScheduleSearchCityPoiAdapter adapter;
//    private ProgressView pgLoading;
    private Boolean bLoading = false;
    private String keyWord;
    private String city;
    private String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_search_city_poi_activity);
        city = getIntent().getStringExtra("city");
        destination = getIntent().getStringExtra("destination");
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        initTitle();
        initView();


    }

    private void initTitle() {
        ImageView imgBack = (ImageView) findViewById(R.id.robin_title_left);
        imgBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        TextView tvTitle = (TextView) findViewById(R.id.robin_title_center);
        tvTitle.setText(city);
        TextView tvRight = (TextView) findViewById(R.id.robin_title_right);

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("搜索目的地");
        tvRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                keyWord = mSearchEditText.getText().toString().trim();
                hideKeyboard();
                if (keyWord != null && keyWord.length() > 0) {
//                    pgLoading.setVisibility(View.VISIBLE);
                    loadData(keyWord);
                } else {
//                    Toast.makeText(getApplicationContext(), "您还没有输入搜索地点", 0)
//                            .show();
                    return;
                }
            }
        });
    }

    private void initView() {
//        pgLoading = (ProgressView) findViewById(R.id.search_poi_progress);
        mSearchEditText = (ClearEditTextView) findViewById(R.id.pio_filter_edit);
        poiListView = (XListView) findViewById(R.id.schedule_search_pio);
        poiList = new ArrayList<PoiInfo>();
        mSearchEditText.setText(destination);
        TextView tvRight = (TextView) findViewById(R.id.robin_title_right);
        tvRight.performClick();


    }

    private void loadData(String keyWord) {
        load_Index = 0;
        mPoiSearch.searchInCity((new PoiCitySearchOption()).city(city)
                .keyword(keyWord).pageNum(load_Index));
    }

    protected void loadMoreData() {
        load_Index++;
        mPoiSearch.searchInCity((new PoiCitySearchOption()).city(city)
                .keyword(keyWord).pageNum(load_Index));
    }


//    @Override
//    public void onGetPoiDetailResult(PoiDetailSearchResult result) {
//        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(ScheduleSearchCityPoiActivity.this, "抱歉，未找到结果",
//                    Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(ScheduleSearchCityPoiActivity.this,
//                    result.getPoiDetailInfoList()+ ": " + result.getAddress(),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }




    @Override
    public void onGetPoiResult(PoiResult result) {
//        pgLoading.loadSuccess();
//        pgLoading.setVisibility(View.GONE);
        poiListView.setVisibility(View.VISIBLE);
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(ScheduleSearchCityPoiActivity.this, "未找到结果",
                    Toast.LENGTH_LONG).show();
            stopRefresh();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            List<PoiInfo> allPoi = result.getAllPoi();
            if (adapter != null) {
                if (load_Index == 0) {//刷新
                    refreshPoiList(allPoi);
                }else {
                    updatePoiList(allPoi);
                }
            }else {
                bLoading = false;
                renderPoiList(allPoi);
            }
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(ScheduleSearchCityPoiActivity.this, strInfo,
                    Toast.LENGTH_LONG).show();
            stopRefresh();
        }
    }
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(ScheduleSearchCityPoiActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ScheduleSearchCityPoiActivity.this,
                    poiDetailResult.getName() + ": " + poiDetailResult.getAddress(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        if (poiDetailSearchResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(ScheduleSearchCityPoiActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            List<PoiDetailInfo> poiDetailInfoList = poiDetailSearchResult.getPoiDetailInfoList();
            if (null == poiDetailInfoList || poiDetailInfoList.isEmpty()) {
                Toast.makeText(ScheduleSearchCityPoiActivity.this, "抱歉，检索结果为空", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < poiDetailInfoList.size(); i++) {
                PoiDetailInfo poiDetailInfo = poiDetailInfoList.get(i);
                if (null != poiDetailInfo) {
                    Toast.makeText(ScheduleSearchCityPoiActivity.this,
                            poiDetailInfo.getName() + ": " + poiDetailInfo.getAddress(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    private void refreshPoiList(List<PoiInfo> allPoi) {
        poiList.clear();
        poiList.addAll(allPoi);
        adapter.notifyDataSetChanged();
        stopRefresh();
    }

    private void renderPoiList(List<PoiInfo> allPoi) {
        poiList = allPoi;
        adapter = new ScheduleSearchCityPoiAdapter(
                ScheduleSearchCityPoiActivity.this, poiList);
        poiListView.setAdapter(adapter);
        poiListView.setPullRefreshEnable(true);
        poiListView.setPullLoadEnable(true);
        poiListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (bLoading)
                    return;
                bLoading = true;
                loadData(keyWord);
            }

            @Override
            public void onLoadMore() {
                if (bLoading)
                    return;
                bLoading = true;
                loadMoreData();
            }

        });

        poiListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PoiInfo poiInfo = poiList.get(position-1);
                String strAddress = poiInfo.address;
                String strName = poiInfo.name;
                LatLng location = poiInfo.location;
                Intent intent = new Intent();
                intent.putExtra("address", strAddress);
                intent.putExtra("name", strName);
                intent.putExtra("latitude", location.latitude);
                intent.putExtra("longitude", location.longitude);
                setResult(RESULT_OK, intent);
                finish();
            }

        });
    }

    private void updatePoiList(List<PoiInfo> allPoi) {
        poiList.addAll(allPoi);
        adapter.notifyDataSetChanged();
        stopRefresh();
    }

    private void stopRefresh(){
        poiListView.stopLoadMore();
        poiListView.stopRefresh();
        bLoading = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
    }
}