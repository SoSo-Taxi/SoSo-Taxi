/**
 * @Author 屠天宇
 * @CreateTime 2020/7/8
 * @UpdateTime 2020/7/11
 */


package com.sosotaxi.ui.userInformation.personData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sosotaxi.R;

public class IndustrySelectActivity extends AppCompatActivity {

    private String[] choices = {"互联网-软件","通信-硬件","房地产-建筑","文化传媒"
            , "金融类","消费品","汽车-机械","教育-翻译","贸易-物流"
            ,"生物-医疗","能源-化工","政府机构","服务业","其他行业"
    };
    private LinearLayout[] mLinearLayouts = new LinearLayout[14];
    private int[] IDs = {R.id.ll1,R.id.ll2,R.id.ll3,R.id.ll4,R.id.ll5,R.id.ll6,R.id.ll7,R.id.ll8
            , R.id.ll9,R.id.ll10,R.id.ll11,R.id.ll12,R.id.ll13,R.id.ll14};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_select);
        Toolbar toolbar = findViewById(R.id.industtry_select_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for(int i = 0; i < IDs.length; i++){
            mLinearLayouts[i] = findViewById(IDs[i]);
            mLinearLayouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout linearLayout = (LinearLayout)v;
                    TextView textView = (TextView) linearLayout.getChildAt(0);
                    Intent intent = new Intent();
                    intent.putExtra("industry",textView.getText());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
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