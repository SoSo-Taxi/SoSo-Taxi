/**
 * @Author 屠天宇
 * @CreateTime 2020/7/12
 * @UpdateTime 2020/7/12
 */

package com.sosotaxi.ui.userInformation.personData;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sosotaxi.R;


public class IndustryChosenActivity extends AppCompatActivity {

    private RecyclerView mIndustryChosenRecycleView;
    private IndustryChosenRecycleViewAdapter mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_chosen);
        Toolbar toolbar = findViewById(R.id.industry_chosen_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIndustryChosenRecycleView = findViewById(R.id.industry_chosen_recycleView);
        mIndustryChosenRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        this.mRecycleViewAdapter = new IndustryChosenRecycleViewAdapter(getApplicationContext());
        this.mRecycleViewAdapter.setGetChoice(new GetChoice() {
            @Override
            public void onItemClick(String choice) {
                Intent intent = new Intent();
                intent.putExtra("industry",choice);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        mIndustryChosenRecycleView.setAdapter(mRecycleViewAdapter);
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