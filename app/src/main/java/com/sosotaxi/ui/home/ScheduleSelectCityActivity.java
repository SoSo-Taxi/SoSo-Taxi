package com.sosotaxi.ui.home;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.sosotaxi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleSelectCityActivity extends Activity {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private ClearEditTextView mClearEditText;

    private List<ScheduleCityGpsStruct> cityList, currentCityList;
    private ScheduleCitySelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        initView();

        loadData();
        initTitle();
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
        tvTitle.setText("选择城市");

        TextView tvRight = (TextView) findViewById(R.id.robin_title_right);
        tvRight.setVisibility(View.GONE);
    }

    private void initView() {
        sideBar = (SideBar) findViewById(R.id.city_sidebar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s);
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView = (ListView) findViewById(R.id.city_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ScheduleCityGpsStruct struct = currentCityList.get(position);
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cityItemData", (Serializable) struct);
                i.putExtras(bundle);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        mClearEditText = (ClearEditTextView) findViewById(R.id.city_filter_edit);
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public String readInPutStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();
        }
    }

    private void loadData() {
        cityList = new ArrayList<ScheduleCityGpsStruct>();
        try {
              AssetManager assetManager = this.getAssets();
              InputStream inputStream = null;
              inputStream = assetManager.open("cityjson.json");
              String jsonStr = readInPutStream(inputStream);

              JSONArray jsonArray =  new JSONArray(jsonStr);
              int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                ScheduleCityGpsStruct sGpsStruct = new ScheduleCityGpsStruct();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sGpsStruct.setSortLetters(jsonObject.getString("sortLetter"));
                sGpsStruct.setStrCityName(jsonObject.getString("cityName"));
                sGpsStruct.setStrLatitude(jsonObject.getString("lat"));
                sGpsStruct.setStrLongitude(jsonObject.getString("log"));
                cityList.add(sGpsStruct);
            }
//            InputStream is = getAssets().open("cityjson.json");
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line = "";
//            String[] arrs = null;
//            while ((line = br.readLine()) != null) {
//                ScheduleCityGpsStruct sGpsStruct = new ScheduleCityGpsStruct();
//                arrs = line.split(",");
//                if (arrs.length == 4) {
//                    sGpsStruct.setSortLetters(arrs[0]);
//                    sGpsStruct.setStrCityName(arrs[1]);
//                    sGpsStruct.setStrLongitude(arrs[2]);
//                    sGpsStruct.setStrLatitude(arrs[3]);
//                }else if (arrs.length == 3) {
//                    sGpsStruct.setSortLetters("");
//                    sGpsStruct.setStrCityName(arrs[0]);
//                    sGpsStruct.setStrLongitude(arrs[1]);
//                    sGpsStruct.setStrLatitude(arrs[2]);
//                }
//                cityList.add(sGpsStruct);
//            }

            currentCityList = cityList;
            adapter = new ScheduleCitySelectAdapter(this, cityList);
            sortListView.setAdapter(adapter);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void filterData(String filterStr) {
        List<ScheduleCityGpsStruct> filterDateList = new ArrayList<ScheduleCityGpsStruct>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = cityList;
        } else {
            filterDateList.clear();
            for (ScheduleCityGpsStruct struct : cityList) {
                String strCityName = struct.getStrCityName();
                if (strCityName.contains(filterStr.toString())) {
                    filterDateList.add(struct);
                }
            }
            currentCityList = filterDateList;
        }
        adapter.updateListView(filterDateList);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}