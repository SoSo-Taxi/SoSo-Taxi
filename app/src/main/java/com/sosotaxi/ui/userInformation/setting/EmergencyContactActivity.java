package com.sosotaxi.ui.userInformation.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sosotaxi.R;
import com.sosotaxi.common.Constant;

public class EmergencyContactActivity extends AppCompatActivity {

    private EditText mPhoneEditText;
    private EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        Toolbar toolbar = findViewById(R.id.emergency_contact_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView contactTextView = findViewById(R.id.contact_textView);
        mPhoneEditText = findViewById(R.id.editTextPhone);
        mNameEditText = findViewById(R.id.editTextTextPersonName);
        //获取权限
        contactTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23){
                    if (PermissionHelper.hasBaseAuth(getApplicationContext(), Manifest.permission.READ_CONTACTS)==false) {
                        // 未获取则请求权限
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Constant.PERMISSION_READ_CONTACTS_CODE);
                        return;
                    }
                }
                Intent intent = new Intent(getApplicationContext(),ContactPhoneActivity.class);
                startActivityForResult(intent,200);
            }
        });

        TextView confirmTextView = findViewById(R.id.contact_confirm_textView);
        confirmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:向后端传送紧急联系人数据
                Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 200:
                    mPhoneEditText.setText(data.getCharSequenceExtra("phone"));
                    mNameEditText.setText(data.getCharSequenceExtra("name"));
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case Constant.PERMISSION_READ_CONTACTS_CODE:
                if(PermissionHelper.hasBaseAuth(getApplicationContext(),Manifest.permission.READ_CONTACTS)==false){
                    // 未获得权限则提示用户权限作用
                    Toast.makeText(getApplicationContext(),"授权", Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(new Intent(getApplicationContext(),ContactPhoneActivity.class));
                break;
            default:
                Toast.makeText(getApplicationContext(),"失败", Toast.LENGTH_SHORT).show();

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