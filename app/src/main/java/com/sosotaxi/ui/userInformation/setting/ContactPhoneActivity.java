package com.sosotaxi.ui.userInformation.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sosotaxi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactPhoneActivity extends AppCompatActivity {

    private RecyclerView mContactRecycleView;
    private ContactRecycleViewAdapter mContactRecycleViewAdapter;
    private GetPhoneNumberFromMobile mGetPhoneNumberFromMobile;
    private List<PhoneInfo>mPhoneInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_phone);
        Toolbar toolbar = findViewById(R.id.contact_phone_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGetPhoneNumberFromMobile = new GetPhoneNumberFromMobile();
        mPhoneInfoList = mGetPhoneNumberFromMobile.getPhoneNumberFromMobile(getApplicationContext());
        mContactRecycleViewAdapter = new ContactRecycleViewAdapter(getApplicationContext(),mPhoneInfoList);
        mContactRecycleViewAdapter.setClickListener(new ClickListener() {
            @Override
            public void click(String phone,String name) {
                Intent intent = new Intent();
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
       mContactRecycleView = findViewById(R.id.contact_recycle_view);
       mContactRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
       mContactRecycleView.setAdapter(mContactRecycleViewAdapter);
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