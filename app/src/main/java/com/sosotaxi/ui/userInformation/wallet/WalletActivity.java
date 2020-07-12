package com.sosotaxi.ui.userInformation.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sosotaxi.R;

public class WalletActivity extends AppCompatActivity {

    private Toolbar mWalletToolbar;
    /**
     * mAssertTextView显示余额
     * mCouponQuantityTextView显示优惠券数量
     * mVIPPointTextView显示积分数量
     * 与后端对接
     */
    private TextView mAssertTextView;
    private TextView mCouponQuantityTextView;
    private TextView mVIPPointTextView;

    private Button mPayBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        mWalletToolbar = findViewById(R.id.wallet_toolbar);
        setSupportActionBar(mWalletToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAssertTextView = findViewById(R.id.assert_textView);

        //设置余额
        mAssertTextView.setText("6.66");
        mCouponQuantityTextView = findViewById(R.id.coupon_quantity_textView);

        //设置消费券数量
        mCouponQuantityTextView.setText("10");
        mVIPPointTextView = findViewById(R.id.VIP_points_TextView);

        //设置积分数量
        mVIPPointTextView.setText("220");

        mPayBtn = findViewById(R.id.payment_btn);
        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChargeMoneyActivity.class);
                startActivity(intent);
            }
        });

        ConstraintLayout couponcL = findViewById(R.id.coupon_cLayout);
        couponcL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_LONG).show();
            }
        });
        ConstraintLayout VIPPointcL = findViewById(R.id.VIP_Point_cLayout);
        VIPPointcL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.payment_setting){
            Intent intent = new Intent(getApplicationContext(),PaymentSettingActivity.class);
            startActivity(intent);
            return false;
        }
        return false;
    }
}