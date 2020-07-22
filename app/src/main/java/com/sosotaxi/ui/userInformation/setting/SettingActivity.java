package com.sosotaxi.ui.userInformation.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.sosotaxi.R;
import com.sosotaxi.ui.userInformation.setting.AboutUs.AboutUsActivity;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout lawClaimCL = findViewById(R.id.law_claim_constraint_layout);
        lawClaimCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LawClaimActivity.class));
            }
        });

        ConstraintLayout usualCL = findViewById(R.id.usual_address_cl);
        usualCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UsualAddressActivity.class));
            }
        });

        ConstraintLayout emergencyContactCL = findViewById(R.id.emergency_contact_CL);
        emergencyContactCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EmergencyContactActivity.class));
            }
        });

        ConstraintLayout quitConstraintLayout = findViewById(R.id.quit_constraint_layout);
        quitConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("isQuit",true);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        ConstraintLayout aboutUsConstraintLayout = findViewById(R.id.about_so_so_contraint_layout);
        aboutUsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
            }
        });
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
